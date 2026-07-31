package com.archers_expansion.client.entity;

import com.archers_expansion.entity.PolarBearEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class SpellPolarBearRenderer extends MobEntityRenderer<PolarBearEntity, GlacialBearEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(MOD_ID, "textures/entity/spell_polar_bear.png");

    public SpellPolarBearRenderer(EntityRendererFactory.Context context) {
        super(context, new GlacialBearEntityModel(context.getPart(GlacialBearEntityModel.LAYER_LOCATION)), 0.9F);
    }

    @Override
    public Identifier getTexture(PolarBearEntity entity) {
        return TEXTURE;
    }
}
