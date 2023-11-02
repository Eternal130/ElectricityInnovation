package com.eternal130.electricityinnovation.inventory;

import com.eternal130.electricityinnovation.items.ItemLoader;
import com.dunk.tfc.Containers.ContainerTFC;
import com.dunk.tfc.Containers.Slots.SlotFoodOnly;
import com.dunk.tfc.Containers.Slots.SlotForShowOnly;
import com.dunk.tfc.Containers.Slots.SlotTFC;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Tools.IKnife;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerSteelyard extends ContainerTFC {
    //用于存储格子,格子数量为第二个参数*第三个参数,此处表示为2列1行
    public InventoryCrafting items = new InventoryCrafting(this, 2, 1);
    //存储当前分割重量
    protected float weight;
    //用于在关闭gui后将存储重量保存在nbt中
    NBTTagCompound nbt;
    //装有杆秤的格子
    Slot slottool;
    //食物格
    SlotFoodOnly foodslot;
    //杆秤
    ItemStack tool;

    public float getweight() {
        return this.weight;
    }

    public void setweight(float weight) {
        //保证分割重量范围是0-120oz,每次被调用时,是在更改分割重量,因此调用食物格的onSlotChanged方法向输出格同步重量
        if (weight > 120) this.weight = 120.0f;
        else if (weight < 0) this.weight = 0.0f;
        else this.weight = (float) (Math.round(weight * 10)) / 10;
        foodslot.onSlotChanged();
    }

    public ContainerSteelyard(EntityPlayer player) {
        super();
        //将打开gui时手持格子当作杆秤格子,打开gui时快速切换格子可能会出问题
        bagsSlotNum = player.inventory.currentItem;
        //向container添加食物格
        this.addSlotToContainer(foodslot = new SlotFoodOnly(items, 0, 136, 77) {
            @Override
            public void onSlotChanged() {
                //方法被调用时,在输出格放置可能的结果
                Slot slot = (Slot) ContainerSteelyard.this.inventorySlots.get(0);
                Slot slott = (Slot) ContainerSteelyard.this.inventorySlots.get(1);
                if (slot.getStack() != null) {
                    ItemStack food = slot.getStack();
                    ItemStack product = slot.getStack().copy();
                    if (Food.getWeight(food) >= getweight() && getweight() > 0) {
                        float decay = Food.getDecay(food) * ContainerSteelyard.this.getweight() / Food.getWeight(food);
                        Food.setDecay(product, decay);
                        Food.setWeight(product, getweight());
                        slott.putStack(product);
                    } else slott.putStack(null);
                } else slott.putStack(null);
            }
        });
        //向container添加输出格
        this.addSlotToContainer(new SlotTFC(items, 1, 55, 77) {
            @Override
            public void onSlotChanged() {
                //方法被调用时,自动切割食物格的食物
                Slot slot = (Slot) ContainerSteelyard.this.inventorySlots.get(0);
                Slot slott = (Slot) ContainerSteelyard.this.inventorySlots.get(1);
                ItemStack food = slot.getStack();
                if (!slott.getHasStack() && slot.getHasStack() && Food.getWeight(slot.getStack()) >= getweight() && getweight() > 0) {
                    if (food != null) {
                        float decay = Food.getDecay(food) * (Food.getWeight(food) - getweight()) / Food.getWeight(food);
                        Food.setDecay(food, decay);
                        Food.setWeight(food, Food.getWeight(food) - getweight());
                        if (Food.getWeight(food) >= 0.1f)
                            slot.putStack(food);
                        else slot.putStack(null);
                    }
                }
                super.onSlotChanged();
            }
            //输出格不能从gui放入任何物品
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
            //只有背包内存在刀时才可以切割
            @Override
            public boolean canTakeStack(EntityPlayer player) {
                int num = 0;
                for (int i = 0; i < 36; i++) {
                    //不涉及物品修改时,可以用玩家背包容器检查是否有刀,涉及物品修改时不能从玩家背包容器修改,否则会导致空指针异常
                    if (player.inventoryContainer.getSlotFromInventory(player.inventory, i).getStack() != null && player.inventoryContainer.getSlotFromInventory(player.inventory, i).getStack().getItem() instanceof IKnife)
                        num++;
                }
                return num > 0;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                //打开gui时从玩家背包加载36个格子
                this.addSlotToContainer(new SlotTFC(player.inventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            //打开gui时从玩家快捷栏加载9个格子
            if (i == bagsSlotNum)
                //加载到杆秤格时,设置格子为仅展示,防止打开gui时扔出杆秤导致崩溃
                this.addSlotToContainer(new SlotForShowOnly(player.inventory, i, 8 + i * 18, 166));
            else
                this.addSlotToContainer(new SlotTFC(player.inventory, i, 8 + i * 18, 166));
        }
        this.slottool = player.inventoryContainer.getSlotFromInventory(player.inventory, bagsSlotNum);
        this.tool = player.inventory.getCurrentItem();
        //打开gui时从杆秤nbt读取分割重量,如果没有就初始化为1oz
        if (tool != null && tool.hasTagCompound()) {
            nbt = tool.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }
        if (!nbt.hasKey("Weight")) {
            nbt.setFloat("Weight", 1.0f);
        }
        this.weight = nbt.getFloat("Weight");
    }
    //此方法在服务器端调用,用于判断玩家是否能和container交互,这里只要玩家手持物品为杆秤即可通过判断
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return new ItemStack(ItemLoader.steelyard).isItemEqual(playerIn.getCurrentEquippedItem());
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        //gui关闭时将当前分割重量保存在nbt中
        nbt.setFloat("Weight", this.weight);
        this.tool.setTagCompound(nbt);
        this.slottool.putStack(this.tool);
        //如果食物格有物品,将其掉落在主世界上
        Slot slot = this.getSlot(0);
        if (playerIn.isClientWorld()) {
            ItemStack foodStack = slot.getStack();
            if (foodStack != null && foodStack.getItem() instanceof IFood) {
                playerIn.dropPlayerItemWithRandomChoice(foodStack, false);
                slot.putStack(null);
            }
        }
    }
    //此方法在按住shift点击物品时调用,用于处理快速分配物品,以下内容为在背包和快捷栏按shift点击时转移物品到食物格,shift点击食物格和输出格时,转移物品到背包
    @Override
    public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum) {
        ItemStack origStack = null;
        Slot slot = this.getSlot(slotNum);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            origStack = slotStack.copy();
            if (slotNum < 2) {
                if (!this.mergeItemStack(slotStack, 2, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(slotStack, 0, 1, false)) {
                return null;
            }

            if (slotStack.stackSize <= 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.stackSize == origStack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, slotStack);
        }

        return origStack;
    }
    //第一个参数是点击的格子id,第二个参数未测试,第三个参数为点击类型,1.12.2版本的mcp据文档所说完成度100%,
    // 虽然1.12此方法的第三个参数为枚举类型,但枚举类型与这里的数字一一对应,
    //0为左键或右键单击,1为按住shift左键或右键单击,2在1.12版本中是按f键,这里未知,我测试时没有按出来过,3是按鼠标中键,4是按q,丢出物品,
    // 5在1.12中应该是点击配方书中的合成表,这里未知,6是鼠标双击,该方法在服务端和客户端都会调用,双击物品时服务端par3的值为0,客户端为6
    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
        // par3 q是4,中键在任何情况下都是3,左键和右键是0,shift加左键右键是1 中键滚动时客户端和服务端返回值分别是1和0,可能和某个mod有关
        //当输出格有物品时转移输出格物品会通过以下判定,减少刀的耐久,刀耐久为0时消失
        if(this.getSlot(1).getStack() != null && par1 == 1 && (par3 == 0 || par3 == 1)){
            //倒序遍历杆秤container中所有格子,具体方向为从右往左,从下往上,食物格和输出格的id分别为0和1,不会被遍历
            for (int i = 37; i > 1; i--) {
                if (this.getSlot(i).getStack() != null
                        && this.getSlot(i).getStack().getItem() instanceof IKnife) {
                    Slot slott = this.getSlot(i);
                    ItemStack knife = slott.getStack();
                    if (knife.getItemDamage() < knife.getMaxDamage()) {
                        knife.setItemDamage(knife.getItemDamage() + 1);
                        slott.putStack(knife);
                    } else slott.putStack(null);
                    break;
                }
            }
        }
        //下面一行是用来在游戏内向玩家聊天框输出内容,用来测试
//        par4EntityPlayer.addChatMessage(new ChatComponentText("par1="+par1 + " par3=" + par3));
        //下面一行调用父类方法,如果在执行当前方法前调用,会导致有时无法扣除刀的耐久,放在最后就没问题
        return super.slotClick(par1, par2, par3, par4EntityPlayer);
    }
}