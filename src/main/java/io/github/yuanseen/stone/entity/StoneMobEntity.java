package io.github.yuanseen.stone.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.UUID;


public class StoneMobEntity extends Monster {
    private int offerFlowerTick;
    private int attackAnimationTick;
    //LOGGER的Logger对象，用于记录日志信息。
//    private static final Logger LOGGER = LogUtils.getLogger();
//    //COUNTER的实体数据访问器，用于存储实体的计数器数据。
//    private static final EntityDataAccessor<Integer> COUNTER = SynchedEntityData.defineId(StoneMobEntity.class, EntityDataSerializers.INT);
//    @Override
//    public void tick() {
//        //检查当前是否为客户端，如果是，则从实体数据中获取计数器数据并记录日志信息。如果不是客户端，则从实体数据中获取计数器数据，记录日志信息，并将计数器数据加1。最后，调用父类的tick()方法。
//        // 说的明白一些就是服务器将计数+1，然后进行数据的同步，在客户端打印出来。
//        if(this.level().isClientSide){
//            Integer i = this.entityData.get(COUNTER);
//            LOGGER.info(i.toString());
//        }
//        if(!this.level().isClientSide){
//            LOGGER.info(this.entityData.get(COUNTER).toString());
//            this.entityData.set(COUNTER,this.entityData.get(COUNTER)+1);
//        }
//        super.tick();
//    }

    public static AttributeSupplier.Builder createAttributes() {
        // 创建一个AttributeSupplier.Builder实例，并基于Monster的基础属性进行配置
        return Monster.createMonsterAttributes()
                // 设置跟随范围（玩家距离多远时，会跟随玩家）
                .add(Attributes.FOLLOW_RANGE, 35.0)
                // 设置移动速度
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                // 设置攻击伤害
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                // 注意：这里可能是一个错误，通常应该是 ATTRIBUTES_ARMOR_TOUGHNESS（防御韧性）或 ATTRIBUTES_ARMOR（防御值），但Java的Minecraft API中没有直接的ATTRIBUTES_ARMOR
                // 这里我假设你是想设置防御韧性，但正确的名称可能是不同的
                .add(Attributes.ARMOR_TOUGHNESS, 2.0) // 假设这里是防御韧性，而不是ARMOR
                ;
    }

    @Override
    protected void registerGoals() {
        // 添加近战攻击目标，距离1.0D，且不会追击玩家
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
        // 添加寻找最近的玩家作为攻击目标的策略
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        // 添加受伤时寻找攻击者的目标
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));


    }

    //构造方法
    public StoneMobEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super( pEntityType,
                pLevel);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }

        if (this.offerFlowerTick > 0) {
            --this.offerFlowerTick;
        }
    }
    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }
    public int getOfferFlowerTick() {
        return this.offerFlowerTick;
    }
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        this.attackAnimationTick = 10;
        this.level().broadcastEntityEvent(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), f1);
        if (flag) {
            double d0 = pEntity instanceof LivingEntity livingentity ? livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 0.0;
            double d1 = Math.max(0.0, 1.0 - d0);
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(0.0, 0.1F * d1, 0.0));
            this.doEnchantDamageEffects(this, pEntity);
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }
    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        IronGolem.Crackiness irongolem$crackiness = this.getCrackiness();
        boolean flag = super.hurt(pSource, pAmount);

        return flag;
    }

    public IronGolem.Crackiness getCrackiness() {
        return IronGolem.Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
    }



    //defineSynchedData()：该方法用于定义实体的同步数据，在该方法中，将COUNTER实体数据访问器初始化为0。
//    @Override
//    protected void defineSynchedData() {
//        this.entityData.define(COUNTER, 0);
//
//    }
//    //readAdditionalSaveData()：该方法用于从NBT标签中读取额外的保存数据，在该方法中，从NBT标签中读取计数器数据，并保存到实体数据中。
//    @Override
//    public void readAdditionalSaveData(CompoundTag pCompound) {
//        this.entityData.set(COUNTER,pCompound.getInt("counter"));
//    }
//    //addAdditionalSaveData()：该方法用于向NBT标签中添加额外的保存数据，在该方法中，将计数器数据保存到NBT标签中。
//    @Override
//    public void addAdditionalSaveData(CompoundTag pCompound) {
//        pCompound.putInt("counter",this.entityData.get(COUNTER));
//    }

    protected void addBehaviourGoals() {

        // 添加一个目标，使避免水并随机游荡，优先级为7
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        // 添加一个目标，使攻击最近的玩家，优先级为2
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        // 添加一个目标，使攻击最近的铁傀儡，并会主动追踪，优先级也为3（与村民目标同级）
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));

    }



}
