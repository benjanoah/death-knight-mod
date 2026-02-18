package com.benjanoah.deathknight.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class DeathKnightCastleStructure extends Structure {

    public static final Codec<DeathKnightCastleStructure> CODEC =
        Structure.settingsCodec(DeathKnightCastleStructure::new);

    private static final Identifier CASTLE_ID =
        new Identifier("death-knight-mod", "death_knight_castle");

    public DeathKnightCastleStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        ChunkPos chunkPos = context.chunkPos();
        int x = chunkPos.getStartX();
        int z = chunkPos.getStartZ();

        // Zoek de vloerhoogte op (OCEAN_FLOOR werkt ook in de Nether)
        int floorY = context.chunkGenerator().getHeightInGround(
            x, z,
            Heightmap.Type.OCEAN_FLOOR_WG,
            context.world(),
            context.noiseConfig()
        );

        // Alleen spawnen als de hoogte geldig is voor Soul Sand Valley (Y 20-100)
        // Als het te hoog of laag is, sla dan dit stuk over (spawn ergens anders)
        if (floorY < 20 || floorY > 100) {
            return Optional.empty();
        }

        BlockPos spawnPos = new BlockPos(x, floorY, z);

        return Optional.of(new StructurePosition(spawnPos, collector ->
            collector.addPiece(new DeathKnightCastlePiece(
                context.structureTemplateManager(),
                CASTLE_ID,
                spawnPos
            ))
        ));
    }

    @Override
    public StructureType<?> getType() {
        return ModStructures.DEATH_KNIGHT_CASTLE_TYPE;
    }
}
