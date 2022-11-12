package com.Eternal130.electricityinnovation.item;

import com.Eternal130.electricityinnovation.ElectricityInnovation;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemDish extends ItemTerra {
    public ItemDish(){
        super();
        this.setUnlocalizedName("dish");
        this.setCreativeTab(TFCTabs.TFC_MATERIALS);
        stackable=false;
        this.setTextureName(ElectricityInnovation.MODID + ":" + "dish");
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getIconString());
    }
}
