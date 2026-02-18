package com.benjanoah.deathknight.entity;

import com.benjanoah.deathknight.DeathKnightMod;
import com.benjanoah.deathknight.entity.custom.DeathKnightEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<DeathKnightEntity> DEATH_KNIGHT = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(DeathKnightMod.MOD_ID, "death_knight"),
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DeathKnightEntity::new)
            .dimensions(EntityDimensions.fixed(1.4f, 2.9f))
            .build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(
            DEATH_KNIGHT,
            DeathKnightEntity.createDeathKnightAttributes()
        );
        DeathKnightMod.LOGGER.info("Death Knight entity registered!");
    }
}
