package com.Eternal130.electricityinnovation.inventory;

import codechicken.lib.inventory.ItemKey;
import com.Eternal130.electricityinnovation.item.ItemLoader;
import com.dunk.tfc.Containers.ContainerTFC;
import com.dunk.tfc.Containers.Slots.SlotFoodOnly;
import com.dunk.tfc.Containers.Slots.SlotForShowOnly;
import com.dunk.tfc.Containers.Slots.SlotTFC;
import com.dunk.tfc.Items.Tools.ItemKnife;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Tools.IKnife;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.impl.sync.TSynchronizedShortObjectMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.Sys;
import org.omg.PortableServer.ServantActivatorPOA;

public class ContainerSteelyard extends ContainerTFC {
    public InventoryCrafting items = new InventoryCrafting(this, 2, 1);
    protected float weight;
    boolean hasknife;
    NBTTagCompound nbt;
    Slot slottool;
    Slot slotknife;
    int knifeSlotNum;
    SlotFoodOnly foodslot;
    ItemStack tool;
//    public SlotFoodOnly foodSlot ;
//    public SlotTFC productSlot ;
//    public static boolean taking=false;
//    public void cut(){
//        ItemStack product=this.foodSlot.getStack().copy();
//        float Decay=Food.getDecay(product);
//        float perc=this.getweight()/Food.getWeight(product);
//        Food.setWeight(product,this.getweight());
//        Food.setDecay(product,Decay*perc);
//        this.putStackInSlot(1,product);
//        float decay=Food.getDecay(this.foodSlot.getStack())*(Food.getWeight(this.foodSlot.getStack())-this.getweight())/Food.getWeight(this.foodSlot.getStack());
//        Food.setWeight(this.foodSlot.getStack(),Food.getWeight(this.foodSlot.getStack())-this.getweight());
//        Food.setDecay(this.foodSlot.getStack(),decay);
//        if(Food.getWeight(this.foodSlot.getStack())<=0)
//            this.putStackInSlot(0,null);
//    }
//    @Override
//    public void detectAndSendChanges()
//    {
//        super.detectAndSendChanges();
//
//        for (ICrafting i : this.crafters)
//        {
//            i.sendProgressBarUpdate(this, 0, this.weight);
//        }
//    }
    public float getweight(){
//        if (!nbt.hasKey("Weight")) {
//            nbt.setFloat("Weight", 1.0f);
//        }
//        return nbt.getFloat("Weight");
        return this.weight;
    }
    public void setweight(float weight){
//        ItemStack tool=slottool.getStack();
//        if (!nbt.hasKey("Weight")) {
//            nbt.setFloat("Weight", 2.0f);
//        }
//        if(weight>120)
//            nbt.setFloat("Weight",120.0f);
//        else if(weight<0)
//            nbt.setFloat("Weight",0.0f);
//        else
//            nbt.setFloat("Weight",(float)(Math.round(weight*10))/10);
//        tool.setTagCompound(nbt);
//        slottool.putStack(tool);
        if(weight>120) this.weight=120.0f;
        else if(weight<0) this.weight=0.0f;
        else this.weight=(float)(Math.round(weight*10))/10;
//        System.out.println(this.weight+"a");
//        Slot slot = (Slot)this.inventorySlots.get(0);
        foodslot.onSlotChanged();
    }
    public ContainerSteelyard(EntityPlayer player){
        super();
        bagsSlotNum = player.inventory.currentItem;
        this.addSlotToContainer(foodslot=new SlotFoodOnly(items, 0, 136, 77)
        {
            @Override
            public  void onSlotChanged(){
                Slot slot = (Slot)ContainerSteelyard.this.inventorySlots.get(0);
                Slot slott = (Slot)ContainerSteelyard.this.inventorySlots.get(1);
                if(slot.getStack()!=null){
                    ItemStack food=slot.getStack();
                    ItemStack product=slot.getStack().copy();
                    if(Food.getWeight(food)>=getweight()&&getweight()>0){
                        float decay=Food.getDecay(food)*ContainerSteelyard.this.getweight()/Food.getWeight(food);
                        Food.setDecay(product,decay);
                        Food.setWeight(product,getweight());
                        slott.putStack(product);
                    }else slott.putStack(null);
                }else slott.putStack(null);
            }
        });
        this.addSlotToContainer(new SlotTFC(items, 1, 55, 77)
        {
            @Override
            public void onSlotChanged()
            {
                Slot slot = (Slot)ContainerSteelyard.this.inventorySlots.get(0);
                Slot slott = (Slot)ContainerSteelyard.this.inventorySlots.get(1);
                ItemStack food = slot.getStack();
                if(!slott.getHasStack()&& slot.getHasStack()&&Food.getWeight(slot.getStack())>=getweight()&&getweight()>0){
                    if(food!=null){
                        float decay=Food.getDecay(food)*(Food.getWeight(food)-getweight())/Food.getWeight(food);
                        Food.setDecay(food,decay);
                        Food.setWeight(food,Food.getWeight(food)-getweight());
                        if(Food.getWeight(food)>=0.1f)
                            slot.putStack(food);
                        else slot.putStack(null);
                    }
                }
                super.onSlotChanged();
            }
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
            @Override
            public boolean canTakeStack(EntityPlayer player) {
                int num=0;
                for(int i=0;i<36;i++){
                    if(player.inventoryContainer.getSlotFromInventory(player.inventory, i).getStack()!=null && player.inventoryContainer.getSlotFromInventory(player.inventory, i).getStack().getItem() instanceof IKnife)
                    num++;
                }
                if(num>0)
                return true;
                else return false;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new SlotTFC(player.inventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
//            this.addSlotToContainer(new SlotTFC(player.inventory, i, 8 + i * 18, 166));
            if (i == bagsSlotNum)
                this.addSlotToContainer(new SlotForShowOnly(player.inventory, i, 8 + i * 18, 166));
            else
                this.addSlotToContainer(new SlotTFC(player.inventory, i, 8 + i * 18, 166));
        }
//        this.slottool=(Slot) ContainerSteelyard.this.inventorySlots.get(bagsSlotNum);
        this.slottool=player.inventoryContainer.getSlotFromInventory(player.inventory, bagsSlotNum);
        this.tool=player.inventory.getCurrentItem();
//        if(tool==null)System.out.println("nooo");
//        if(!slottool.getHasStack())System.out.println("no");
        if (tool!=null&&tool.hasTagCompound())
        {
            nbt = tool.getTagCompound();
//            System.out.println("yes");
        }
        else
        {
            nbt = new NBTTagCompound();
//            System.out.println("no");
        }
        if (!nbt.hasKey("Weight")) {
            nbt.setFloat("Weight", 1.0f);
//            System.out.println("noo");
        }
        this.weight=nbt.getFloat("Weight");
//        System.out.println(nbt.getFloat("Weight"));
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return new ItemStack(ItemLoader.steelyard).isItemEqual(playerIn.getCurrentEquippedItem());
    }
    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
//        nbt.setFloat("Weight",this.weight);
//        System.out.println(this.weight+"b");
//        if(tool!=null)
//        System.out.println("succeed");
        nbt.setFloat("Weight",this.weight);
        this.tool.setTagCompound(nbt);
        this.slottool.putStack(this.tool);
        Slot slot = (Slot)ContainerSteelyard.this.inventorySlots.get(0);
        if (playerIn.isClientWorld())
        {
            ItemStack foodStack = slot.getStack();
            if (foodStack != null&&foodStack.getItem() instanceof IFood)
            {
                playerIn.dropPlayerItemWithRandomChoice(foodStack, false);
                slot.putStack(null);
            }
        }
    }
    public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum) {
        ItemStack origStack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNum);
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
                slot.putStack((ItemStack)null);
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
}
