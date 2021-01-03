package me.miqhtie.boopmod.multithreading;

import me.miqhtie.boopmod.BoopMod;
import net.minecraft.client.Minecraft;

import java.util.Map;
import java.util.Set;

public class ChatThread extends Thread {

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            if (BoopMod.instance.multithreading.getChatQueue().isEmpty()) {
                running = false;
            } else {
                Map.Entry<String, Long> entry = getFirstEntry(BoopMod.instance.multithreading.getChatQueue().entrySet());
                if (entry != null) {
                    try {
                        sleep(entry.getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Minecraft.getMinecraft().thePlayer != null) {
                        Minecraft.getMinecraft().thePlayer.sendChatMessage(entry.getKey());
                        BoopMod.instance.multithreading.getChatQueue().remove(entry.getKey());
                    }
                }
            }
        }
        BoopMod.instance.multithreading.setChatThread(null);
    }


    private Map.Entry<String, Long> getFirstEntry(Set<Map.Entry<String, Long>> entrySet) {
        for (Map.Entry<String, Long> entry : entrySet) {
            return entry;
        }
        return null;
    }
}
