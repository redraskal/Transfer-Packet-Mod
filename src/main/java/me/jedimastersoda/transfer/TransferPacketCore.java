package me.jedimastersoda.transfer;

import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.jedimastersoda.transfer.network.TransferPacketHandler;

@Mod(TransferPacketCore.MODID)
public class TransferPacketCore {

    public static final String MODID = "transfer";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public TransferPacketCore() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            TransferPacketHandler.init();
        });
    }
}
