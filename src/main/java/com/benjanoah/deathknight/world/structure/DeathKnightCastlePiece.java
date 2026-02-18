package com.benjanoah.deathknight.world.structure;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;

public class DeathKnightCastlePiece extends SimpleStructurePiece {

    public DeathKnightCastlePiece(StructureTemplateManager manager, Identifier id, BlockPos pos) {
        super(
            ModStructures.DEATH_KNIGHT_CASTLE_PIECE,
            0,
            manager,
            id,
            id.toString(),
            new StructurePlacementData().setRotation(BlockRotation.NONE),
            pos
        );
    }

    // Constructor voor deserialisatie (laden van wereld)
    public DeathKnightCastlePiece(StructureContext context, NbtCompound nbt) {
        super(ModStructures.DEATH_KNIGHT_CASTLE_PIECE, context, nbt);
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox) {
        // Niks te doen — geen speciale metadata blokken in het kasteel
    }
}
