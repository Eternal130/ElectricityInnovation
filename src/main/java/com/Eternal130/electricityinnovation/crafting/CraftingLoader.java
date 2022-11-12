package com.Eternal130.electricityinnovation.crafting;

import com.Eternal130.electricityinnovation.PlayerTickEventHandler;
import com.Eternal130.electricityinnovation.item.ItemLoader;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Crafting.AnvilRecipe;
import com.dunk.tfc.api.Crafting.AnvilReq;
import com.dunk.tfc.api.Crafting.PlanRecipe;
import com.dunk.tfc.api.Enums.RuleEnum;
import com.dunk.tfc.api.TFCItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Map;

public class CraftingLoader
{
    private static PlayerTickEventHandler playerTickEventHandler = new PlayerTickEventHandler();
    public CraftingLoader()
    {
        registerRecipe();
    }

    private static void registerRecipe()
    {
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemLoader.steelyard, 1),
                new Object[]{TFCItems.stick, "materialString",ItemLoader.dish,ItemLoader.hook,ItemLoader.weight}));
    }

    public static void registerAnvilRecipes() {
        Map map = AnvilManager.getInstance().getPlans();
        if (map.containsKey("dish"))
            return;
        if (AnvilManager.world == null)
            return;
        AnvilManager manager = AnvilManager.getInstance();
        manager.addPlan("dish", new PlanRecipe(new RuleEnum[] { RuleEnum.HITLAST, RuleEnum.UPSETSECONDFROMLAST, RuleEnum.DRAWNOTLAST }));
        manager.addRecipe((new AnvilRecipe(new ItemStack(TFCItems.brassIngot), null, "dish", false, AnvilReq.COPPER, new ItemStack(ItemLoader.dish))));
        manager.addPlan("hook", new PlanRecipe(new RuleEnum[] { RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.DRAWTHIRDFROMLAST }));
        manager.addRecipe((new AnvilRecipe(new ItemStack(TFCItems.brassIngot), null, "hook", false, AnvilReq.COPPER, new ItemStack(ItemLoader.hook))));
        manager.addPlan("weight", new PlanRecipe(new RuleEnum[] { RuleEnum.UPSETLAST, RuleEnum.SHRINKSECONDFROMLAST, RuleEnum.DRAWNOTLAST }));
        manager.addRecipe((new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "weight", false, AnvilReq.COPPER, new ItemStack(ItemLoader.weight))));
    }


    public static PlayerTickEventHandler getPlayerTickEventHandler() {
        return playerTickEventHandler;
    }
}
