package com.Eternal130.electricityinnovation.client.gui;

import com.Eternal130.electricityinnovation.ElectricityInnovation;
import com.Eternal130.electricityinnovation.inventory.ContainerSteelyard;
import com.Eternal130.electricityinnovation.network.NetworkLoader;
import com.Eternal130.electricityinnovation.network.messagepacket;
import com.dunk.tfc.api.Food;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiSteelyard extends GuiContainer {
    private static final int BUTTON_LEFT = 0;
    private static final int BUTTON_RIGHT = 1;
    private static final int BUTTON_CUT = 2;
    protected ContainerSteelyard inventory;
    private static final String TEXTURE_PATH = ElectricityInnovation.MODID + ":" + "textures/gui/container/guisteelyard.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);

    private long lastClick = 0L;
    private int lastX;
    private int lastY;

    public GuiSteelyard(ContainerSteelyard inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 190;
        this.inventory=inventorySlotsIn;
//        this.weight=this.inventory.getweight();
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
//        this.inventory.taking=super.func_146978_c(this.inventory.productSlot.xDisplayPosition,this.inventory.productSlot.yDisplayPosition,16,16,mouseX,mouseY);
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
//        this.buttonList.add(new GuiButton(BUTTON_CUT,offsetX+129,offsetY+76,22,13,I18n.format("container.steelyard.cut")));
    }
    @Override
    protected void actionPerformed(GuiButton button) {

//        Slot slot = (Slot)this.inventory.inventorySlots.get(0);

        switch (button.id)
        {
            case BUTTON_LEFT:
                this.inventory.setweight(this.inventory.getweight()+0.1f);
                messagepacket message = new messagepacket();
                message.weight=this.inventory.getweight();
                NetworkLoader.instance.sendToServer(message);
                break;
            case BUTTON_RIGHT:
                this.inventory.setweight(this.inventory.getweight()-0.1f);
                messagepacket messagea = new messagepacket();
                messagea.weight=this.inventory.getweight();
                NetworkLoader.instance.sendToServer(messagea);
                break;
//            case BUTTON_CUT:
//                if(this.inventory.foodSlot.getStack()!=null&&Food.getWeight(this.inventory.foodSlot.getStack())>=this.inventory.getweight()){
//                    this.inventory.cut();
//                }
            default:
                super.actionPerformed(button);
                return;
        }

    }
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        if(this.lastX<=offsetX+140-(int)this.inventory.getweight() && this.lastX>=offsetX+130-(int)this.inventory.getweight() && this.lastY>=offsetY+33&this.lastY<=offsetY+49 && clickedMouseButton==0&&mouseX<=offsetX+140-(int)this.inventory.getweight() && mouseX>=offsetX+130-(int)this.inventory.getweight()){
            this.inventory.setweight(offsetX+136-mouseX);
            messagepacket messageb = new messagepacket();
            messageb.weight=this.inventory.getweight();
            NetworkLoader.instance.sendToServer(messageb);
        }
        this.lastClick = timeSinceLastClick;
        this.lastX = mouseX;
        this.lastY = mouseY;
    }
}
