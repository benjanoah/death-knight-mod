package com.benjanoah.deathknight.world.structure;

import com.benjanoah.deathknight.DeathKnightMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {

    public static StructurePieceType DEATH_KNIGHT_CASTLE_PIECE;
    public static StructureType<DeathKnightCastleStructure> DEATH_KNIGHT_CASTLE_TYPE;

    public static void register() {
        // Registreer het stuk (piece) voor het kasteel
        DEATH_KNIGHT_CASTLE_PIECE = Registry.register(
            Registries.STRUCTURE_PIECE,
            new Identifier(DeathKnightMod.MOD_ID, "death_knight_castle_piece"),
            DeathKnightCastlePiece::new
        );

        // Registreer het structure type
        DEATH_KNIGHT_CASTLE_TYPE = Registry.register(
            Registries.STRUCTURE_TYPE,
            new Identifier(DeathKnightMod.MOD_ID, "death_knight_castle"),
            () -> DeathKnightCastleStructure.CODEC
        );

        DeathKnightMod.LOGGER.info("Death Knight structures registered!");
    }
}
