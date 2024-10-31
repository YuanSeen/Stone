package io.github.yuanseen.stone.entity;


import com.mojang.authlib.minecraft.client.MinecraftClient;
import io.github.yuanseen.stone.entity.ai.LookForAndRideCapybara;
import io.github.yuanseen.stone.entity.ai.MoveToSandGoal;
import io.github.yuanseen.stone.entity.ai.MoveToWaterGoal;
import io.github.yuanseen.stone.item.ModItems;
import io.github.yuanseen.stone.item.custom.CapybaraPokeBall;
import io.github.yuanseen.stone.utils.NBTHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.Main;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.yuanseen.stone.item.custom.CapybaraPokeBall.toID;
import static net.minecraft.world.entity.Entity.RemovalReason.DISCARDED;
import static net.minecraft.world.entity.Entity.RemovalReason.UNLOADED_TO_CHUNK;


public class CapybaraEntity extends TamableAnimal
{
    public static final double TEMPT_SPEED_MOD = 0.6;
    public static final double WALK_SPEED_MOD = 0.8;
    public static final double SPRINT_SPEED_MOD = 1.33;
    private static final Ingredient TEMPT_INGREDIENT = Ingredient.of(Items.APPLE,ModItems.TANGERINE.get(),ModItems.GOLDEN_TANGERINE.get(),ModItems.ENCHANTED_GOLDEN_TANGERINE.get());
    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CAN_BE_RIDE = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> HAND_ITEM = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final int MORE_CARROTS_DELAY = 40;
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int jumpDelayTicks;
    private Level level;
    public boolean isCapybaraJockey = false;
    private static final double DEFAULT_ATTACK_REACH = Math.sqrt(2.04F) - 0.6F;
    private static net.neoforged.neoforge.common.IMinecartCollisionHandler COLLISIONS = null;



    protected CapybaraEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.level = pLevel;
//        this.setTame(false);
//        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
//        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    public void setLying(boolean pLying) {
        this.entityData.set(IS_LYING, pLying);
    }

    public boolean isLying() {
        return this.entityData.get(IS_LYING);
    }

    public void setCanBeRide(boolean pCanBeRide) {
        this.entityData.set(CAN_BE_RIDE, pCanBeRide);
    }

    public boolean isCanBeRide() {
        return this.entityData.get(CAN_BE_RIDE);
    }

    public void setHandItem(ItemStack itemStack){
        this.entityData.set(HAND_ITEM,itemStack);
    }
    public ItemStack getHandItem(){
        return  this.entityData.get(HAND_ITEM);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_LYING, false);
        this.entityData.define(CAN_BE_RIDE,false); //默认不可骑乘 同时不去骑乘
        this.entityData.define(HAND_ITEM, Items.AIR.getDefaultInstance());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isCanBeRide",this.isCanBeRide());
    }


    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setCanBeRide(pCompound.getBoolean("isCanBeRide"));
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2,new MoveToWaterGoal(this,0.8));
        this.goalSelector.addGoal(3, new MoveToSandGoal(this, 0.2, 8));
        this.goalSelector.addGoal(2, new LookForAndRideCapybara(this, 0.3, false));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CapybaraEntity.class,10, true,true,this::isAngryAt1));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes();
    }


    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstackplayer = pPlayer.getItemInHand(pHand);
        ItemStack itemstackcapybara = this.getItemBySlot(EquipmentSlot.MAINHAND);
//                this.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = itemstackplayer.getItem();

        // 判断当前是否在客户端执行（通常用于渲染和输入处理，不执行实际逻辑）
        if (this.level().isClientSide) {
            // 如果生物被驯服且是玩家的宠物，则交互成功
            if (this.isTame() && this.isOwnedBy(pPlayer)) {
                return InteractionResult.SUCCESS;
            } else {
                // 如果不是食物，或者生物健康且被驯服但玩家尝试用非食物物品交互
                // 则根据条件返回PASS（忽略）或SUCCESS（可能用于客户端反馈）
                return !this.isFood(itemstackplayer) || !(this.getHealth() < this.getMaxHealth()) && this.isTame()
                        ? InteractionResult.PASS
                        : InteractionResult.SUCCESS;
            }
        } else {
            // 在服务端执行逻辑
            if (this.isTame()) {
                // 如果生物是玩家的宠物
                if (this.isOwnedBy(pPlayer)) {


                    if (!(item.equals(Items.SADDLE))) { //如果不是 鞍


                        if (item.isEdible() && this.isFood(itemstackplayer) && this.getHealth() < this.getMaxHealth()) {
                            // 治疗生物并消耗物品
                            this.heal((float)itemstackplayer.getFoodProperties(this).getNutrition());
                            this.usePlayerItem(pPlayer, pHand, itemstackplayer);
                            return InteractionResult.CONSUME;
                        }

                        if (this.isFood(itemstackplayer) && itemstackcapybara.isEmpty() && !itemstackplayer.isEmpty()) {
                            // 将玩家手中的物品复制一份（数量为1）到生物的主手中
                            ItemStack itemstack3 = itemstackplayer.copyWithCount(1);
//                            System.out.println( this.getItemBySlot(EquipmentSlot.MAINHAND));
//                            this.setItemInHand(InteractionHand.MAIN_HAND, itemstack3);
                            this.setItemSlot(EquipmentSlot.MAINHAND,itemstack3);
                            setHandItem(itemstack3);
//                            System.out.println( this.getItemBySlot(EquipmentSlot.MAINHAND));
                            // 从玩家手中移除该物品
                            this.removeInteractionItem(pPlayer, itemstackplayer);
//                            // 播放一个特定的声音（可能是表示物品已被给予生物）
//                            this.level().playSound(pPlayer, this, SoundEvents.ALLAY_ITEM_GIVEN, SoundSource.NEUTRAL, 2.0F, 1.0F);
//                            // 设置生物的记忆，记住这个玩家（可能是为了后续的交互或行为）
//                            this.getBrain().setMemory(MemoryModuleType.LIKED_PLAYER, pPlayer.getUUID());
                            // 返回交互成功的结果
                            return InteractionResult.SUCCESS;
                        }

                        // 如果生物的主手有物品，玩家使用主手进行交互且手中没有物品
                        else if (!itemstackcapybara.isEmpty() && pHand == InteractionHand.MAIN_HAND && itemstackplayer.isEmpty()) {
                            // 将生物主手的物品清空
                            this.setItemSlot(EquipmentSlot.MAINHAND, Items.AIR.getDefaultInstance());
                            //                            pPlayer.awardStat(Stats.ITEM_USED.get(ItemInit.BODYGUARD.get()));
//                            if (pPlayer instanceof ServerPlayer serverplayer) {
////                                serverplayer.awardStat(Stats.ITEM_USED.get(ModItems.GOLDEN_TANGERINE.get()), 1);
////                                CriteriaTriggers.USED_TOTEM.trigger(serverplayer, itemstackcapybara);
//                                this.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
//
////                            }
//                            Minecraft minecraft = ((CapybaraToDo)Minecraft.getInstance()).getInstance();
                            Minecraft.getInstance().gameRenderer.displayItemActivation(itemstackcapybara);
//                            this.minecraft.particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                            this.level.playLocalSound(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.TOTEM_USE, pPlayer.getSoundSource(), 1.0F, 1.0F, false);
//                            pPlayer.level().broadcastEntityEvent(pPlayer, (byte)35);

                            return InteractionResult.SUCCESS;
                        }
//                         调用父类的交互方法，并根据结果决定是否让生物坐下
//                        InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
//                        if (!interactionresult.consumesAction() || this.isBaby()) {
//                            this.setOrderedToSit(!this.isOrderedToSit());
//                        }
//
//                        return interactionresult;
                    }else {//如果是 鞍
                        this.setCanBeRide(!isCanBeRide()); //则改变骑乘属性
//                        System.out.println(isCanBeRide());
                        pPlayer.sendSystemMessage(Component.nullToEmpty(this.isCanBeRide()?"可以":"不可以"));
//                        this.saveAsPassenger()
                        if (!this.isCanBeRide()){

                            this.remove(UNLOADED_TO_CHUNK);
                            ItemStack item1 = ModItems.CAPYBARA_POKEBALL.get().getDefaultInstance();
                            if(!NBTHelper.hasNbt(item1,"capybara_pokeball_item")){
                                CompoundTag compoundTag = new CompoundTag();
                                compoundTag.putInt("hand_item",toID(this.getHandItem()));
                                item1.setTag(compoundTag);
                            }



                            pPlayer.getInventory().add(item1);

                        }
//                        CompoundTag compoundtag = this.saveWithoutId(new CompoundTag());
//                        compoundtag.remove("capybara_entity");
//                        this.load(compoundtag);
//                        this.load("");
                    }
                }
            } else if (this.isFood(itemstackplayer)) {
                // 如果生物未被驯服但手中的物品是食物
                this.usePlayerItem(pPlayer, pHand, itemstackplayer);
                // 根据随机数是否驯服生物
                if (this.random.nextInt(3) == 0 && !net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, pPlayer)) {
                    this.tame(pPlayer);
//                    this.setOrderedToSit(true); // 让生物坐下
                    this.setCanBeRide(false);
                    this.level().broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }

                this.setPersistenceRequired();
                return InteractionResult.CONSUME;
            }

            InteractionResult interactionresult1 = super.mobInteract(pPlayer, pHand);
            if (interactionresult1.consumesAction()) {
                this.setPersistenceRequired();
            }

            return interactionresult1;
        }
    }



    private void removeInteractionItem(Player pPlayer, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
    }

    @Override
    protected void usePlayerItem(Player pPlayer, InteractionHand pHand, ItemStack pStack) {
        if (this.isFood(pStack)) {
//            this.playSound(SoundEvents.CAT_EAT, 1.0F, 1.0F);
        }

        super.usePlayerItem(pPlayer, pHand, pStack);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }



    @Override
    public boolean isFood(ItemStack pStack) {
        return TEMPT_INGREDIENT.test(pStack);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !this.isTame() && this.tickCount > 2400;
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        Entity entity = pSource.getEntity();
        if (entity instanceof Creeper creeper && creeper.canDropMobsSkull()) {
            ItemStack itemstack = this.getSkull();
            if (!itemstack.isEmpty()) {
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(itemstack);
            }
        }
    }

    protected ItemStack getSkull() {
        return new ItemStack(Items.ZOMBIE_HEAD);
    }

    protected AABB getRideBoundingBox() {
        Entity entity = this.getTarget();
        Entity entity1 = entity.getVehicle();
        AABB aabb;
        if (entity1 != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = entity1.getBoundingBox();
            aabb = new AABB(
                    Math.min(aabb2.minX, aabb1.minX),
                    aabb2.minY,
                    Math.min(aabb2.minZ, aabb1.minZ),
                    Math.max(aabb2.maxX, aabb1.maxX),
                    aabb2.maxY,
                    Math.max(aabb2.maxZ, aabb1.maxZ)
            );
        } else {
            aabb = entity.getBoundingBox();
        }

        return aabb.inflate(DEFAULT_ATTACK_REACH, 0.0, DEFAULT_ATTACK_REACH);
    }

    public boolean isWithinMeleeRideRange(CapybaraEntity capybara) {
        //传进来的是其他水豚
        return this.getBoundingBox().intersects(capybara.getRideBoundingBox());
    }

    private boolean isAngryAt1(LivingEntity pTarget) {
        return ((CapybaraEntity)pTarget).isCanBeRide();
    }


//    @Override
//    public boolean canHoldItem(ItemStack pStack) {
//        return  (!this.isFood(pStack)) && this.isBaby() && this.isPassenger() ? false : super.canHoldItem(pStack); //pStack.is(Items.EGG) &&
//    }
//
//    @Override
//    public boolean wantsToPickUp(ItemStack pStack) {
//        return pStack.is(Items.GLOW_INK_SAC) ? false : super.wantsToPickUp(pStack);
//    }

//    @Override
//    public void aiStep(){
//        super.aiStep();
//    }

//    protected void pickUpItem(ItemEntity pItemEntity) {
//        ItemStack itemstack = pItemEntity.getItem();
//            ItemStack itemstack1 = this.equipItemIfPossible(itemstack.copy());
//        if (!itemstack1.isEmpty()) {
//            this.onItemPickup(pItemEntity);
//            this.take(pItemEntity, itemstack1.getCount());
//            itemstack.shrink(itemstack1.getCount());
//            if (itemstack.isEmpty()) {
//                pItemEntity.discard();
//            }
//        }
//    }


}
