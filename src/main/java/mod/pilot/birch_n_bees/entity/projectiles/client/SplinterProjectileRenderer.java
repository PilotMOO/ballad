package mod.pilot.birch_n_bees.entity.projectiles.client;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.projectiles.SplinterProjectileEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SplinterProjectileRenderer extends ArrowRenderer<SplinterProjectileEntity, ArrowRenderState> {
    public SplinterProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    private static final ResourceLocation texture =
            ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, "textures/entity/splinter_projectile.png");
    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull ArrowRenderState arrowRenderState) {
        return texture;
    }

    @Override
    public @NotNull ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
