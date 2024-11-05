package io.github.yuanseen.stone.item.magic_stick;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.yuanseen.stone.entity.magic.ReturnLockTargetEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MagicStick1 extends Item implements Vanishable {
    public static final int THROW_THRESHOLD_TIME = 10;
//    public static final float BASE_DAMAGE = 8.0F;
    public static final float SHOOT_POWER = 2.5F;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public MagicStick1(Item.Properties pProperties) {
        super(pProperties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 8.0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.9F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
// 重写releaseUsing方法，用于处理物品使用结束时的逻辑
    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        // 检查使用此物品的实体是否为玩家
        if (pEntityLiving instanceof Player) {
            Player player = (Player)pEntityLiving;
            // 计算已使用时长（总时长减去剩余时长）
            int i = this.getUseDuration(pStack) - pTimeLeft;
            // 如果已使用时长达到或超过一定值（这里为10）
            if (i >= 10) {
                    // 确保在非客户端环境中执行以下逻辑（即服务器或单人游戏逻辑处理部分）
                    if (!pLevel.isClientSide) {
                        // 损坏并破坏三叉戟，同时向玩家和其他玩家广播破坏事件
                        pStack.hurtAndBreak(1, player, p_43388_ -> p_43388_.broadcastBreakEvent(pEntityLiving.getUsedItemHand()));


//                        //需要重写的代码
//                        {
//                            ThrownTrident throwntrident = new ThrownTrident(pLevel, player, pStack);
//                            // 根据玩家的旋转角度和力度投掷三叉戟
//                            throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + 1 * 0.5F, 1.0F);
//
//                            // 如果玩家在创造模式下，设置投掷的三叉戟只能被创造模式玩家拾取
//                            if (player.getAbilities().instabuild) {
//                                throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
//                            }
//
//                            // 将投掷的三叉戟添加到世界中
//                            pLevel.addFreshEntity(throwntrident);
//
//                            // 播放投掷声音
//                            pLevel.playSound(null, throwntrident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
//                        }

                        {
                            ReturnLockTargetEntity returnLockTargetEntity = new ReturnLockTargetEntity(pLevel, player, pStack);
                            // 根据玩家的旋转角度和力度投掷三叉戟
                            returnLockTargetEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + 1 * 0.5F, 1.0F);

                            // 如果玩家在创造模式下，设置投掷的三叉戟只能被创造模式玩家拾取
//                            if (player.getAbilities().instabuild) {
//                                return;.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
//                            }

                            // 将投掷的三叉戟添加到世界中
                            pLevel.addFreshEntity(returnLockTargetEntity);

                            // 播放投掷声音
                            pLevel.playSound(null, returnLockTargetEntity, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                        }


//                            // 如果不是创造模式，从玩家背包中移除三叉戟
//                            if (!player.getAbilities().instabuild) {
//                                player.getInventory().removeItem(pStack);
//                            }
                }
            }
        }
    }

    /**
//     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block,
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        }
//        else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !pPlayer.isInWaterOrRain()) {
//            return InteractionResultHolder.fail(itemstack);
//        }
        else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise the damage on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, p_43414_ -> p_43414_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    /**
     * Called when a {@link net.minecraft.world.level.block.Block} is destroyed using this Item. Return {@code true} to trigger the "Use Item" statistic.
     */
    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if ((double)pState.getDestroySpeed(pLevel, pPos) != 0.0) {
            pStack.hurtAndBreak(2, pEntityLiving, p_43385_ -> p_43385_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ToolAction toolAction) {
        return net.neoforged.neoforge.common.ToolActions.DEFAULT_TRIDENT_ACTIONS.contains(toolAction);
    }
}
