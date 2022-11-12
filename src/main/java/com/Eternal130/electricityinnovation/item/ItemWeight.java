package com.Eternal130.electricityinnovation.item;

import com.Eternal130.electricityinnovation.ElectricityInnovation;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemWeight extends ItemTerra {
    public ItemWeight(){
        super();
        this.setUnlocalizedName("weight");
        this.setCreativeTab(TFCTabs.TFC_MATERIALS);
        stackable=true;
        this.setTextureName(ElectricityInnovation.MODID + ":" + "weight");
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getIconString());
    }
}
