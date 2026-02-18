package com.benjanoah.deathknight.entity.custom;

import net.minecraft.item.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class DeathKnightEntity extends HostileEntity {

    // Cooldowns (in ticks, 20 ticks = 1 second)
    private int witherSkullCooldown = 0;
    private static final int WITHER_SKULL_COOLDOWN_MAX = 80; // 4 seconds between skulls

    public DeathKnightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        // Geef de Death Knight een netherite sword
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
    }

    @Override
    protected void initGoals() {
        // Prioriteit 1: Zwemmen (niet verdrinken)
        this.goalSelector.add(1, new SwimGoal(this));

        // Prioriteit 2: Melee aanval (scythe swipe)
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2, false));

        // Prioriteit 3: Rondwandelen
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8));

        // Prioriteit 4: Kijken naar spelers
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));

        // Targets: aanvallen spelers + wraak nemen
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createDeathKnightAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 150.0)          // 150 HP (super sterk!)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)          // 8 schade per klap
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)        // Gemiddeld snel
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)          // Ziet ver
            .add(EntityAttributes.GENERIC_ARMOR, 4.0)                  // Beetje armor
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6);  // Wordt moeilijk weggeslagen
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient) {
            // Cooldown afbouwen
            if (witherSkullCooldown > 0) {
                witherSkullCooldown--;
            }

            // Wither skull aanval als target ver genoeg is
            LivingEntity target = this.getTarget();
            if (target != null && witherSkullCooldown <= 0) {
                double distanceSq = this.squaredDistanceTo(target);
                // Schiet als target 5-30 blokken ver is
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

        // Maak een wither skull aan (false = zwarte skull, true = blauwe charged skull)
        WitherSkullEntity skull = new WitherSkullEntity(world, this, dx, dy, dz);
        skull.setPosition(this.getX(), this.getBodyY(0.5), this.getZ());

        world.spawnEntity(skull);

        // Speel het wither skull geluid
        this.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1.0f, 1.0f);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean success = super.tryAttack(target);

        // Bij een succesvolle scythe aanval: geef Wither effect!
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
