package com.eternal130.electricityinnovation.items;

import com.eternal130.electricityinnovation.ElectricityInnovation;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemHook extends ItemTerra {
    public ItemHook(){
        super();
        this.setUnlocalizedName("hook");
        this.setCreativeTab(TFCTabs.TFC_MATERIALS);
        stackable=false;
        this.setTextureName(ElectricityInnovation.MODID + ":" + "hook");
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getIconString());
    }
}
