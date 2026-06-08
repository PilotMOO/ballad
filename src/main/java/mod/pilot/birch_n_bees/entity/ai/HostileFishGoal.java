package mod.pilot.birch_n_bees.entity.ai;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class HostileFishGoal extends HostileMeleeGoal {
    public static final Predicate<LivingEntity> WATER_HOSTILE_PREDICATE =
            (target) -> target instanceof Player player
                    && player.canBeSeenAsEnemy() && player.isInWaterOrRain();
    public static final Predicate<LivingEntity> SALMON_HOSTILE_PREDICATE =
            (target) -> target instanceof Player player
                    && player.canBeSeenAsEnemy() && (player.isInWaterOrRain() || player.getMainHandItem().getItem() == Items.SALMON);

    public HostileFishGoal(Mob mob) {
        super(mob, WATER_HOSTILE_PREDICATE);
    }
    public HostileFishGoal(Mob mob, float damage, double followRange, double bonusRange, double speed, int maxTimeNotVisible) {
        this(mob, damage, followRange, bonusRange, speed, maxTimeNotVisible, WATER_HOSTILE_PREDICATE);
    }
    public HostileFishGoal(Mob mob, float damage, double followRange, double bonusRange, double speed, int maxTimeNotVisible,
                           Predicate<LivingEntity> targetPredicate) {
        super(mob, damage, followRange, bonusRange, speed, maxTimeNotVisible, targetPredicate);
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity target = mob.getTarget();
        if (target != null && mob.invulnerableTime == 0 && mob.isInWaterOrRain()) {
            Vec3 delta = mob.getDeltaMovement();
            Vec3 to = target.position().subtract(mob.position()).normalize().scale(speed);
            mob.setDeltaMovement(new Vec3(
                    Mth.lerp(0.5d, delta.x, to.x),
                    Mth.lerp(0.5d, delta.y, to.y),
                    Mth.lerp(0.5d, delta.z, to.z)));
        }
    }
}
