package io.github.yuanseen.stone.block.entity;

import io.github.yuanseen.stone.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

import static io.github.yuanseen.stone.block.magic_block.MagicCricle.MAGICKITEMID;
import static net.minecraft.world.entity.Entity.RemovalReason.KILLED;
import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class MagicCricleBlockEntity extends BlockEntity {
    private static final int MAX_TIME = 600 * 20;
    private int timer = 0;

    private UUID itemEntityUUID = UUID.randomUUID();

    private UUID attachEntityUUID = UUID.randomUUID();

    private UUID nullMagicCricleUUID = UUID.randomUUID();

    private int cricleHowBig = 0;

    private int[] blockPosArray = null ;

    private BlockPos middleMagicCricleBlockPos ;

    public void killItemEntity(Level pLevel,BlockPos pPos){
        if (getItemEntityUUID() != null  ) {
            Level level = pLevel;
            if (level instanceof ServerLevel serverlevel) {
                if(serverlevel.getEntity(getItemEntityUUID()) != null) {
                    serverlevel.getEntity(getItemEntityUUID()).remove(KILLED);
//                    System.out.println("摧毁的是"+getUuid()+")");
                }
            }
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        timer = pTag.getInt("counter");
        itemEntityUUID = pTag.getUUID("item_entity_uuid");
        attachEntityUUID = pTag.getUUID("attach_entity_uuid");
        nullMagicCricleUUID = pTag.getUUID("null_magic_cricle_uuid");
        blockPosArray = pTag.getIntArray("blockPosArray");
        this.middleMagicCricleBlockPos = new BlockPos(blockPosArray[0],blockPosArray[1],blockPosArray[2]);
        cricleHowBig = pTag.getInt("cricle_how_big");

        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("counter",timer);
        pTag.putUUID("item_entity_uuid", itemEntityUUID);
        pTag.putUUID("attach_entity_uuid",attachEntityUUID);
        pTag.putUUID("null_magic_cricle_uuid",nullMagicCricleUUID);
        pTag.putIntArray("blockPosArray",blockPosArray);
        pTag.putInt("cricle_how_big",cricleHowBig);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, MagicCricleBlockEntity pBlockEntity) {
        if(pLevel!=null && !pLevel.isClientSide ){
            if (pBlockEntity.getItemEntityUUID()!=null && ((ServerLevel)pLevel).getEntity(pBlockEntity.getItemEntityUUID())==null){
                pLevel.setBlock(pPos,pState.setValue(MAGICKITEMID,0),UPDATE_ALL
                );
            }
//            //计时自毁
            if(pBlockEntity.timer == MagicCricleBlockEntity.MAX_TIME){
                //移除展示物品
                if (pBlockEntity.getItemEntityUUID()!=null && ((ServerLevel)pLevel).getEntity(pBlockEntity.getItemEntityUUID())!=null){
                pBlockEntity.killItemEntity(pLevel,pPos);
                     }
                pLevel.destroyBlock(pPos,false);//移除方块
                pLevel.removeBlockEntity(pPos);//移除方块实体
                pBlockEntity.timer = 0;
            }
            pBlockEntity.timer++;
    }
}

    public MagicCricleBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MAGIC_CRICLE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public UUID getItemEntityUUID() {
        return itemEntityUUID;
    }

    public void setItemEntityUUID(UUID itemEntityUUID) {
        this.itemEntityUUID = itemEntityUUID;
    }

    public UUID getAttachEntityUUID() {
        return attachEntityUUID;
    }

    public void setAttachEntityUUID(UUID attachEntityUUID) {
        this.attachEntityUUID = attachEntityUUID;
    }

    public int[] getBlockPosArray() {
        return blockPosArray;
    }

    public void setBlockPosArray(BlockPos blockPosArrayOri) {
        int[] blockPosArray ={blockPosArrayOri.getX(),blockPosArrayOri.getY(),blockPosArrayOri.getZ()};
        this.blockPosArray = blockPosArray;
        this.middleMagicCricleBlockPos = new BlockPos(blockPosArray[0],blockPosArray[1],blockPosArray[2]);
    }

    public int getCricleHowBig() {
        return cricleHowBig;
    }

    public void setCricleHowBig(int cricleHowBig) {
        this.cricleHowBig = cricleHowBig;
    }

    public UUID getNullMagicCricleUUID() {
        return nullMagicCricleUUID;
    }

    public void setNullMagicCricleUUID(UUID nullMagicCricleUUID) {
        this.nullMagicCricleUUID = nullMagicCricleUUID;
    }
}
