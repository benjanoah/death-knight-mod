package com.benjanoah.deathknight;

import com.benjanoah.deathknight.entity.ModEntities;
import com.benjanoah.deathknight.item.ModItems;
import com.benjanoah.deathknight.world.structure.ModStructures;
import net.fabricmc.api.ModInitializer;
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
        LOGGER.info("Death Knight Mod ready! 💀⚔️");
    }
}
