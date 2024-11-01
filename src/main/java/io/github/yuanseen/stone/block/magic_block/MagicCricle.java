package io.github.yuanseen.stone.block.magic_block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.UUID;

import static net.minecraft.world.entity.Entity.RemovalReason.UNLOADED_TO_CHUNK;

public class MagicCricle extends Block {

    public static final IntegerProperty MAGICKITEMID = IntegerProperty.create("magic_item_id",0,64);
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,3,16);

    private static BlockPos pos = new BlockPos(0, 0, 0);;
    @Nullable
    private UUID itemEntityUUID;

    public void setItemEntityUUID(ItemEntity itemEntity) {
        this.itemEntityUUID = itemEntity.getUUID();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MAGICKITEMID);
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = itemStack.getItem();
        int id = 0;
            id = MagicItemIDToBe.getMagicItemID(item);
            pLevel.setBlock(pPos,pState.setValue(MAGICKITEMID,id),2);
        if (id!=0){
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM,pLevel);
            setItemEntityUUID(itemEntity);
            itemEntity.setItem(item.getDefaultInstance());
            itemEntity.setNeverPickUp();
            itemEntity.setPos(pPos.getX()+0.5,pPos.getY()+1,pPos.getZ()+0.5);
            pLevel.addFreshEntity(itemEntity);
            itemStack.shrink(1);
            }else{
            killItemEntity(this.itemEntityUUID,pLevel);
        }

            return InteractionResult.sidedSuccess(pLevel.isClientSide);

    }

    public void killItemEntity(UUID beKillTtemEntityUUID,Level pLevel){
        if (beKillTtemEntityUUID != null) {
            Level level = pLevel;
            if (level instanceof ServerLevel serverlevel) {
                serverlevel.getEntity(beKillTtemEntityUUID).remove(UNLOADED_TO_CHUNK);
            }
        }
    }


    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        killItemEntity(this.itemEntityUUID, (Level) pLevel);

    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        killItemEntity(this.itemEntityUUID,pLevel);
    }


    public MagicCricle(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(MAGICKITEMID,0));
    }
}
