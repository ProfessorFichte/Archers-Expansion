package com.archers_expansion.client.render;

import net.minecraft.client.render.*;
import net.minecraft.client.texture.SpriteAtlasTexture;

public class NonGlowingProjectileLayer extends RenderLayer {
    public NonGlowingProjectileLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode,
                                      int expectedBufferSize, boolean hasCrumbling, boolean translucent,
                                      Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderLayer nonGlowingProjectile() {
        var texture = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .program(ENTITY_TRANSLUCENT_PROGRAM)
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(NO_TRANSPARENCY)
                .cull(DISABLE_CULLING)
                .writeMaskState(ALL_MASK)
                .overlay(ENABLE_OVERLAY_COLOR)
                .lightmap(ENABLE_LIGHTMAP)
                .target(PARTICLES_TARGET)
                .build(false);
        return RenderLayer.of("entity_translucent_emissive",
                VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS,
                256, true, true, multiPhaseParameters);
    }
}
