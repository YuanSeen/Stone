package io.github.yuanseen.stone.entity.magic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.AnnotatedType;
import java.util.function.Supplier;

public class FireBallMagicEntity extends Entity {
    public final AnimationState getBiggerState = new AnimationState();
    public final AnimationState rollTowardsTheTargetState = new AnimationState();
    private static final int MAX_TIME = 1 * 20;

    private int timer = 0;

    public FireBallMagicEntity(EntityType<FireBallMagicEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }



    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {

            if (getTimer() <= 40) {
                this.rollTowardsTheTargetState.stop();
                this.getBiggerState.startIfStopped(this.tickCount);
                System.out.println(getTimer());
            } else {
                this.getBiggerState.stop();
                this.rollTowardsTheTargetState.startIfStopped(this.tickCount);
                System.out.println(getTimer()+"eee");

            }
//            this.rollTowardsTheTargetState.stop();
//            this.getBiggerState.startIfStopped(this.tickCount);
////            System.out.println(this.tickCount);
//
            timer++;
        }




//        this.setupAnimationStates();
    }

    /**
     *
     */
    @Override
    protected void defineSynchedData() {

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     *
     * @param pCompound
     */
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
            setTimer(pCompound.getInt("time"));
    }

    /**
     * @param pCompound
     */
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
            pCompound.putInt("time",getTimer());
    }

    /**
     * Returns {@code true} if there is a data attachment of the give type, {@code false} otherwise.
     *
     * @param type
     */
    @Override
    public <T> boolean hasData(Supplier<AttachmentType<T>> type) {
        return super.hasData(type);
    }

    /**
     * {@return the data attachment of the given type}
     *
     * <p>If there is no data attachment of the given type, <b>the default value is stored in this holder and returned.</b>
     *
     * @param type
     */
    @Override
    public <T> T getData(Supplier<AttachmentType<T>> type) {
        return super.getData(type);
    }

    /**
     * Sets the data attachment of the given type.
     *
     * @param type
     * @param data
     * @return the previous value for that attachment type, if any, or {@code null} if there was none
     */
    @Override
    public <T> @Nullable T setData(Supplier<AttachmentType<T>> type, T data) {
        return super.setData(type, data);
    }

    /**
     * @param nbt
     */
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
    }

    /**
     * @return
     */
    @Override
    public CompoundTag serializeNBT() {
        return super.serializeNBT();
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
