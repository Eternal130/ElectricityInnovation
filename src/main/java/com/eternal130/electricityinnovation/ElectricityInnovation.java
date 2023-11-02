package com.eternal130.electricityinnovation;

import com.eternal130.electricityinnovation.common.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ElectricityInnovation.MODID, version = ElectricityInnovation.VERSION,name = ElectricityInnovation.NAME,dependencies = "required-after:terrafirmacraftplus;")
public class ElectricityInnovation
{

    public static final String NAME = "ElectricityInnovation";
    public static final String MODID = "electricityinnovation";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static ElectricityInnovation instance;
    @SidedProxy(
            clientSide = "com.eternal130.electricityinnovation.client.ClientProxy",
            serverSide = "com.eternal130.electricityinnovation.common.CommonProxy"
    )
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

}
