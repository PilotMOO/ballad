package mod.pilot.birch_n_bees.entity.mob.client;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.mob.NestHeadEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class NestHeadModel extends DefaultedEntityGeoModel<NestHeadEntity> {
    /*private static final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, "geo/entity/mob/splintering.geo.json");
    private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, "textures/entity/splintering.png");
    private static final ResourceLocation animation = ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, "animations/entity/mob/splintering.animation.json");*/

    public NestHeadModel() {
        super(ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, "nesthead"));
    }
}
