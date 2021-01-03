package me.miqhtie.boopmod.events;

import me.miqhtie.boopmod.BoopMod;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;
import java.util.Random;

public class ClientRecieveChatEvent {

    private int messagesSinceLastBoop = -1;
    private boolean hideCannotMessage = false;

    @SubscribeEvent
    public void chat(ClientChatReceivedEvent event) throws IOException, ClassNotFoundException {
        if (!BoopMod.instance.boopModConfig.getModState() | !BoopMod.instance.isHypixel) return;
        String msg = event.message.getUnformattedText();

        // Hide the "You cannot message this player." message if user you are booping has messages disabled.
        if (messagesSinceLastBoop > 5) {
            messagesSinceLastBoop = -1;
            hideCannotMessage = false;
        }
        if (msg.equalsIgnoreCase("You cannot message this player.") && hideCannotMessage && messagesSinceLastBoop != -1) {
            event.setCanceled(true);
        }

        messagesSinceLastBoop++;

        if (BoopMod.instance.boopModConfig.getBoopBackEnabled()) {
            if (msg.startsWith("From ") && msg.endsWith(": Boop!")) {
                String[] message = msg.split(" ");
                String username = message[message.length - 2].replace(":", "");
                BoopMod.instance.multithreading.queueMessage("/msg " + username + " Boop! " + getRandomString(4), (long) 2000);
                hideCannotMessage = true;
                messagesSinceLastBoop = 1;
            }
        }

        if (BoopMod.instance.boopModConfig.getBoopJoinEnabled()) {
            if (msg.startsWith("Guild > ") || msg.startsWith("Friend > ") || msg.endsWith("joined.")) {
                String username = msg.split(" ")[2];
                if (inWhitelist(username)) {
                    BoopMod.instance.multithreading.queueMessage("/msg " + username + " Boop! " + getRandomString(4), (long) 2000);
                    hideCannotMessage = true;
                    messagesSinceLastBoop = 1;
                }
            }
        }

        if (BoopMod.instance.boopModConfig.getHideBoopEnabled()) {
            if (msg.startsWith("From ") && msg.endsWith(": Boop!")) event.setCanceled(true);
            if (msg.startsWith("To ") && msg.contains(": Boop! ")) event.setCanceled(true);
        }
    }

    //stackoverflow moment
    private String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    private Boolean inWhitelist(String name) throws IOException, ClassNotFoundException {
        for (String s : BoopMod.instance.whitelistConfig.read()) {
            if (s.equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
