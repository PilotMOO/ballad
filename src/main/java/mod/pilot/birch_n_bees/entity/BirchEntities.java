package mod.pilot.birch_n_bees.entity;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.entity.mob.SplinteringEntity;
import mod.pilot.birch_n_bees.entity.projectiles.SplinterProjectileEntity;
import mod.pilot.birch_n_bees.entity.projectiles.WildflowerPopperProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BirchEntities {
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(ABalladofBirchandBees.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<SplinterProjectileEntity>> SPLINTER_PROJECTILE =
            ENTITIES.registerEntityType("splinter", SplinterProjectileEntity::new, MobCategory.MISC,
                    (builder -> builder.sized(.5f, .5f)));

    public static final DeferredHolder<EntityType<?>, EntityType<WildflowerPopperProjectileEntity>> WILDFLOWER_POPPER_PROJECTILE =
            ENTITIES.registerEntityType("wildflower_popper", WildflowerPopperProjectileEntity::new, MobCategory.MISC,
                    (builder -> builder.sized(.3f, .3f)));

    //Entities
    public static final DeferredHolder<EntityType<?>, EntityType<SplinteringEntity>> SPLINTERING =
            ENTITIES.registerEntityType("splintering", SplinteringEntity::new, MobCategory.MONSTER,
                    (builder -> builder.sized(.6f, 2.25f)));
}
