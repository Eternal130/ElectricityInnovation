package com.Eternal130.electricityinnovation.item;

import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Crafting.AnvilRecipe;
import com.dunk.tfc.api.Crafting.AnvilReq;
import com.dunk.tfc.api.Crafting.PlanRecipe;
import com.dunk.tfc.api.Enums.RuleEnum;
import com.dunk.tfc.api.TFCItems;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.dunk.tfc.api.Crafting.AnvilManager.world;

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
