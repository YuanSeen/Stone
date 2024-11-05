package io.github.yuanseen.stone.block.entity;

import io.github.yuanseen.stone.block.ModBlockEntities;
import io.github.yuanseen.stone.block.magic_block.MagicCricle;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.UUID;

import static io.github.yuanseen.stone.block.magic_block.MagicCricle.MAGICKITEMID;
import static net.minecraft.world.entity.Entity.RemovalReason.KILLED;
import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class MagicCricleBlockEntity extends BlockEntity {
    private static final int MAX_TIME = 10 * 20;
    private int timer = 0;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    private UUID uuid = UUID.randomUUID();

    public void killItemEntity(Level pLevel,BlockPos pPos){
        if (getUuid() != null  ) {
            Level level = pLevel;
            if (level instanceof ServerLevel serverlevel) {
                if(serverlevel.getEntity(getUuid()) != null) {
                    serverlevel.getEntity(getUuid()).remove(KILLED);
//                    System.out.println("摧毁的是"+getUuid()+")");
                }
            }
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        timer = pTag.getInt("counter");
        uuid = pTag.getUUID("uuid");
        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("counter",timer);
        pTag.putUUID("uuid",uuid);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, MagicCricleBlockEntity pBlockEntity) {
        if(pLevel!=null && !pLevel.isClientSide ){
            if (pBlockEntity.getUuid()!=null && ((ServerLevel)pLevel).getEntity(pBlockEntity.getUuid())==null){
                pLevel.setBlock(pPos,pState.setValue(MAGICKITEMID,0),UPDATE_ALL
                );
            }
//            //计时自毁
            if(pBlockEntity.timer == MagicCricleBlockEntity.MAX_TIME){
                //移除展示物品
                if (pBlockEntity.getUuid()!=null && ((ServerLevel)pLevel).getEntity(pBlockEntity.getUuid())!=null){
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
}
