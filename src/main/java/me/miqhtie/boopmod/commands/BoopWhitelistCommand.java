package me.miqhtie.boopmod.commands;

import me.miqhtie.boopmod.BoopMod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

public class BoopWhitelistCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "boopwhitelist";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        if(args.length == 0){
            sendMessage(EnumChatFormatting.RED + "Invalid command arguments. Please do /boopmod for help with commands!");
            return;
        }
        args[0] = args[0].toLowerCase();
        if(args[0].equalsIgnoreCase("list")){
            try{
                String msg = EnumChatFormatting.BOLD + "" + EnumChatFormatting.LIGHT_PURPLE + "Whitelisted users to Boop! on join\n";
                if(BoopMod.instance.whitelistConfig.read().size() == 0){
                    msg += EnumChatFormatting.YELLOW + "No one ): Add someone by doing /boopwhitelist add (user)";
                }
                for(String message : BoopMod.instance.whitelistConfig.read()){
                    msg += EnumChatFormatting.AQUA + " " + message + ".\n";
                }
                sendMessage(msg);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(EnumChatFormatting.RED + "Something wrong happened that shouldn't... awkward.");
                return;
            }
        }

        if(args.length < 2){
            sendMessage(EnumChatFormatting.RED + "Invalid command arguments. Please do /boopmod for help with commands!");
            return;
        }

        if(args[0].equalsIgnoreCase("add")){
            String username = args[1];
            try {
                if(inWhitelist(username)){
                    sendMessage(EnumChatFormatting.RED + username + " is already whitelisted!");
                    return;
                }

                BoopMod.instance.whitelistConfig.add(username);
                sendMessage(EnumChatFormatting.GREEN + "Added " + username + " to the whitelist!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(EnumChatFormatting.RED + "Something wrong happened that shouldn't... awkward.");
                return;
            }
        }

        if(args[0].equalsIgnoreCase("remove")){
            String username = args[1];
            try {
                if(!inWhitelist(username)){
                    sendMessage(EnumChatFormatting.RED + username + " is not whitelisted!");
                    return;
                }
                BoopMod.instance.whitelistConfig.remove(username);
                sendMessage(EnumChatFormatting.GREEN + "Removed " + username + " from the whitelist!");
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(EnumChatFormatting.RED + "Something wrong happened that shouldn't... awkward.");
            }
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    private void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(message));
    }

    private Boolean inWhitelist(String name) throws IOException, ClassNotFoundException {
        for (String s : BoopMod.instance.whitelistConfig.read()){
            if(s.equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
