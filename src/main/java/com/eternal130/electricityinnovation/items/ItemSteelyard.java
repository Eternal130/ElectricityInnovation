package com.eternal130.electricityinnovation.items;

import com.eternal130.electricityinnovation.ElectricityInnovation;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.ItemTerra;
import com.eternal130.electricityinnovation.inventory.GuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemSteelyard extends ItemTerra {
    public ItemSteelyard(){
        super();
        this.setUnlocalizedName("steelyard");
        this.setCreativeTab(TFCTabs.TFC_TOOLS);
        stackable=false;
        this.setTextureName(ElectricityInnovation.MODID + ":" + "steelyard");
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getIconString());
    }
    @Override
    public boolean shouldRotateAroundWhenRendering() { return true; }
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        NBTTagCompound nbt;
        nbt = itemStackIn.hasTagCompound()?itemStackIn.getTagCompound():new NBTTagCompound();
        if (!nbt.hasKey("Weight"))
        {
            nbt.setFloat("Weight", 10.0f);
        }
        itemStackIn.setTagCompound(nbt);
        if (!worldIn.isRemote)
        {
            if (!playerIn.isSneaking())
            {
                int id = GuiHandler.GUI_STEELYARD;
                playerIn.openGui(ElectricityInnovation.instance, id, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
            }
        }
        return itemStackIn;
    }
}
