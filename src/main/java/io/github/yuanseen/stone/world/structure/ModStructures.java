package io.github.yuanseen.stone.world.structure;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.world.structure.structure.MyStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;

public class ModStructures {
    // 以下的三个辅助的方法用于获得StructureSettings这个类
    // 和之前讲解StructureSettings的一致，就不赘述了。
    public static Structure.StructureSettings structure(
            HolderSet<Biome> pBiomes, Map<MobCategory, StructureSpawnOverride> pSpawnOverrides, GenerationStep.Decoration pStep, TerrainAdjustment pTerrainAdaptation
    ) {
        return new Structure.StructureSettings(pBiomes, pSpawnOverrides, pStep, pTerrainAdaptation);
    }
    private static Structure.StructureSettings structure(HolderSet<Biome> pBiomes, GenerationStep.Decoration pStep, TerrainAdjustment pTerrainAdaptation) {
        return structure(pBiomes, Map.of(), pStep, pTerrainAdaptation);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> pBiomes, TerrainAdjustment pTerrainAdaptation) {
        return structure(pBiomes, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, pTerrainAdaptation);
    }
    // strucutre的key
    public static final ResourceKey<Structure> MY_STRUCTURE = registerKey("my_structure");
    // 创建key的方法
    public static ResourceKey<Structure> registerKey(String name) {
         return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Stone.MOD_ID,name));
    }
    // bootstrap在数据生成时候调用
    public static void bootstrap(BootstapContext<Structure> context) {
        // 从上下文中获得所有的生物群系
        HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
        // 第一个参数是key
        // 第二个参数就是我们的strucutre的构造
        // 构造的要传入一个StructureSettings
        // setting的一个参数是生物群系，我们指定了生物群系是主世界，对于第二个参数设置的是TerrainAdjustment.NONE，当然你也可以选择其他的。
        context.register(
                ModStructures.MY_STRUCTURE,
                new MyStructure(structure(biomeHolderGetter.getOrThrow(BiomeTags.IS_OVERWORLD), TerrainAdjustment.NONE))
        );

    }
}
