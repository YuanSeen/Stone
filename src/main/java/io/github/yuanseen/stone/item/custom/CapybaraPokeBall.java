package io.github.yuanseen.stone.item.custom;

import io.github.yuanseen.stone.entity.ThrownCapybaraPokeBall;
import io.github.yuanseen.stone.item.ModItems;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CapybaraPokeBall extends Item {
    public CapybaraPokeBall(Properties pProperties) {
        super(pProperties);
    }
//    private ItemStack handItem ;
//
//    public void setHandItem(ItemStack itemStack){
//        handItem = itemStack;
//    }
//
//    public ItemStack getHandItem(){
//        return this.handItem;
//    }
//
//
//    public void load(CompoundTag pTag) {
////        setHandItem(pTag.getId());
////        counter = pTag.getInt("counter");
////        super.load(pTag);
//        setHandItem(readID(pTag.getInt("hand_item")).getDefaultInstance());
//
//    }
//
//    protected void saveAdditional(CompoundTag pTag) {
//
////        super.saveAdditional(pTag);
//        pTag.putInt("hand_item",toID(handItem));
//
//    }
////
    public static int toID(ItemStack itemStack){
        Item item = itemStack.getItem();
        int id = 0;
        if (item == Items.APPLE){
            id = 1;
        }else if (item == ModItems.TANGERINE.get()){
            id = 2;
        }else if(item == ModItems.GOLDEN_TANGERINE.get()){
            id = 3;
        }else if (item == ModItems.ENCHANTED_GOLDEN_TANGERINE.get()){
            id = 4;
        }
//                id = 1 ;
//                break;
//            case ModItems.TANGERINE.get(): id = 2 ;
//            case ModItems.GOLDEN_TANGERINE.get(): id = 3 ;
//            case ModItems.ENCHANTED_GOLDEN_TANGERINE.get(): id = 4 ;
        return id;
    }

    public static Item readID(int id){
        Item item =Items.AIR;
        if (id == 1){
            item = Items.APPLE;
        }else if (id == 2){
            item = ModItems.TANGERINE.get();
        }else if( id == 3){
            item = ModItems.GOLDEN_TANGERINE.get();
        }else if (id == 4){
            item = ModItems.ENCHANTED_GOLDEN_TANGERINE.get();
        }
        return item;
    }
////    private void spawnInfestation(ServerLevel pLevel, BlockPos pPos) {
////        Silverfish silverfish = EntityType.SILVERFISH.create(pLevel);
////        if (silverfish != null) {
////            silverfish.moveTo((double)pPos.getX() + 0.5, (double)pPos.getY(), (double)pPos.getZ() + 0.5, 0.0F, 0.0F);
////            pLevel.addFreshEntity(silverfish);
////            silverfish.spawnAnim();
////        }
////    }
//    private void spawnInfestation(ServerLevel pLevel, BlockPos pPos, Player pPlayer){
//        CapybaraEntity capybara = StoneEntityTypes.CAPYBARA_ENTITY.get().create(pLevel);
//        if (capybara != null){
////            capybara.setTame();
//            capybara.tame(pPlayer);
//            capybara.moveTo((double)pPos.getX() + 0.5, (double)pPos.getY(), (double)pPos.getZ() + 0.5, 0.0F, 0.0F);
//            pLevel.addFreshEntity(capybara);
//        }
//    }
//
//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//        Level level = pContext.getLevel();
//
//        if (!(level instanceof ServerLevel)) {
//            return InteractionResult.SUCCESS;
//        } else {
//            Player pPlayer = pContext.getPlayer();
//            ItemStack itemstack = pContext.getItemInHand();
//            BlockPos blockpos = pContext.getClickedPos();
//            Direction direction = pContext.getClickedFace();
//            BlockState blockstate = level.getBlockState(blockpos);
////            BlockEntity $$10 = level.getBlockEntity(blockpos);
////            if ($$10 instanceof Spawner spawner) {
////                EntityType<?> entitytype1 = this.getType(itemstack.getTag());
////                spawner.setEntityId(entitytype1, level.getRandom());
////                level.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
////                level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_CHANGE, blockpos);
////                itemstack.shrink(1);
////                return InteractionResult.CONSUME;
////            } else {
//                BlockPos blockpos1;
//                if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
//                    blockpos1 = blockpos;
//                } else {
//                    blockpos1 = blockpos.relative(direction);
//                }
//
//
//                spawnInfestation((ServerLevel) level,blockpos1,pPlayer);
//
////                StoneEntityTypes entitytype = StoneEntityTypes.
////                if (entitytype.spawn(
////                        (ServerLevel)level,
////                        itemstack,
////                        pContext.getPlayer(),
////                        blockpos1,
////                        MobSpawnType.SPAWN_EGG,
////                        true,
////                        !Objects.equals(blockpos, blockpos1) && direction == Direction.UP
////                )
////                        != null) {
//                    itemstack.shrink(1);
////                    level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
////                }
//
//                return InteractionResult.CONSUME;
////            }
//        }
//    }
////
//    public ItemStack getHandItem(@Nullable CompoundTag pNbt) {
//        if (pNbt != null && pNbt.contains("EntityTag", 10)) {
//            CompoundTag compoundtag = pNbt.getCompound("EntityTag");
//            if (compoundtag.contains("id", 8)) {
//                return EntityType.byString(compoundtag.getString("id")).orElse(this.getDefaultType());
//            }
//        }
//
//        return this.getDefaultType();
//        }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
//        pLevel.playSound(
//                null,
//                pPlayer.getX(),
//                pPlayer.getY(),
//                pPlayer.getZ(),
////                SoundEvents.EGG_THROW,
////                SoundSource.PLAYERS,
////                0.5F,
////                0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F)
//        );
        if (!pLevel.isClientSide) {
//            ThrownEgg thrownegg = new ThrownEgg(pLevel, pPlayer);
//            thrownegg.setItem(itemstack);
//            thrownegg.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
//            pLevel.addFreshEntity(thrownegg);
            ThrownCapybaraPokeBall pokeBall = new ThrownCapybaraPokeBall(pLevel,pPlayer);
            pokeBall.setItem(itemstack);
//            pokeBall.getTags().add("hand_item");
            pokeBall.setHandItem(readID(itemstack.getTag().getInt("hand_item")).getDefaultInstance());
//                CompoundTag compoundTag = new CompoundTag();
//                compoundTag.putInt("hand_item",itemstack.getTag().getInt("hand_item"));
////                item1.setTag(compoundTag);
//            pokeBall.addAdditionalSaveData(compoundTag);
////            pokeBall.getTags().
//
            pokeBall.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(pokeBall);

        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

}
