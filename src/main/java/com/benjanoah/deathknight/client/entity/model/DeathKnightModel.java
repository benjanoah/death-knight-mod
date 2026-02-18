package com.benjanoah.deathknight.client.entity.model;

import com.benjanoah.deathknight.entity.custom.DeathKnightEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DeathKnightModel<T extends DeathKnightEntity> extends BipedEntityModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(
        new Identifier("death-knight-mod", "death_knight"),
        "main"
    );

    public DeathKnightModel(ModelPart root) {
        super(root);
    }

    // Gebruik het standaard biped (menselijk) model met 64x64 texture
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(new Dilation(0.0f), 0.0f);
        return TexturedModelData.of(modelData, 64, 64);
    }
}
