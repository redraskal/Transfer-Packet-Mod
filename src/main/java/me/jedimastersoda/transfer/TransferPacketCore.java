package me.jedimastersoda.transfer;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.jedimastersoda.transfer.network.TransferPacketHandler;

@Mod(modid = TransferPacketCore.MODID)
public class TransferPacketCore {

    public static final String MODID = "transfer";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @EventHandler
    private void preInit(FMLPreInitializationEvent event) {
        TransferPacketHandler.init();
    }
}
