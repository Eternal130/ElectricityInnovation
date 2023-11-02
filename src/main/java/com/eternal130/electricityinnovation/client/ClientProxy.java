package com.eternal130.electricityinnovation.client;

import com.eternal130.electricityinnovation.common.CommonProxy;
//import com.eternal130.electricityinnovation.render.GenericBlockItemRender;
//import com.eternal130.electricityinnovation.render.RenderElectricOven;
//import com.eternal130.electricityinnovation.tileentity.TileEntityElectricOven;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

//import static com.Eternal130.electricityinnovation.block.BlockLoader.electricovenBlock;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityElectricOven.class,new RenderElectricOven());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(electricovenBlock),new GenericBlockItemRender(new TileEntityElectricOven(),new RenderElectricOven()));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}
