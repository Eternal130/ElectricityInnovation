package com.eternal130.electricityinnovation.client.gui;

import com.eternal130.electricityinnovation.ElectricityInnovation;
import com.eternal130.electricityinnovation.inventory.ContainerSteelyard;
import com.eternal130.electricityinnovation.network.NetworkLoader;
import com.eternal130.electricityinnovation.network.messagepacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiSteelyard extends GuiContainer {
    private static final int BUTTON_LEFT = 0;
    private static final int BUTTON_RIGHT = 1;
    protected ContainerSteelyard inventory;
    private static final String TEXTURE_PATH = ElectricityInnovation.MODID + ":" + "textures/gui/container/guisteelyard.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);

    private int lastX;
    private int lastY;

    public GuiSteelyard(ContainerSteelyard inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 190;
        this.inventory=inventorySlotsIn;
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(131-(int)this.inventory.getweight(),32,176,0,11,27);
        this.fontRendererObj.drawString(String.valueOf(this.inventory.getweight()),55,67,0xFFFFFF);
        String title = I18n.format("container.steelyard");
        this.fontRendererObj.drawString(title, (this.xSize - this.fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);
    }
    @Override
    public void initGui()
    {
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(BUTTON_LEFT, offsetX + 24, offsetY + 78, 10, 15, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY)
            {
                if (this.visible)
                {
                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.xPosition, y = mouseY - this.yPosition;

                    if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                    {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, 42, this.width, this.height);
                    }
                    else
                    {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, 27, this.width, this.height);
                    }
                }
            }
        });
        this.buttonList.add(new GuiButton(BUTTON_RIGHT, offsetX + 34, offsetY + 78, 10, 15, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY)
            {
                if (this.visible)
                {
                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.xPosition, y = mouseY - this.yPosition;

                    if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                    {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 186, 42, this.width, this.height);
                    }
                    else
                    {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 186, 27, this.width, this.height);
                    }
                }
            }
        });
    }
    @Override
    protected void actionPerformed(GuiButton button) {

        if (button.id == BUTTON_LEFT){
            this.inventory.setweight(this.inventory.getweight()+0.1f);
        }
        else if (button.id == BUTTON_RIGHT){
            this.inventory.setweight(this.inventory.getweight()-0.1f);
        }
        messagepacket message = new messagepacket();
        message.weight=this.inventory.getweight();
        NetworkLoader.instance.sendToServer(message);
    }
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        if(clickedMouseButton==0
                && this.lastX<=offsetX+140-(int)this.inventory.getweight()
                && this.lastX>=offsetX+130-(int)this.inventory.getweight()
                && this.lastY>=offsetY+33
                && this.lastY<=offsetY+49
                && mouseX<=offsetX+140-(int)this.inventory.getweight()
                && mouseX>=offsetX+130-(int)this.inventory.getweight()){
            this.inventory.setweight(offsetX+136-mouseX);
            messagepacket messageb = new messagepacket();
            messageb.weight=this.inventory.getweight();
            NetworkLoader.instance.sendToServer(messageb);
        }
        this.lastX = mouseX;
        this.lastY = mouseY;
    }
}
