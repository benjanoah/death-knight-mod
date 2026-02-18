package com.benjanoah.deathknight;

import com.benjanoah.deathknight.entity.ModEntities;
import com.benjanoah.deathknight.item.ModItems;
import com.benjanoah.deathknight.world.structure.ModStructures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathKnightMod implements ModInitializer {
    public static final String MOD_ID = "death-knight-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Death Knight Mod initializing...");
        ModStructures.register();
        ModEntities.register();
        ModItems.register();

        // Voeg het kasteel toe aan de Soul Sand Valley biome
        BiomeModifications.addStructure(
            BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
            RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(MOD_ID, "death_knight_castle"))
        );

        LOGGER.info("Death Knight Mod ready! 💀⚔️");
    }
}
