package mod.pilot.birch_n_bees.entity.mob;

import mod.pilot.birch_n_bees.entity.BirchEntities;
import mod.pilot.birch_n_bees.entity.projectiles.SplinterProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SplinteringEntity extends Creeper implements GeoEntity {
    public SplinteringEntity(EntityType<? extends Creeper> p_32278_, Level p_32279_) {
        super(p_32278_, p_32279_);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes(){
        return SplinteringEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 16D)
                .add(Attributes.ARMOR, 1)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0D)
                .add(Attributes.ATTACK_SPEED, 2D);
    }

    public static final EntityDataAccessor<Integer> data_SWELL = SynchedEntityData.defineId(SplinteringEntity.class, EntityDataSerializers.INT);
    public int getSwell(){return entityData.get(data_SWELL);}
    public void setSwell(int count) {
        entityData.set(data_SWELL, count);
    }
    public void syncSwell(){
        setSwell(swell);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("swell", getSwell());
    }
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSwell(tag.getInt("swell").orElse(0));
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(data_SWELL, 0);
    }

    public int swell, oldSwell;
    public static final int maxSwell = 30;


    //TODO: FIX THE EXPLODING LOGIC, PROBABLY JUST WRITE YOUR OWN RATHER THAN USING BASE CREEPER
    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited() || getSwellDir() != 0) {
                this.setSwell(this.swell = 1);
                setSwellDir(0);

                int i = this.getSwell();
                if (i > 0 && this.swell == 1) {
                    this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 1.0F);
                    this.gameEvent(GameEvent.PRIME_FUSE);
                }

                this.swell += i;
                if (this.swell < 0) {
                    this.swell = 0;
                }
                syncSwell();
            }

            if (this.swell >= maxSwell) {
                this.swell = maxSwell;
                this.explodeSplintering();
            }
        }

        super.tick();
    }


    public void explodeSplintering(){
        setSwell(swell = 0);
        this.dead = true;
        if (this.level() instanceof ServerLevel server){
            server.explode(this, getX(), getY(), getZ(),
                    .5f, Level.ExplosionInteraction.BLOCK);

            float xRot = 0, yRot = 0;
            for (int i = 0; i < 12; i++){
                xRot += 30f; //360f/12
                for (int j = 0; j < 8; j++){
                    yRot += 11.25f; //90f/8
                    getSplinter(server, this, xRot, yRot);
                }
            }
            this.triggerOnDeathMobEffects(server, RemovalReason.KILLED);
            this.discard();
        }

    }

    public static SplinterProjectileEntity getSplinter(ServerLevel server, Entity from, float xRot, float yRot){
        SplinterProjectileEntity splinter = new SplinterProjectileEntity(BirchEntities.SPLINTER_PROJECTILE.get(), server);
        splinter.copyPosition(from);
        splinter.move(MoverType.SELF, new Vec3(0, from.getEyeHeight(), 0));
        splinter.setNoPickup(server.random.nextDouble() > 0.05);
        splinter.setOwner(from);
        splinter.setDamage(1f);
        splinter.setIgnoreIFrames();
        splinter.shootFromRotation(from, xRot, yRot, 0.0F, 1.0F, 5.0F);
        server.addFreshEntity(splinter);
        return splinter;
    }

    public boolean isMoving(){
        Vec3 delta = getDeltaMovement();
        return delta.x != 0 || delta.z != 0;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("splintering", event -> {
            if (swell != 0){
                return event.setAndContinue(RawAnimation.begin().thenLoop("explode"));
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
}
