package me.miqhtie.boopmod.commands;

import me.miqhtie.boopmod.BoopMod;
import me.miqhtie.boopmod.utils.BoopModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class BoopModCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "boopmod";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        if (args.length == 0) {
            sendHelpMessage();
            return;
        }

        String cmd = args[0];
        BoopModConfig config = BoopMod.instance.boopModConfig;

        if (cmd.equalsIgnoreCase("toggle")) {
            config.setModState(!BoopMod.instance.boopModConfig.getModState());
            sendMessage(EnumChatFormatting.GREEN + "Set the mod to " + config.getModState());
        } else if (cmd.equalsIgnoreCase("toggleboopback")) {
            config.setBoopBackEnabled(!config.getBoopBackEnabled());
            sendMessage(EnumChatFormatting.GREEN + "Set boopback to " + config.getBoopBackEnabled());
        } else if (cmd.equalsIgnoreCase("toggleboopjoin")) {
            config.setBoopJoinEnabled(!config.getBoopJoinEnabled());
            sendMessage(EnumChatFormatting.GREEN + "Set boopjoin to " + config.getBoopJoinEnabled());
        } else if (cmd.equalsIgnoreCase("togglehideboop")) {
            config.setHideBoopEnabled(!config.getHideBoopEnabled());
            sendMessage(EnumChatFormatting.GREEN + "Set hideboop to " + config.getHideBoopEnabled());
        } else sendHelpMessage();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    private void sendHelpMessage() {
        String msg;
        msg = EnumChatFormatting.AQUA + "-- BoopMod | By: Miqhtie --\n" + EnumChatFormatting.AQUA + " -- General Help Menu -- \n" +
                EnumChatFormatting.YELLOW + "/" + getCommandName() + " toggle | " + EnumChatFormatting.LIGHT_PURPLE + "Enables or disables the mod\n" +
                EnumChatFormatting.YELLOW + "/" + getCommandName() + " toggleboopback | " + EnumChatFormatting.LIGHT_PURPLE + "Enables or disables booping users back when they boop you\n" +
                EnumChatFormatting.YELLOW + "/" + getCommandName() + " toggleboopjoin | " + EnumChatFormatting.LIGHT_PURPLE + "Enables or disables booping whitelisted users on join\n" +
                EnumChatFormatting.YELLOW + "/" + getCommandName() + " togglehideboop | " + EnumChatFormatting.LIGHT_PURPLE + "Enables or disabled hiding boops from chat\n\n" +
                EnumChatFormatting.AQUA + " -- Whitelist Help Menu -- \n" +
                EnumChatFormatting.YELLOW + "/boopwhitelist add (username) | " + EnumChatFormatting.LIGHT_PURPLE + "Adds a user to the boopmod whitelist\n" +
                EnumChatFormatting.YELLOW + "/boopwhitelist remove (username) | " + EnumChatFormatting.LIGHT_PURPLE + "Removes a user from the boopmod whitelist\n" +
                EnumChatFormatting.YELLOW + "/boopwhitelist list | " + EnumChatFormatting.LIGHT_PURPLE + " Lists the current boopmod whitelist\n\n";
        sendMessage(msg);
    }

    private void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(message));
    }
}
