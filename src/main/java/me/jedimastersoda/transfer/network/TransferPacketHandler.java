package me.jedimastersoda.transfer.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class TransferPacketHandler {

  public static final SimpleNetworkWrapper HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("transfer:main");

  public static void init() {
    HANDLER.registerMessage(TransferPacket.Handler.class, TransferPacket.class, 0, Side.CLIENT);
  }
}