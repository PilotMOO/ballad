package mod.pilot.birch_n_bees.events;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.BirchEntities;
import mod.pilot.birch_n_bees.entity.client.mob.SplinteringModel;
import mod.pilot.birch_n_bees.entity.client.mob.SplinteringRenderer;
import mod.pilot.birch_n_bees.entity.projectiles.client.SplinterProjectileRenderer;
import mod.pilot.birch_n_bees.entity.projectiles.client.WildflowerPopperRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ABalladofBirchandBees.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientManager {
    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(BirchEntities.SPLINTER_PROJECTILE.get(), SplinterProjectileRenderer::new);
        event.registerEntityRenderer(BirchEntities.WILDFLOWER_POPPER_PROJECTILE.get(), WildflowerPopperRenderer::new);
        event.registerEntityRenderer(BirchEntities.SPLINTERING.get(), (context) -> new SplinteringRenderer<>(context, new SplinteringModel()));
    }
}
