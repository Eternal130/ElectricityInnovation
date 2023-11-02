package com.eternal130.electricityinnovation.common;

//import com.eternal130.electricityinnovation.block.BlockLoader;
import com.eternal130.electricityinnovation.recipes.CraftingLoader;
import com.eternal130.electricityinnovation.inventory.GuiHandler;
import com.eternal130.electricityinnovation.items.ItemLoader;
import com.eternal130.electricityinnovation.network.NetworkLoader;
//import com.eternal130.electricityinnovation.tileentity.TileEntityLoader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event)
    {
        new ItemLoader(event);
//        new BlockLoader(event);
        new NetworkLoader(event);
//        new TileEntityLoader(event);
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
