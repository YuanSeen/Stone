package io.github.yuanseen.stone.event.client;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.data.tag.ModWorldGen;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Stone.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGeneratorHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        ExistingFileHelper efh = event.getExistingFileHelper();
        var lp = event.getLookupProvider();
        //world  gen
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<ModWorldGen>) pOutput -> new ModWorldGen(pOutput,lp));
    }
}
