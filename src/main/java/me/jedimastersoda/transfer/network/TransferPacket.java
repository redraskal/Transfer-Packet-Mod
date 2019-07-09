package me.jedimastersoda.transfer.network;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import me.jedimastersoda.transfer.TransferPacketCore;
import me.jedimastersoda.transfer.utils.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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

        ConnectingScreen connectingScreen = new ConnectingScreen(multiplayerScreen, Minecraft.getInstance(),
            serverData);
        Field connectingText = ReflectionUtils.getField(ConnectingScreen.class, ITextComponent.class, 0);
        StringTextComponent stringTextComponent = new StringTextComponent("Transferring server connection...");

        try {
          ReflectionUtils.modifyPrivateField(connectingText, connectingScreen, stringTextComponent);
        } catch (Exception e) {
          e.printStackTrace();
        }

        Minecraft.getInstance().displayGuiScreen(connectingScreen);
      });

      ctx.get().setPacketHandled(true);
    }
  }
}