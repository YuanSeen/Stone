package io.github.yuanseen.stone.entity.ai;

import io.github.yuanseen.stone.entity.CapybaraEntity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class LookForAndRideCapybara extends Goal{
    private CapybaraEntity capybara;
    private long lastCanUseCheck;
    private boolean canPenalize = false;
    private int ticksUntilNextPathRecalculation;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private final double speedModifier;
    private int ticksUntilNextAttack;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private CapybaraEntity target;


    private void setTarget(CapybaraEntity capybara){
        target = capybara;
    }

    public LookForAndRideCapybara(CapybaraEntity pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        this.capybara = pMob;
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
      if(this.capybara.isTame() && !this.capybara.isOrderedToSit() && !this.capybara.isLying() && this.capybara.isCanBeRide()){
          long i = this.capybara.level().getGameTime();
          if (i - this.lastCanUseCheck < 20L) {
              return false;
          } else {
              this.lastCanUseCheck = i;
              LivingEntity livingentity = this.capybara.getTarget();//获取目标生物
              setTarget((CapybaraEntity) livingentity);

              if (livingentity == null) {
                  return false;
              } else if (!livingentity.isAlive()) {
                  return false;
              } else if (!((CapybaraEntity) livingentity).isCanBeRide()){
                  return false;
              } else{
                  if (canPenalize) {
                      if (--this.ticksUntilNextPathRecalculation <= 0) {
                          this.path = this.capybara.getNavigation().createPath(livingentity, 0);
                          this.ticksUntilNextPathRecalculation = 4 + this.capybara.getRandom().nextInt(7);
                          return this.path != null;
                      } else {
                          return true;
                      }
                  }
                  this.path = this.capybara.getNavigation().createPath(livingentity, 0);
                  if (this.path != null) {
                      return true;
                  } else {
                      return this.capybara.isWithinMeleeRideRange((CapybaraEntity) livingentity);
                  }
              }
          }
      }
      return false;
    }

    @Override
    public void start() {
        this.capybara.getNavigation().moveTo(this.path, this.speedModifier);
//        this.capybara.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
//        if (this.capybara.isWithinMeleeRideRange(capybara)){
        CapybaraEntity capybara1 = (CapybaraEntity) this.capybara.getTarget();

//        CapybaraEntity capybaraVehicle = null;
//        boolean capybaraVehicleBoolean = false;
//        CapybaraEntity capybaraRolling = null;
//        boolean capybaraRollingBoolean = false;
//        if ((capybara1.getVehicle() != null)){
//            capybaraVehicle = (CapybaraEntity) capybara1.getVehicle();
//            capybaraVehicleBoolean = true;
//        }
//        if (capybara1.getControllingPassenger() != null){
//            capybaraRolling = (CapybaraEntity) capybara1.getControllingPassenger();
//            capybaraRollingBoolean = true;
//        }


//        if ((!(capybara1.getVehicle() != null)) && (!(capybara1.getControllingPassenger() != null))){//单单一个
//            capybara1.startRiding(this.capybara);
//        } else if ((capybara1.getVehicle() != null) && (!(capybara1.getControllingPassenger() != null))) {//有坐骑
//            capybara1.startRiding(this.capybara);
//        } else if ((!(capybara1.getVehicle() != null)) && ((capybara1.getControllingPassenger() != null))) {//被骑着
//            ((CapybaraEntity) capybara1).getControllingPassenger().startRiding(this.capybara);
//        }


        if (capybara1.getVehicle() != null ){
            if (capybara1.isCanBeRide()&&this.capybara.isWithinMeleeRideRange(capybara1)){
            CapybaraEntity capybara2 = (CapybaraEntity) capybara1.getVehicle();
            capybara2.setCanBeRide(false);
            capybara1.setCanBeRide(false);
            capybara.setCanBeRide(false);
            capybara2.startRiding(this.capybara);

            }
        }else
        if (capybara1.isCanBeRide()){
            capybara1.setCanBeRide(false);
            this.capybara.setCanBeRide(false);
            capybara.startRiding(capybara1);
        }

    }

    @Override
    public void stop() {
//        CapybaraEntity capybara = (CapybaraEntity) this.capybara.getTarget(); // 获取当前目标生物
//        //this.capybara.setAggressive(false); // 设置生物为非攻击状态
//        this.capybara.getNavigation().stop(); // 停止生物的移动
    }

    /**
     * 判断此任务是否需要每个游戏刻都更新。
     * 返回true表示每个游戏刻都需要调用updateTask方法来更新此任务的状态。
     * @return 总是返回true，因为近战攻击任务需要频繁更新以检查生物是否接近目标并准备攻击。
     */
    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * 每游戏刻的更新逻辑。
     * 检查目标是否有效，并据此更新生物的视线、路径规划和攻击行为。
     */
    @Override
    public void tick() {
        LivingEntity livingentity = this.capybara.getTarget(); // 获取当前目标生物
        if (livingentity != null) { // 如果存在目标
//            if (!this.capybara.isCanBeRide()){
//                Vec3 vec3 = livingentity.getDismountLocationForPassenger(this.capybara);
//                this.capybara.dismountTo(vec3.x, vec3.y+5, vec3.z);
//            }
//            this.capybara.getLookControl().setLookAt(livingentity, 30.0F, 30.0F); // 控制生物看向目标

            // 更新路径重新计算的时间间隔
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

            // 根据条件重新计算路径
            if ((this.followingTargetEvenIfNotSeen || this.capybara.getSensing().hasLineOfSight(livingentity))
                    && this.ticksUntilNextPathRecalculation <= 0
                    && (
                    this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
                            || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
                            || this.capybara.getRandom().nextFloat() < 0.05F
            )) {
                // 设置新的路径目标并重新计算路径
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.capybara.getRandom().nextInt(7);

                // 根据距离和路径规划情况调整路径重新计算的时间间隔
                // ...（省略了具体的调整逻辑）

                // 如果无法移动到目标，则增加路径重新计算的时间间隔
                if (!this.capybara.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                // 应用调整后的时间间隔
                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }

            // 更新攻击计时器并检查是否执行攻击
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
//            this.checkAndPerformAttack(livingentity);
        }


    }

}
