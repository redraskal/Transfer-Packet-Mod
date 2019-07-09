package me.jedimastersoda.transfer.network;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TransferPacket implements IMessage {

  public String ip_address;

  @Override
  public void toBytes(ByteBuf buf) {}

  @Override
  public void fromBytes(ByteBuf buf) {
    try {
      this.ip_address = new String(buf.array(), "ASCII");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public static class Handler implements IMessageHandler<TransferPacket, IMessage> {

    @Override
    public IMessage onMessage(TransferPacket message, MessageContext ctx) {
      if(message.ip_address == null) return null;

      Minecraft.getMinecraft().addScheduledTask(new Runnable() {
        @Override
        public void run() {
          ServerData serverData = new ServerData("Transfer", message.ip_address, false);
          GuiMultiplayer multiplayerScreen = new GuiMultiplayer(new GuiMainMenu());

          Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();

          GuiConnecting connectingScreen = new GuiConnecting(multiplayerScreen, Minecraft.getMinecraft(), serverData);

          Minecraft.getMinecraft().displayGuiScreen(connectingScreen);
        }
      });

      return null;
    }
  }
}