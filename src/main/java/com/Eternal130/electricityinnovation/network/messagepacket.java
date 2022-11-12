package com.Eternal130.electricityinnovation.network;

import com.Eternal130.electricityinnovation.inventory.ContainerSteelyard;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

import java.util.logging.Handler;

public class messagepacket implements IMessage {
    public float weight;
//    public messagepacket(float weight){
//        this.weight=weight;
//    }
    @Override
    public void fromBytes(ByteBuf byteBuf) {
        weight= byteBuf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeFloat(weight);
    }
    public static class Handler implements IMessageHandler<messagepacket, IMessage>{

        @Override
        public IMessage onMessage(messagepacket messagepacket, MessageContext messageContext) {
            if (messageContext.side == Side.SERVER)
            {
                ContainerSteelyard steelyard= (ContainerSteelyard) messageContext.getServerHandler().playerEntity.openContainer;
                steelyard.setweight(messagepacket.weight);
            }
            return null;
        }
    }
}
