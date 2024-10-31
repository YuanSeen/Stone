package io.github.yuanseen.stone.world.structure;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.world.structure.structure.MyStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

// 注册StructureType方法
public class ModStructureType<S extends Structure> {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Stone.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<MyStructure>> MY_STRUCTURE = registerType("my_structure", () -> () -> MyStructure.CODEC);

    private static <P extends Structure> DeferredHolder<StructureType<?>, StructureType<P>> registerType(String name, Supplier<StructureType<P>> factory) {
        return STRUCTURE_TYPES.register(name, factory);
    }

    public static void register(IEventBus eventBus){
        STRUCTURE_TYPES.register(eventBus);
    }
}
