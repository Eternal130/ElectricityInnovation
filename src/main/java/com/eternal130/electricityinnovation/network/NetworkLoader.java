package com.eternal130.electricityinnovation.network;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkLoader {
    //这里信道名字不能过长,例如本mod的modid填进去会溢出,其他内容也不清楚为什么这样做
    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel("EI");

    private static int nextID = 0;

    public NetworkLoader(FMLPreInitializationEvent event)
    {
        registerMessage(messagepacket.Handler.class, messagepacket.class, Side.SERVER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        instance.registerMessage(messageHandler, requestMessageType, nextID++, side);
    }
}
