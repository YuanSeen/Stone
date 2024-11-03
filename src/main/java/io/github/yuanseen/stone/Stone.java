package io.github.yuanseen.stone;

import com.mojang.logging.LogUtils;
import io.github.yuanseen.stone.block.ModBlockEntities;
import io.github.yuanseen.stone.block.ModBlocks;
import io.github.yuanseen.stone.entity.StoneEntityTypes;
import io.github.yuanseen.stone.item.ModCreativeTab;
import io.github.yuanseen.stone.item.ModItems;
//import io.github.yuanseen.stone.world.structure.ModStructurePieceTypes;
//import io.github.yuanseen.stone.world.structure.ModStructureSets;
//import io.github.yuanseen.stone.world.structure.ModStructureType;
import io.github.yuanseen.stone.world.structure.ModStructurePieceTypes;
import io.github.yuanseen.stone.world.structure.ModStructureType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Stone.MOD_ID)
public class Stone
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "stone";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public Stone(IEventBus modEventBus)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        StoneEntityTypes.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        ModStructureType.register(modEventBus);
        ModStructurePieceTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
//        event.enqueueWork(() -> {
//            SpawnPlacements.register(StoneEntityTypes.CAPYBARA_ENTITY.get(),
//                    SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
//                    Animal::checkAnimalSpawnRules);
//        });

    }

    // Add the example block item to the building blocks tab

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
