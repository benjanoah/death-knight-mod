package com.benjanoah.deathknight.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class DeathKnightEntity extends HostileEntity {

    // Boss bar bovenaan het scherm
    private final ServerBossBar bossBar = new ServerBossBar(
        Text.literal("☠ Death Knight"),
        BossBar.Color.RED,
        BossBar.Style.NOTCHED_10
    );

    // Cooldowns (in ticks, 20 ticks = 1 second)
    private int witherSkullCooldown = 0;
    private static final int WITHER_SKULL_COOLDOWN_MAX = 80; // 4 seconden

    public DeathKnightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        // Netherite sword in de hand (maar doet iron sword schade via attribute)
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createDeathKnightAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 150.0)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)          // Iron sword schade (6)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
            .add(EntityAttributes.GENERIC_ARMOR, 4.0)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6);
    }

    // Boss bar toevoegen als speler dichtbij komt
    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    // Boss bar verwijderen als speler weggaat
    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient) {
            // Boss bar health bijhouden
            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());

            // Wither skull cooldown afbouwen
            if (witherSkullCooldown > 0) {
                witherSkullCooldown--;
            }

            // Wither skull aanval
            LivingEntity target = this.getTarget();
            if (target != null && witherSkullCooldown <= 0) {
                double distanceSq = this.squaredDistanceTo(target);
                if (distanceSq > 25.0 && distanceSq < 900.0) {
                    this.shootWitherSkull(target);
                    witherSkullCooldown = WITHER_SKULL_COOLDOWN_MAX;
                }
            }
        }
    }

    private void shootWitherSkull(LivingEntity target) {
        World world = this.getWorld();

        double dx = target.getX() - this.getX();
        double dy = target.getBodyY(0.5) - this.getBodyY(0.5);
        double dz = target.getZ() - this.getZ();

        WitherSkullEntity skull = new WitherSkullEntity(world, this, dx, dy, dz);
        skull.setPosition(this.getX(), this.getBodyY(0.5), this.getZ());
        world.spawnEntity(skull);

        this.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1.0f, 1.0f);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean success = super.tryAttack(target);

        if (success && target instanceof LivingEntity livingTarget) {
            livingTarget.addStatusEffect(
                new StatusEffectInstance(StatusEffects.WITHER, 100, 1) // 5 sec Wither II
            );
        }

        return success;
    }

    @Override
    protected boolean shouldDropLoot() {
        return true;
    }
}
