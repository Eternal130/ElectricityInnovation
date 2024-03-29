package com.eternal130.electricityinnovation;

import com.eternal130.electricityinnovation.recipes.CraftingLoader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ChunkEventHandler {
    public ChunkEventHandler(){}
    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event){
        if (event.world.provider.dimensionId == 0)
            if (event.world.isRemote) {
                FMLCommonHandler.instance().bus().register(CraftingLoader.getPlayerTickEventHandler());
            } else {
                CraftingLoader.registerAnvilRecipes();
            }
    }
}
