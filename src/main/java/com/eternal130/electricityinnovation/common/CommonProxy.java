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
        //注册物品
        new ItemLoader(event);
//        new BlockLoader(event);
        //注册通讯,用于杆秤gui内改变重量时同步到服务端
        new NetworkLoader(event);
//        new TileEntityLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        //注册gui
        new GuiHandler();
        //注册合成表
        new CraftingLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
