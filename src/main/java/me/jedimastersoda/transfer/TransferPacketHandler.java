package me.jedimastersoda.transfer;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TransferPacketHandler {

  public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
    .named(new ResourceLocation("transfer", "main"))
    .clientAcceptedVersions(NetworkRegistry.ACCEPTVANILLA::equals)
		.serverAcceptedVersions(NetworkRegistry.ACCEPTVANILLA::equals)
    .networkProtocolVersion(() -> "1")
    .simpleChannel();

  public static void init() {
    HANDLER.registerMessage(0, TransferPacket.class, TransferPacket::encode, TransferPacket::decode, TransferPacket.Handler::handle);
  }
}