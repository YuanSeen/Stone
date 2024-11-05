package io.github.yuanseen.stone.block.magic_block;

import com.mojang.serialization.MapCodec;
import io.github.yuanseen.stone.block.ModBlockEntities;
import io.github.yuanseen.stone.block.entity.MagicCricleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class MagicCricle extends BaseEntityBlock {

    public static final IntegerProperty MAGICKITEMID = IntegerProperty.create("magic_item_id",0,64);
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,3,16);

    public static BlockPos getPos() {
        return pos;
    }

    public static void setPos(BlockPos pos) {
        MagicCricle.pos = pos;
    }

    private static BlockPos pos = new BlockPos(0, 0, 0);;


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MAGICKITEMID);
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = itemStack.getItem();
        setPos(pPos);
        MagicCricleBlockEntity blockEntity = (MagicCricleBlockEntity) pLevel.getBlockEntity(pPos);
        int id = 0;
            id = MagicItemIDToBe.getMagicItemID(item);
            pLevel.setBlock(pPos,pState.setValue(MAGICKITEMID,id),UPDATE_ALL);
            //全场唯一 setblock
        if (!pLevel.isClientSide()) {
            if (id != 0) {
                blockEntity.killItemEntity(pLevel,pPos);
                ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, pLevel);
                itemEntity.noPhysics = false;
                itemEntity.setItem(item.getDefaultInstance());
                itemEntity.setNeverPickUp();
                itemEntity.setPos(pPos.getX() + 0.5, pPos.getY() + 1, pPos.getZ() + 0.5);
                pLevel.addFreshEntity(itemEntity);
                itemStack.shrink(1);

                blockEntity.setItemEntityUUID(itemEntity.getUUID());

//                System.out.println("放入的是" + blockEntity.getUuid() + ")");
            } else {
                blockEntity.killItemEntity(pLevel,pPos);
            }
        }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.MAGIC_CRICLE_BLOCK_ENTITY.get(), MagicCricleBlockEntity::serverTick);
    }

    public static void updateOrDestroy(BlockState pOldState, BlockState pNewState, LevelAccessor pLevel, BlockPos pPos, int pFlags) {
        updateOrDestroy(pOldState, pNewState, pLevel, pPos, pFlags, 512);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {

    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {


    }


    public MagicCricle(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(MAGICKITEMID,0));
    }


    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker
    ) {
        return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MagicCricleBlockEntity(pPos,pState);
    }
}
