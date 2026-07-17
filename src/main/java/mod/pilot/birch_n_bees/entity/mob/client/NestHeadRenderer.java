package mod.pilot.birch_n_bees.entity.mob.client;

import mod.pilot.birch_n_bees.entity.mob.NestHeadEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class NestHeadRenderer<R extends LivingEntityRenderState & GeoRenderState>
        extends GeoEntityRenderer<NestHeadEntity, R> {
    public NestHeadRenderer(EntityRendererProvider.Context context,
                            GeoModel<NestHeadEntity> model) {
        super(context, model);
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0f;
    }
}
