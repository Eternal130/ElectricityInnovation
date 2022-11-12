package com.Eternal130.electricityinnovation;

import com.Eternal130.electricityinnovation.common.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ElectricityInnovation.MODID,name = ElectricityInnovation.NAME, version = ElectricityInnovation.VERSION,dependencies = "required-after:terrafirmacraftplus;")
public class ElectricityInnovation
{    
	public static final String NAME = "ElectricityInnovation";
    public static final String MODID = "electricityinnovation";
    public static final String VERSION = "1.0";
    public static SimpleNetworkWrapper network;

    @Instance(MODID)
    public static ElectricityInnovation instance;
    @SidedProxy(
            clientSide = "com.Eternal130.electricityinnovation.client.ClientProxy",
            serverSide = "com.Eternal130.electricityinnovation.common.CommonProxy"
    )
    public static CommonProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}