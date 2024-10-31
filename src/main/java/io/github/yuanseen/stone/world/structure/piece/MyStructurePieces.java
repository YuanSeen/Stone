package io.github.yuanseen.stone.world.structure.piece;

import com.google.common.collect.ImmutableMap;
import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.world.structure.ModStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Map;

public class MyStructurePieces {
    static final ResourceLocation STRUCTURE_LOCATION_MY_STRUCTURE = new ResourceLocation(Stone.MOD_ID,"my_structure/my_structure");
    static final ResourceLocation ON_STRUCTURE_LOCATION_MY_STRUCTURE = new ResourceLocation(Stone.MOD_ID,"my_structure/on_my_structure");
    static final ResourceLocation BEON_STRUCTURE_LOCATION_MY_STRUCTURE = new ResourceLocation(Stone.MOD_ID,"my_structure/beon_my_structure");

    static final Map<ResourceLocation, BlockPos> PIVOTS = ImmutableMap.of(
            STRUCTURE_LOCATION_MY_STRUCTURE,BlockPos.ZERO,
            ON_STRUCTURE_LOCATION_MY_STRUCTURE,BlockPos.ZERO,
            BEON_STRUCTURE_LOCATION_MY_STRUCTURE,BlockPos.ZERO
    );
    static final Map<ResourceLocation, BlockPos> OFFSETS = ImmutableMap.of(
            STRUCTURE_LOCATION_MY_STRUCTURE, BlockPos.ZERO,
            ON_STRUCTURE_LOCATION_MY_STRUCTURE, BlockPos.ZERO,
            BEON_STRUCTURE_LOCATION_MY_STRUCTURE, BlockPos.ZERO
    );

    public static void addPieces(
            StructureTemplateManager pStructureTemplateManager, BlockPos pStartPos, Rotation pRotation, StructurePieceAccessor pPieces, RandomSource pRandom
    ) {

        pPieces.addPiece(new MyStructurePieces.OnMyStructurePiece(pStructureTemplateManager, ON_STRUCTURE_LOCATION_MY_STRUCTURE, pStartPos, pRotation, 3));
        pPieces.addPiece(new MyStructurePieces.MyStructurePiece(pStructureTemplateManager, STRUCTURE_LOCATION_MY_STRUCTURE, pStartPos, pRotation, 0));
        pPieces.addPiece(new MyStructurePieces.BeOnMyStructurePiece(pStructureTemplateManager, BEON_STRUCTURE_LOCATION_MY_STRUCTURE, pStartPos, pRotation, 0));


    }

    public static class MyStructurePiece extends TemplateStructurePiece {
        // 构造方法1，等会我们在structure中讲，怎么new，也就是我们刚刚没说的方法addpiece方法。
        public MyStructurePiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pStartPos, Rotation pRotation, int pDown) {
            super(ModStructurePieceTypes.MY_STRUCTURE_PIECE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(),
                    makeSettings(pRotation, pLocation),
                    makePosition(pLocation, pStartPos, pDown)
            );
        }

        // 构造方法二
        public MyStructurePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(ModStructurePieceTypes.MY_STRUCTURE_PIECE.get(), pTag, pStructureTemplateManager,   resourceLocation -> makeSettings(Rotation.valueOf(pTag.getString("Rot")), resourceLocation));
        }
        // 辅助函数用于生成StructurePlaceSettings，第一个参数是构建时候传入的随机数

        // 可以看出来是可以直接new出来的，new出来之后，你可以根据自己的需求进行设置，这里的设置了一些内容，
        // 比如设置piece的旋转
        // 设置mirror镜像
        // 设置旋转点
        // 以及最后一个addProcessor，表示添加一个新的StructureProcessor，这里用的是BlockIgnoreProcessor下面的STRUCTURE_BLOCK，
        // 表示生成的时候不要生成STRUCTURE_BLOCK方块。
        private static StructurePlaceSettings makeSettings(Rotation pRotation, ResourceLocation pLocation) {
            return new StructurePlaceSettings()
                    .setRotation(pRotation)
                    .setMirror(Mirror.NONE)
                    .setRotationPivot(MyStructurePieces.PIVOTS.get(pLocation))
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }
        // 辅助函数用于返回piece生成的位置
        private static BlockPos makePosition(ResourceLocation pLocation, BlockPos pPos, int pDown) {
            return pPos.offset(MyStructurePieces.OFFSETS.get(pLocation)).below(pDown);
        }
        // 给nbt添加一个rot的字段，表示随机旋转的信息。
        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            super.addAdditionalSaveData(pContext, pTag);
            pTag.putString("Rot", this.placeSettings.getRotation().name());
        }

        // 下面还是我们的piece类中的hhandleDataMarker
        // 这个主要处理了结构模板中的箱子，并放置战利品箱子
        // 其中的这个chest数据你可以到对应的nbt下看，nbt打开格式需要你用idea装下那个minecraft development插件
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {
            if ("chest".equals(pName)) {
                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
                BlockEntity blockentity = pLevel.getBlockEntity(pPos.below());
                if (blockentity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockentity).setLootTable(BuiltInLootTables.IGLOO_CHEST, pRandom.nextLong());
                }
            }
        }

        // 下面是我们自己类中的postProcess的方法，我们说下如何在生成前处理，或者生成后处理。
        @Override
        public void postProcess(
                WorldGenLevel pLevel,
                StructureManager pStructureManager,
                ChunkGenerator pGenerator,
                RandomSource pRandom,
                BoundingBox pBox,
                ChunkPos pChunkPos,
                BlockPos pPos
        ) {
            // 对生成前进行处理的话
            // 写在这里
            super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos);
            // 生成后处理
            // 写在这里
        }
    }

    public static class OnMyStructurePiece extends TemplateStructurePiece{
        // 构造方法1，等会我们在structure中讲，怎么new，也就是我们刚刚没说的方法addpiece方法。
        public OnMyStructurePiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pStartPos, Rotation pRotation, int pDown) {
            super(ModStructurePieceTypes.ON_MY_STRUCTURE_PIECE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(),
                    makeSettings(pRotation, pLocation),
                    makePosition(pLocation, pStartPos, pDown)
            );
        }

        // 构造方法二
        public OnMyStructurePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(ModStructurePieceTypes.ON_MY_STRUCTURE_PIECE.get(), pTag, pStructureTemplateManager,   resourceLocation -> makeSettings(Rotation.valueOf(pTag.getString("Rot")), resourceLocation));
        }
        // 辅助函数用于生成StructurePlaceSettings，第一个参数是构建时候传入的随机数

        // 可以看出来是可以直接new出来的，new出来之后，你可以根据自己的需求进行设置，这里的设置了一些内容，
        // 比如设置piece的旋转
        // 设置mirror镜像
        // 设置旋转点
        // 以及最后一个addProcessor，表示添加一个新的StructureProcessor，这里用的是BlockIgnoreProcessor下面的STRUCTURE_BLOCK，
        // 表示生成的时候不要生成STRUCTURE_BLOCK方块。
        private static StructurePlaceSettings makeSettings(Rotation pRotation, ResourceLocation pLocation) {
            return new StructurePlaceSettings()
                    .setRotation(pRotation)
                    .setMirror(Mirror.NONE)
                    .setRotationPivot(MyStructurePieces.PIVOTS.get(pLocation))
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }
        // 辅助函数用于返回piece生成的位置
        private static BlockPos makePosition(ResourceLocation pLocation, BlockPos pPos, int pDown) {
            return pPos.offset(MyStructurePieces.OFFSETS.get(pLocation)).below(pDown);
        }
        // 给nbt添加一个rot的字段，表示随机旋转的信息。
        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            super.addAdditionalSaveData(pContext, pTag);
            pTag.putString("Rot", this.placeSettings.getRotation().name());
        }

        // 下面还是我们的piece类中的hhandleDataMarker
        // 这个主要处理了结构模板中的箱子，并放置战利品箱子
        // 其中的这个chest数据你可以到对应的nbt下看，nbt打开格式需要你用idea装下那个minecraft development插件
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {
            if ("chest".equals(pName)) {
                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
                BlockEntity blockentity = pLevel.getBlockEntity(pPos.below());
                if (blockentity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockentity).setLootTable(BuiltInLootTables.IGLOO_CHEST, pRandom.nextLong());
                }
            }
        }

        // 下面是我们自己类中的postProcess的方法，我们说下如何在生成前处理，或者生成后处理。
        @Override
        public void postProcess(
                WorldGenLevel pLevel,
                StructureManager pStructureManager,
                ChunkGenerator pGenerator,
                RandomSource pRandom,
                BoundingBox pBox,
                ChunkPos pChunkPos,
                BlockPos pPos
        ) {
            // 对生成前进行处理的话
            // 写在这里
            super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos);
            // 生成后处理
            // 写在这里
        }
    }

    public static class BeOnMyStructurePiece extends TemplateStructurePiece{
        // 构造方法1，等会我们在structure中讲，怎么new，也就是我们刚刚没说的方法addpiece方法。
        public BeOnMyStructurePiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pStartPos, Rotation pRotation, int pDown) {
            super(ModStructurePieceTypes.BEON_MY_STRUCTURE_PIECE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(),
                    makeSettings(pRotation, pLocation),
                    makePosition(pLocation, pStartPos, pDown)
            );
        }

        // 构造方法二
        public BeOnMyStructurePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(ModStructurePieceTypes.BEON_MY_STRUCTURE_PIECE.get(), pTag, pStructureTemplateManager,   resourceLocation -> makeSettings(Rotation.valueOf(pTag.getString("Rot")), resourceLocation));
        }
        // 辅助函数用于生成StructurePlaceSettings，第一个参数是构建时候传入的随机数

        // 可以看出来是可以直接new出来的，new出来之后，你可以根据自己的需求进行设置，这里的设置了一些内容，
        // 比如设置piece的旋转
        // 设置mirror镜像
        // 设置旋转点
        // 以及最后一个addProcessor，表示添加一个新的StructureProcessor，这里用的是BlockIgnoreProcessor下面的STRUCTURE_BLOCK，
        // 表示生成的时候不要生成STRUCTURE_BLOCK方块。
        private static StructurePlaceSettings makeSettings(Rotation pRotation, ResourceLocation pLocation) {
            return new StructurePlaceSettings()
                    .setRotation(pRotation)
                    .setMirror(Mirror.NONE)
                    .setRotationPivot(MyStructurePieces.PIVOTS.get(pLocation))
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }
        // 辅助函数用于返回piece生成的位置
        private static BlockPos makePosition(ResourceLocation pLocation, BlockPos pPos, int pDown) {
            return pPos.offset(MyStructurePieces.OFFSETS.get(pLocation)).below(pDown);
        }
        // 给nbt添加一个rot的字段，表示随机旋转的信息。
        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            super.addAdditionalSaveData(pContext, pTag);
            pTag.putString("Rot", this.placeSettings.getRotation().name());
        }

        // 下面还是我们的piece类中的hhandleDataMarker
        // 这个主要处理了结构模板中的箱子，并放置战利品箱子
        // 其中的这个chest数据你可以到对应的nbt下看，nbt打开格式需要你用idea装下那个minecraft development插件
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {
            if ("chest".equals(pName)) {
                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
                BlockEntity blockentity = pLevel.getBlockEntity(pPos.below());
                if (blockentity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockentity).setLootTable(BuiltInLootTables.IGLOO_CHEST, pRandom.nextLong());
                }
            }
        }

        // 下面是我们自己类中的postProcess的方法，我们说下如何在生成前处理，或者生成后处理。
        @Override
        public void postProcess(
                WorldGenLevel pLevel,
                StructureManager pStructureManager,
                ChunkGenerator pGenerator,
                RandomSource pRandom,
                BoundingBox pBox,
                ChunkPos pChunkPos,
                BlockPos pPos
        ) {
            // 对生成前进行处理的话
            // 写在这里
            super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos);
            // 生成后处理
            // 写在这里
        }
    }
}

