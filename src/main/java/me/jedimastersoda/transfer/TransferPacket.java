package me.jedimastersoda.transfer;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TransferPacket {

  public final String ip_address;

  public TransferPacket(String ip_address) {
    this.ip_address = ip_address;
  }

  public static void encode(TransferPacket pkt, PacketBuffer buf) {}

  public static TransferPacket decode(PacketBuffer buf) {
    return new TransferPacket(buf.readString());
  }

  public static class Handler {

    public static void handle(TransferPacket message, Supplier<NetworkEvent.Context> ctx) {
      TransferPacketCore.LOGGER.info("Transferring player to " + message.ip_address);

      ctx.get().enqueueWork(() -> {
        ServerData serverData = new ServerData("Transfer", message.ip_address, false);
        MultiplayerScreen multiplayerScreen = new MultiplayerScreen(new MainMenuScreen());

        Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
        
        Minecraft.getInstance().displayGuiScreen(new ConnectingScreen(multiplayerScreen, Minecraft.getInstance(), serverData));
      });

      ctx.get().setPacketHandled(true);
    }
  }
}