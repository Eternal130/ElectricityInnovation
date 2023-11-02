package com.eternal130.electricityinnovation.items;

import com.eternal130.electricityinnovation.ElectricityInnovation;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemDish extends ItemTerra {
    public ItemDish(){
        super();
        //设置物品通用名(未本地化名字)
        this.setUnlocalizedName("dish");
        //设置物品出现在哪个创造物品页,这里设置为tfc+材料页
        this.setCreativeTab(TFCTabs.TFC_MATERIALS);
        //物品不可堆叠
        stackable=false;
        //物品材质名
        this.setTextureName(ElectricityInnovation.MODID + ":" + "dish");
    }
    //注册物品图标
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getIconString());
    }
}
