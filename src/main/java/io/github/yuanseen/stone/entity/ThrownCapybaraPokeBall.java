package io.github.yuanseen.stone.entity;

import io.github.yuanseen.stone.item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import static io.github.yuanseen.stone.item.custom.CapybaraPokeBall.readID;
import static io.github.yuanseen.stone.item.custom.CapybaraPokeBall.toID;

public class ThrownCapybaraPokeBall extends ThrowableItemProjectile {


    private static final EntityDataAccessor<ItemStack> HAND_ITEM = SynchedEntityData.defineId(ThrownCapybaraPokeBall.class, EntityDataSerializers.ITEM_STACK);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAND_ITEM, Items.AIR.getDefaultInstance());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("hand_item",toID(this.getHandItem()));
    }


    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setHandItem(readID(pCompound.getInt("hand_item")).getDefaultInstance());
    }

    public void setHandItem(ItemStack itemStack){
        this.entityData.set(HAND_ITEM,itemStack);
    }
    public ItemStack getHandItem(){
        return  this.entityData.get(HAND_ITEM);
    }



    public ThrownCapybaraPokeBall(EntityType<? extends ThrownCapybaraPokeBall> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownCapybaraPokeBall(Level pLevel, LivingEntity pShooter) {
        super(StoneEntityTypes.CAPYBARAP_POKEBALL_ENTITY.get(), pShooter, pLevel);
    }

    public ThrownCapybaraPokeBall(Level pLevel, double pX, double pY, double pZ) {
        super(StoneEntityTypes.CAPYBARAP_POKEBALL_ENTITY.get(), pX, pY, pZ, pLevel);
    }


    /**
     * //此处动画需要重做
     */
    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.08;

            for(int i = 0; i < 8; ++i) {
                this.level()
                        .addParticle(
                                new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                ((double)this.random.nextFloat() - 0.5) * 0.08,
                                ((double)this.random.nextFloat() - 0.5) * 0.08,
                                ((double)this.random.nextFloat() - 0.5) * 0.08
                        );
            }
        }
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
//            if (this.random.nextInt(8) == 0) {
//                int i = 1;
//                if (this.random.nextInt(32) == 0) {
//                    i = 4;
//                }

//                for(int j = 0; j < i; ++j) {
//                    Chicken chicken = EntityType.CHICKEN.create(this.level());
                    CapybaraEntity capybara = StoneEntityTypes.CAPYBARA_ENTITY.get().create(this.level());
                    if (capybara != null) {
//                        chicken.setAge(-24000);
                        ItemStack item = this.getHandItem();
                        capybara.tame((Player) this.getOwner());
                        if (item.getItem()!=Items.AIR){
//                        capybara.setHandItem(item);
                        capybara.setItemSlot(EquipmentSlot.MAINHAND,item);}

                        capybara.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        this.level().addFreshEntity(capybara);

                    }
//                }
//            }

//            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CAPYBARA_POKEBALL.get();
    }
}
