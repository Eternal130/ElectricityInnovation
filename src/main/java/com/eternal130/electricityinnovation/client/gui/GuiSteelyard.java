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
    //增加重量按钮,每次点击加0.1
    private static final int BUTTON_LEFT = 0;
    //减少重量按钮,每次点击-0.1
    private static final int BUTTON_RIGHT = 1;
    //绑定gui对应的container
    protected ContainerSteelyard inventory;
    //gui材质
    private static final String TEXTURE_PATH = ElectricityInnovation.MODID + ":" + "textures/gui/container/guisteelyard.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    //用于存储拖动秤砣时上次点击位置
    private int lastX;
    private int lastY;

    public GuiSteelyard(ContainerSteelyard inventorySlotsIn)
    {
        super(inventorySlotsIn);
        //gui大小
        this.xSize = 176;
        this.ySize = 190;
        this.inventory=inventorySlotsIn;
    }
    //绘制gui背景
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        //设置材质文件
        this.mc.getTextureManager().bindTexture(TEXTURE);
        //gui居中,width和height是窗口宽度和高度
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        //4个参数分别是绘制x起始位置,绘制y起始位置,材质文件x轴起点,材质文件y轴起点,绘制宽度,绘制高度,屏幕和窗口坐标均是左上角为(0,0)
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //设置材质文件
        this.mc.getTextureManager().bindTexture(TEXTURE);
        //绘制秤砣，6个参数分别为x绘制起点，y绘制起点，材质x起点，材质y起点，宽度，高度，绘制起点为gui窗口左上角，材质起点为材质左上角
        this.drawTexturedModalRect(131-(int)this.inventory.getweight(),32,176,0,11,27);
        //绘制重量显示,三个参数为绘制字符串,绘制x起点,绘制y起点,字体颜色
        this.fontRendererObj.drawString(String.valueOf(this.inventory.getweight()),55,67,0xFFFFFF);
        String title = I18n.format("container.steelyard");
        //绘制gui标题,x方向居中显示
        this.fontRendererObj.drawString(title, (this.xSize - this.fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);
    }
    //添加gui组件,这里用来添加按钮
    @Override
    public void initGui()
    {
        super.initGui();
        //设置绘制起点
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        //GuiButton构造函数中6个参数分别为按钮id,x起点,y起点,宽度,高度,显示文本,这里使用匿名内部类绘制材质
        this.buttonList.add(new GuiButton(BUTTON_LEFT, offsetX + 24, offsetY + 78, 10, 15, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY)
            {
                if (this.visible)
                {
                    //绑定材质文件
                    mc.getTextureManager().bindTexture(TEXTURE);
                    //鼠标指针和按钮左上角的相对位置
                    int x = mouseX - this.xPosition, y = mouseY - this.yPosition;
                    //如果指针在按钮范围内
                    if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                    {
                        //绘制选中时材质
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, 42, this.width, this.height);
                    }
                    else
                    {
                        //绘制未选中时材质
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
    //设置按钮点击时的逻辑
    @Override
    protected void actionPerformed(GuiButton button) {
        //根据传入的参数选择要执行的方法
        if (button.id == BUTTON_LEFT){
            //将杆秤的分割重量+0.1
            this.inventory.setweight(this.inventory.getweight()+0.1f);
        }
        else if (button.id == BUTTON_RIGHT){
            this.inventory.setweight(this.inventory.getweight()-0.1f);
        }
        //和服务端通讯,设置container的属性,否则按钮点击后仅在客户端显示,实际上没用
        messagepacket message = new messagepacket();
        message.weight=this.inventory.getweight();
        NetworkLoader.instance.sendToServer(message);
    }
    //设计鼠标指针点击并拖动时实现的功能,4个参数分别为指针x坐标,指针y坐标,点击的按键,距离上次点击的时间,
    // 坐标是相对于窗口左上角,上次点击时间可能在其他地方存储,单位可能是tick,这里并没有用到
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        //设置相对于gui左上角坐标
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        //如果按下鼠标左键并拖动,且当前和上次点击位置均在秤砣判定范围内,则相应增加或减少秤砣称重,实现通过鼠标拖动改变分割重量
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
