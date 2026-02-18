package com.benjanoah.deathknight;

import com.benjanoah.deathknight.client.entity.model.DeathKnightModel;
import com.benjanoah.deathknight.client.entity.renderer.DeathKnightRenderer;
import com.benjanoah.deathknight.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class DeathKnightModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(
            DeathKnightModel.LAYER_LOCATION,
            DeathKnightModel::getTexturedModelData
        );
        EntityRendererRegistry.register(ModEntities.DEATH_KNIGHT, DeathKnightRenderer::new);
    }
}
