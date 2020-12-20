package me.miqhtie.boopmod.events;

import me.miqhtie.boopmod.BoopMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class DisconnectedFromServerEvent {
    @SubscribeEvent
    public void disconnectedFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        BoopMod.instance.isHypixel = false;
    }
}
