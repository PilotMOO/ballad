package mod.pilot.birch_n_bees.events;


import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.BirchEntities;
import mod.pilot.birch_n_bees.entity.mob.SplinteringEntity;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = ABalladofBirchandBees.MOD_ID)
public class BalladEventBus {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(BirchEntities.SPLINTERING.get(), SplinteringEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerMobSpawningRules(RegisterSpawnPlacementsEvent event){
        event.register(BirchEntities.SPLINTERING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SplinteringEntity::splinteringSpawnCheck, RegisterSpawnPlacementsEvent.Operation.AND);
    }
}
