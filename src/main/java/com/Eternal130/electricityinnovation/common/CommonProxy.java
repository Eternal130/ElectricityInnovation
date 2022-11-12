package com.Eternal130.electricityinnovation.common;

import com.Eternal130.electricityinnovation.crafting.CraftingLoader;
import com.Eternal130.electricityinnovation.inventory.GuiHandler;
import com.Eternal130.electricityinnovation.item.ItemLoader;
import com.Eternal130.electricityinnovation.network.NetworkLoader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event)
    {
        new ItemLoader(event);
        new NetworkLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new GuiHandler();
        new CraftingLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
