package me.miqhtie.boopmod.events;

import me.miqhtie.boopmod.BoopMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ConnectToServerEvent {
    @SubscribeEvent
    public void connectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();

        if (serverData != null) {
            if (serverData.serverIP.toLowerCase().contains("hypixel.net")) {
                BoopMod.instance.isHypixel = true;
            }
        }
    }

}
