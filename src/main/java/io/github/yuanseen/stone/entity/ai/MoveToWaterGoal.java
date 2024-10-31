package io.github.yuanseen.stone.entity.ai;

import io.github.yuanseen.stone.entity.CapybaraEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class MoveToWaterGoal extends PanicGoal {

    public static final int WATER_CHECK_DISTANCE_VERTICAL = 1;
    protected final PathfinderMob mob;
    protected final double speedModifier;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected boolean isRunning;

    public MoveToWaterGoal(CapybaraEntity pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
        this.mob = pMob;
        this.speedModifier = pSpeedModifier;
        this.setFlags(EnumSet.of(Flag.JUMP));
    }

    @Override
    protected boolean shouldPanic() {
        return this.mob.getLastHurtByMob() != null || this.mob.isFreezing() || this.mob.isOnFire()  ;
    }


    @Override
    public boolean canUse() {
        if (this.shouldPanic()) {
            if (this.mob.isInWater()) {
                BlockPos blockpos = this.lookForSand(this.mob.level(), this.mob, 5);
                if (blockpos != null) {
                    if (this.mob.level().isEmptyBlock(blockpos.above())){
                    this.posX = (double) blockpos.getX();
                    this.posY = (double) blockpos.getY();
                    this.posZ = (double) blockpos.getZ();
                    return true;}
                }
            } else {
                BlockPos blockpos = this.lookForWater(this.mob.level(), this.mob, 5);
                if (blockpos != null) {
                    if (this.mob.level().isEmptyBlock(blockpos.above())){
                    this.posX = (double) blockpos.getX();
                    this.posY = (double) blockpos.getY();
                    this.posZ = (double) blockpos.getZ();
                    return true;}else {
                        canUse();
                    }
                }
            }
        }return false;
    }
    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
        this.isRunning = true;
    }

    @Nullable
    protected BlockPos lookForSand(BlockGetter pLevel, Entity pEntity, int pRange) {
        BlockPos blockpos = pEntity.blockPosition();
        return (!pLevel.getBlockState(blockpos).getCollisionShape(pLevel, blockpos).isEmpty())
                ? null
                : BlockPos.findClosestMatch(pEntity.blockPosition(), pRange, 1, p_196649_ -> pLevel.getBlockState(p_196649_).is(BlockTags.SAND))
                .orElse(null);
    }

}
