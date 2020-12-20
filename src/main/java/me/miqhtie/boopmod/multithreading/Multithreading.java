package me.miqhtie.boopmod.multithreading;

import java.util.HashMap;

public class Multithreading {
    private final HashMap<String, Long> chatQueue = new HashMap<>();
    private ChatThread chatThread = null;

    public void queueMessage(String message, Long delay) {
        chatQueue.put(message, delay);
        if (chatThread == null) {
            System.out.println("No chat thread exists, creating new one.");
            chatThread = new ChatThread();
            chatThread.start();
        }
    }

    public void setChatThread(ChatThread thread) {
        chatThread = thread;
    }

    public HashMap<String, Long> getChatQueue() {
        return chatQueue;
    }
}
