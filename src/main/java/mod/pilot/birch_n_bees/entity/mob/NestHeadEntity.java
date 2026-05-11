package mod.pilot.birch_n_bees.entity.mob;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class NestHeadEntity extends Zombie implements GeoEntity {
    public NestHeadEntity(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
        super(p_34271_, p_34272_);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes(){
        return NestHeadEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 22D)
                .add(Attributes.ARMOR, 8)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 6D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_SPEED, 2D);
    }

    public static final int DEFAULT_BEES = 5;
    public static final EntityDataAccessor<Integer> BEES = SynchedEntityData.defineId(NestHeadEntity.class,
            EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_BEES = SynchedEntityData.defineId(NestHeadEntity.class,
            EntityDataSerializers.INT);
    public int getStoredBees() {return entityData.get(BEES);}
    public void setStoredBees(int count) {entityData.set(BEES, count);}
    public boolean maxBees(){ return getStoredBees() >= getMaxBees();}
    public void incBees() {
        int bees = getStoredBees();
        int max_bees = getMaxBees();
        if (++bees <= max_bees) setStoredBees(bees);
    }
    public void decBees() {
        int bees = getStoredBees();
        if (--bees < 0) setStoredBees(bees);
    }

    public int getMaxBees(){return entityData.get(MAX_BEES);}
    public void setMaxBees(int count) {
        entityData.set(MAX_BEES, count);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("bees", getStoredBees());
        tag.putInt("max_bees", getMaxBees());
    }
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setStoredBees(tag.getInt("bees").orElse(DEFAULT_BEES));
        setMaxBees(tag.getInt("max_bees").orElse(DEFAULT_BEES));
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BEES, DEFAULT_BEES);
        builder.define(MAX_BEES, DEFAULT_BEES);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();

        goalSelector.addGoal(4, new CollectBeesGoal(this));
    }

    @Override
    protected void tickDeath() {
        //Manually change the death timer from 1 sec (20 tick) to 2.5 sec (50 tick) to work with death animation
        ++this.deathTime;
        if (this.deathTime >= 50 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(RemovalReason.KILLED);
        }
    }

    public boolean isMoving(){
        Vec3 delta = getDeltaMovement();
        return delta.x != 0 || delta.z != 0;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("nesthead", 2, event -> {
            if (isDeadOrDying()){
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("die"));
            }
            else if (isMoving()){
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }));
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }
    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }
    @Override
    protected @NotNull SoundEvent getAmbientSound() {
        return SoundEvents.BEEHIVE_WORK;
    }
    @Override
    protected @NotNull SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }

    public static boolean nestHeadSpawnCheck(EntityType<? extends Monster> entityType, ServerLevelAccessor level,
                                                EntitySpawnReason spawnReason, BlockPos pos, RandomSource random){
        return level.getBiome(pos).is(Tags.Biomes.IS_BIRCH_FOREST) && Monster.checkMonsterSpawnRules(entityType, level, spawnReason, pos, random);
    }

    public static class CollectBeesGoal extends Goal{
        public final NestHeadEntity entity;
        public Bee target;
        public BlockPos nest;
        public CollectBeesGoal(NestHeadEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return !entity.maxBees() && entity.getTarget() == null;
        }

        @Override
        public boolean canContinueToUse() {
            return (target != null || nest != null) && !entity.maxBees();
        }

        @Override
        public void start() {
            AABB area = entity.getBoundingBox().inflate(16d, 4d, 16d);
            List<Bee> bees = entity.level().getEntities(EntityType.BEE, area,
                    (bee) -> bee.getTarget() == null);
            if (bees.isEmpty()){
                BlockPos pos = entity.blockPosition();
                Level level = entity.level();
                ArrayList<BlockPos> nests = new ArrayList<>();
                for (int x = -16; x <= 16; x++) {
                    for (int y = -16; y <= 16; y++) {
                        for (int z = -16; z <= 16; z++) {
                            BlockPos bPos = pos.offset(x,y,z);
                            BlockState bState = level.getBlockState(bPos);
                            if (bState.is(Blocks.BEE_NEST)) {
                                BeehiveBlockEntity beehive = (BeehiveBlockEntity) level.getBlockEntity(bPos);
                                if (beehive != null && beehive.getOccupantCount() > 0) nests.add(bPos);
                            }
                        }
                    }
                }
                if (!nests.isEmpty()) {
                    BlockPos bPos = null;
                    double dist = Double.MAX_VALUE;
                    for (BlockPos bPos1 : nests) {
                        double dist1 = entity.distanceToSqr(bPos1.getCenter());
                        if (dist1 < dist) {
                            dist = dist1;
                            bPos = bPos1;
                        }
                    }
                    if ((nest = bPos) != null) {
                        entity.navigation.moveTo(nest.getX(), nest.getY(), nest.getZ(), 2, 1d);
                    }
                }
            }
            else{
                Bee bee = null;
                double dist = Double.MAX_VALUE;
                for (Bee bee1 : bees) {
                    double dist1 = entity.distanceTo(bee1);
                    if (dist1 < dist) {
                        dist = dist1;
                        bee = bee1;
                    }
                }
                if (bee != null) entity.navigation.moveTo(target = bee, 1d);
            }

        }

        @Override
        public void tick() {
            PathNavigation nav = entity.navigation;
            if (nav.isStuck()) {
                stop();
                return;
            }

            if (target != null){
                if (entity.distanceTo(target) < 2.5d){
                    entity.incBees();
                    target.discard();
                    entity.level().playLocalSound(entity, SoundEvents.BEEHIVE_EXIT, SoundSource.HOSTILE, 1f, 1.25f);
                    stop();
                } else if (nav.isDone()) nav.moveTo(target, 1d);
            } else if (nest != null){
                if (entity.distanceToSqr(nest.getCenter()) < 6.5d){
                    Level level = entity.level();
                    BlockEntity bEntity = entity.level().getBlockEntity(nest);
                    if (bEntity instanceof BeehiveBlockEntity beehive) {
                        if (beehive.getOccupantCount() > 0) {
                            boolean collectFlag = false;
                            int toCollect = entity.getMaxBees() - entity.getStoredBees();
                            beehive.emptyAllLivingFromHive(null, level.getBlockState(nest), BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
                            for (Bee bee : level.getEntities(EntityType.BEE, AABB.ofSize(nest.getCenter(), 2, 2,2), (b) -> true)){
                                if (--toCollect <= 0) break;
                                bee.discard();
                                entity.incBees();
                                collectFlag = true;
                            }
                            if (collectFlag) level.playLocalSound(nest, SoundEvents.BEEHIVE_EXIT, SoundSource.HOSTILE, 1f, 1.25f, true);
                            stop();
                        }
                    }
                }
            } else stop();
        }

        @Override
        public void stop() {
            target = null;
            nest = null;
            entity.navigation.stop();
        }
    }
}
