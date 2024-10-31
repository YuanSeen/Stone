package io.github.yuanseen.stone.block.magic_block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MagicCricle extends Block {

    public static final IntegerProperty MAGICKITEMID = IntegerProperty.create("magic_item_id",0,64);
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MAGICKITEMID);
    }


//    @Override
//    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
//        if (!pPlayer.getAbilities().mayBuild) {
//            return InteractionResult.PASS;
//        } else {
//            pLevel.setBlock(pPos, pState.cycle(DELAY), 3);
//            return InteractionResult.sidedSuccess(pLevel.isClientSide);
//        }
//    }

    public MagicCricle(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(MAGICKITEMID,0));
    }
}
