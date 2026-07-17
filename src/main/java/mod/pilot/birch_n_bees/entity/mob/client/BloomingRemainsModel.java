package mod.pilot.birch_n_bees.entity.mob.client;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.mob.BloomingRemainsEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BloomingRemainsModel extends DefaultedEntityGeoModel<BloomingRemainsEntity> {
    public BloomingRemainsModel() {
        super(ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, "blooming_remains"));
    }
}
