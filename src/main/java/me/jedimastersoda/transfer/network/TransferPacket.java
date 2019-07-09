package me.jedimastersoda.transfer.network;

import io.netty.buffer.ByteBuf;
import me.jedimastersoda.transfer.TransferPacketCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TransferPacket implements IMessage {

  public String ip_address;

  @Override
  public void toBytes(ByteBuf buf) {}

  @Override
  public void fromBytes(ByteBuf buf) {
    this.ip_address = ByteBufUtils.readUTF8String(buf);
  }

  public static class Handler implements IMessageHandler<TransferPacket, IMessage> {

    @Override
    public IMessage onMessage(TransferPacket message, MessageContext ctx) {
      if(message.ip_address == null) return null;
      
      TransferPacketCore.LOGGER.info("Transferring player to " + message.ip_address);

      Minecraft.getMinecraft().addScheduledTask(new Runnable() {
        @Override
        public void run() {
          ServerData serverData = new ServerData("Transfer", message.ip_address, false);
          GuiMultiplayer multiplayerScreen = new GuiMultiplayer(new GuiMainMenu());

          Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();

          Minecraft.getMinecraft().displayGuiScreen(new GuiConnecting(multiplayerScreen, Minecraft.getMinecraft(), serverData));
        }
      });

      return null;
    }
  }
}