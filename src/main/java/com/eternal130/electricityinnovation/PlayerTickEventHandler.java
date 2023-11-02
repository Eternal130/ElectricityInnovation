package com.eternal130.electricityinnovation;

import com.eternal130.electricityinnovation.recipes.CraftingLoader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class PlayerTickEventHandler {
    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        CraftingLoader.registerAnvilRecipes();
        FMLCommonHandler.instance().bus().unregister(CraftingLoader.getPlayerTickEventHandler());
    }
}
