package com.eternal130.electricityinnovation.inventory;

import com.eternal130.electricityinnovation.ElectricityInnovation;
import com.eternal130.electricityinnovation.client.gui.GuiSteelyard;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_STEELYARD = 1;

    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(ElectricityInnovation.instance, this);
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_STEELYARD) {
            return new ContainerSteelyard(player);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_STEELYARD) {
            return new GuiSteelyard(new ContainerSteelyard(player));
        }
        return null;
    }
}
