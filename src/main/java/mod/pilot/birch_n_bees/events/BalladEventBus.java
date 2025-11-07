package mod.pilot.birch_n_bees.events;


import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.BirchEntities;
import mod.pilot.birch_n_bees.entity.mob.SplinteringEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ABalladofBirchandBees.MOD_ID)
public class BalladEventBus {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(BirchEntities.SPLINTERING.get(), SplinteringEntity.createAttributes().build());
    }
}
