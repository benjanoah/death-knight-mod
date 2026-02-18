package com.benjanoah.deathknight.client.entity.renderer;

import com.benjanoah.deathknight.client.entity.model.DeathKnightModel;
import com.benjanoah.deathknight.entity.custom.DeathKnightEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DeathKnightRenderer extends MobEntityRenderer<DeathKnightEntity, DeathKnightModel<DeathKnightEntity>> {

    // Pad naar de texture van de Death Knight (jij maakt dit!)
    private static final Identifier TEXTURE = new Identifier(
        "death-knight-mod",
        "textures/entity/death_knight.png"
    );

    public DeathKnightRenderer(EntityRendererFactory.Context context) {
        super(
            context,
            new DeathKnightModel<>(context.getPart(DeathKnightModel.LAYER_LOCATION)),
            0.8f // schaduw grootte
        );
        // Zorgt dat de zeis in de hand wordt gerenderd
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(DeathKnightEntity entity) {
        return TEXTURE;
    }

    // Maak de Death Knight 2x groter dan een normale skeleton (= Warden-formaat)
    @Override
    protected void scale(DeathKnightEntity entity, MatrixStack matrices, float tickDelta) {
        matrices.scale(2.0f, 2.0f, 2.0f);
    }
}
