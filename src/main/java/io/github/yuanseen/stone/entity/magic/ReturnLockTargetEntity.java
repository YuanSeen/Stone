package io.github.yuanseen.stone.entity.magic;

import io.github.yuanseen.stone.block.ModBlocks;
import io.github.yuanseen.stone.block.entity.MagicCricleBlockEntity;
import io.github.yuanseen.stone.entity.ModEntityTypes;
import io.github.yuanseen.stone.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import static net.minecraft.world.entity.Entity.RemovalReason.UNLOADED_TO_CHUNK;
import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class ReturnLockTargetEntity extends AbstractArrow {
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ReturnLockTargetEntity.class, EntityDataSerializers.BOOLEAN);
    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(Items.TRIDENT);
    private boolean dealtDamage;

    private int cricleHowBig = 0;

    public int clientSideReturnTridentTickCount;

    public ReturnLockTargetEntity(EntityType<? extends ReturnLockTargetEntity> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_, DEFAULT_ARROW_STACK);
    }

    public ReturnLockTargetEntity(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(ModEntityTypes.RETURN_LOCK_TARGET_ENTITY.get(), pShooter, pLevel, pStack);
        this.entityData.set(ID_FOIL, pStack.hasFoil());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
            this.remove(UNLOADED_TO_CHUNK);
        }

        Entity entity = this.getOwner();
        super.tick();
    }


    /**
     * Gets the EntityHitResult representing the entity hit
     */
    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        // 获取被击中的实体
        Entity entity = pResult.getEntity();
        // 获取投掷物的拥有者
        Entity entity1 = this.getOwner();
        // 标记投掷物已经造成了伤害
        this.dealtDamage = true;
        // 初始化声音事件为三叉戟击中的声音
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;

//        if (entity.hurt(damagesource, f)) {
            // 如果被击中实体是末影人，则直接返回
//            if (entity.getType() == EntityType.ENDERMAN) {
//                return;
//            }
            // 如果被击中实体是生物实体，并且拥有者也是生物实体
            if (entity instanceof LivingEntity livingentity1) {
                if (entity1 instanceof LivingEntity) {
                    if (this.level() instanceof ServerLevel ) {
                        // 获取被击中实体的方块位置
                        BlockPos blockposWillBeAttach = entity.blockPosition();
                        BlockPos blockPosPlayer = entity1.blockPosition();
                        nullMagicCricleSpawn3(blockposWillBeAttach,blockPosPlayer,livingentity1);
                        this.remove(UNLOADED_TO_CHUNK);
                    }
                }
            }
//        }
//        else if (entity.getType().is(EntityTypeTags.DEFLECTS_TRIDENTS)) {
//            this.deflect();
//            return;
//        }
        // 修改投掷物的移动向量，使其稍微减速
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        // 初始化声音音量
        // 播放相应的声音
        this.playSound(soundevent, 1, 1.0F);
    }

    //生成空白法阵组 3 阶

    public void nullMagicCricleSpawn3(BlockPos blockposWillBeAttach,BlockPos blockPosPlayer,Entity attachEntity){
        int x,z;
        if (blockposWillBeAttach.getX()-blockPosPlayer.getX()>0){
            x = blockPosPlayer.getX()+1;
        }else {
            x = blockPosPlayer.getX()-1;
        }
        if (blockposWillBeAttach.getZ()-blockPosPlayer.getZ()>0){
            z = blockPosPlayer.getZ()+1;
        }else {
            z = blockPosPlayer.getZ()-1;
        }

        BlockPos middleBlockpos = new BlockPos(x,blockPosPlayer.getY(),z);
        for (int i = -1 ; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                BlockPos setblockPos = new BlockPos(x+i,blockPosPlayer.getY(),z+j);
                this.level().setBlock(setblockPos, ModBlocks.MAGIC_CIRCLE.get().defaultBlockState(), UPDATE_ALL);
                MagicCricleBlockEntity blockEntity = (MagicCricleBlockEntity) this.level().getBlockEntity(setblockPos);
                blockEntity.setAttachEntityUUID(attachEntity.getUUID());
                blockEntity.setBlockPosArray(middleBlockpos);
                blockEntity.setCricleHowBig(getCricleHowBig());
            }
        }
    }




    @Override
    protected boolean tryPickup(Player pPlayer) {
        return false;
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void playerTouch(Player pEntity) {
        if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.dealtDamage = pCompound.getBoolean("DealtDamage");

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void tickDespawn() {
    }


//    @Override
//    public boolean shouldRender(double pX, double pY, double pZ) {
//        return false;
//    }
public int getCricleHowBig() {
    return cricleHowBig;
}

    public void setCricleHowBig(int cricleHowBig) {
        this.cricleHowBig = cricleHowBig;
    }
}
