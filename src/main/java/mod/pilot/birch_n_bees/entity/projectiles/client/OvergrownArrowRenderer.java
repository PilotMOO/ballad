package mod.pilot.birch_n_bees.entity.projectiles.client;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.projectiles.OvergrownArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class OvergrownArrowRenderer extends ArrowRenderer<OvergrownArrowEntity, ArrowRenderState> {
    public static final ResourceLocation RESOURCE =
            ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID,
                    "textures/entity/overgrown_arrow.png");

    public OvergrownArrowRenderer(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    protected ResourceLocation getTextureLocation(ArrowRenderState arrowRenderState) {
        return RESOURCE;
    }

    @Override
    public @NotNull ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
