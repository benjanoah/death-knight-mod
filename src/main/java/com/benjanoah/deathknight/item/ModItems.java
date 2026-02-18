package com.benjanoah.deathknight.item;

import com.benjanoah.deathknight.DeathKnightMod;
import com.benjanoah.deathknight.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final SpawnEggItem DEATH_KNIGHT_SPAWN_EGG = Registry.register(
        Registries.ITEM,
        new Identifier(DeathKnightMod.MOD_ID, "death_knight_spawn_egg"),
        new SpawnEggItem(ModEntities.DEATH_KNIGHT, 0x1a1a2e, 0x8b0000, new FabricItemSettings())
    );

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries ->
            entries.add(DEATH_KNIGHT_SPAWN_EGG)
        );
    }
}
