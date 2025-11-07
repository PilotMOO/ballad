package mod.pilot.birch_n_bees.entity.client.mob;

import mod.pilot.birch_n_bees.entity.mob.SplinteringEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class SplinteringRenderer<R extends LivingEntityRenderState & GeoRenderState>
        extends GeoEntityRenderer<SplinteringEntity, R> {
    public SplinteringRenderer(EntityRendererProvider.Context context,
                               GeoModel<SplinteringEntity> model) {
        super(context, model);
    }
}
