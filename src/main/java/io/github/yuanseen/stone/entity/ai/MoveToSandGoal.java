package io.github.yuanseen.stone.entity.ai;

import io.github.yuanseen.stone.entity.CapybaraEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

import java.util.EnumSet;

public class MoveToSandGoal extends MoveToBlockGoal {

    protected int verticalSearchStart;
    private CapybaraEntity capybara;

    public MoveToSandGoal(CapybaraEntity capybara, double pSpeedModifier, int pSearchRange) {
        super(capybara, pSpeedModifier, pSearchRange, 10);
        this.capybara = capybara ;
        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return !this.capybara.isTame() && !this.capybara.isOrderedToSit() && !this.capybara.isLying() && super.canUse();
    }

    @Override
    public void start() {
        super.start();
        this.capybara.setInSittingPose(false);
    }

    @Override
    protected int nextStartTick(PathfinderMob pCreature) {
        return 20;
    }

    @Override
    public void stop() {
        super.stop();
        this.capybara.setLying(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.capybara.setInSittingPose(false);
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        return pLevel.isEmptyBlock(pPos.above()) && pLevel.getBlockState(pPos).is(BlockTags.SAND)
                &&(pLevel.isWaterAt(pPos.north())||pLevel.isWaterAt(pPos.south())||pLevel.isWaterAt(pPos.west())||pLevel.isWaterAt(pPos.east())) ;
    }

}
