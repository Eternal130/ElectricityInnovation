package com.eternal130.electricityinnovation.items;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemLoader {
    public static Item steelyard = new ItemSteelyard();
    public static Item dish = new ItemDish();
    public static Item hook = new ItemHook();
    public static Item weight = new ItemWeight();

    public ItemLoader(FMLPreInitializationEvent event)
    {
        register();
    }

    private static void register()
    {
        GameRegistry.registerItem(steelyard, steelyard.getUnlocalizedName());
        GameRegistry.registerItem(dish, dish.getUnlocalizedName());
        GameRegistry.registerItem(hook, hook.getUnlocalizedName());
        GameRegistry.registerItem(weight, weight.getUnlocalizedName());
    }
}
