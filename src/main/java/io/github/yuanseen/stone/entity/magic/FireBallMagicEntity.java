package io.github.yuanseen.stone.entity.magic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FireBallMagicEntity extends Entity {
    public FireBallMagicEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    /**
     * @param pCompound
     */
    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

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
}
