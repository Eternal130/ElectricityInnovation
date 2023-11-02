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
    public InventoryCrafting items = new InventoryCrafting(this, 2, 1);
    protected float weight;
    NBTTagCompound nbt;
    Slot slottool;
    SlotFoodOnly foodslot;
    ItemStack tool;

    public float getweight() {
        return this.weight;
    }

    public void setweight(float weight) {
        if (weight > 120) this.weight = 120.0f;
        else if (weight < 0) this.weight = 0.0f;
        else this.weight = (float) (Math.round(weight * 10)) / 10;
        foodslot.onSlotChanged();
    }

    public ContainerSteelyard(EntityPlayer player) {
        super();
        bagsSlotNum = player.inventory.currentItem;
        this.addSlotToContainer(foodslot = new SlotFoodOnly(items, 0, 136, 77) {
            @Override
            public void onSlotChanged() {
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
        this.addSlotToContainer(new SlotTFC(items, 1, 55, 77) {
            @Override
            public void onSlotChanged() {
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

            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeStack(EntityPlayer player) {
                int num = 0;
                for (int i = 0; i < 36; i++) {
                    if (player.inventoryContainer.getSlotFromInventory(player.inventory, i).getStack() != null && player.inventoryContainer.getSlotFromInventory(player.inventory, i).getStack().getItem() instanceof IKnife)
                        num++;
                }
                return num > 0;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new SlotTFC(player.inventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            if (i == bagsSlotNum)
                this.addSlotToContainer(new SlotForShowOnly(player.inventory, i, 8 + i * 18, 166));
            else
                this.addSlotToContainer(new SlotTFC(player.inventory, i, 8 + i * 18, 166));
        }
        this.slottool = player.inventoryContainer.getSlotFromInventory(player.inventory, bagsSlotNum);
        this.tool = player.inventory.getCurrentItem();
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

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return new ItemStack(ItemLoader.steelyard).isItemEqual(playerIn.getCurrentEquippedItem());
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        nbt.setFloat("Weight", this.weight);
        this.tool.setTagCompound(nbt);
        this.slottool.putStack(this.tool);
        Slot slot = this.getSlot(0);
        if (playerIn.isClientWorld()) {
            ItemStack foodStack = slot.getStack();
            if (foodStack != null && foodStack.getItem() instanceof IFood) {
                playerIn.dropPlayerItemWithRandomChoice(foodStack, false);
                slot.putStack(null);
            }
        }
    }

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

    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
        // par3 q是4,中键在任何情况下都是3,左键和右键是0,shift加左键右键是1 中键滚动时客户端和服务端返回值分别是1和0,可能和某个mod有关
        if(this.getSlot(1).getStack() != null && par1 == 1 && (par3 == 0 || par3 == 1)){
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
//        par4EntityPlayer.addChatMessage(new ChatComponentText("par1="+par1 + " par3=" + par3));
        return super.slotClick(par1, par2, par3, par4EntityPlayer);
    }
}