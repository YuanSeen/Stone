package io.github.yuanseen.stone.world.structure;

import io.github.yuanseen.stone.Stone;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSets {
    // 我们structureset的资源名称
    public static final ResourceKey<StructureSet> MY_STRUCTURE_SET = register("my_structure");
    // 等会使用数据生成要要传递的方法引用
    public static void bootstrap(BootstapContext<StructureSet> pContext) {
        // BootstapContext数据生成时候的上下文
        // 从上下文中获得HolderGetter<Structure>
        HolderGetter<Structure> holdergetter = pContext.lookup(Registries.STRUCTURE);
        // 通过上下文注册我们的set
        // 第一个参数是我们的key
        // 第二个参数是set的实例，new处理
        // set的第一个参数是对应的strcture，这里通过holder获得，第二个参数就是描述放置的信息的类
        // RandomSpreadStructurePlacement是随机放置，也有另一个ConcentricRingsStructurePlacement放置，他们继承于StructurePlacement
        // 我们使用了随机放置
        // 随机放置的第一个参数是当前位置生成失败时候寻找下一生成点的距离
        // 第二个参数建组之间的最小间隔。
        // 第三个参数是随机放置的类型，这里填的线性的， 也有其他的可以选择。
        // 第四个参数是盐
        pContext.register(
                ModStructureSets.MY_STRUCTURE_SET,
                new StructureSet(holdergetter.getOrThrow(ModStructures.MY_STRUCTURE), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357619))
        );
    }

    private static ResourceKey<StructureSet> register(String pName) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(Stone.MOD_ID,pName));
    }

}