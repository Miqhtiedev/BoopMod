package me.miqhtie.boopmod.commands;

import me.miqhtie.boopmod.BoopMod;
import me.miqhtie.boopmod.utils.BoopModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        String cmd = args[0].toLowerCase();
        BoopModConfig config = BoopMod.instance.boopModConfig;


        if (cmd.equals("toggle")) {
            config.setModState(!BoopMod.instance.boopModConfig.getModState());
            sendMessage(EnumChatFormatting.GREEN + "Set the mod to " + config.getModState());
        } else if (cmd.equals("toggleboopback")) {
            config.setBoopBackEnabled(!config.getBoopBackEnabled());
            sendMessage(EnumChatFormatting.GREEN + "Set boopback to " + config.getBoopBackEnabled());
        } else if (cmd.equals("toggleboopjoin")) {
            config.setBoopJoinEnabled(!config.getBoopJoinEnabled());
            sendMessage(EnumChatFormatting.GREEN + "Set boopjoin to " + config.getBoopJoinEnabled());
        } else if (cmd.equals("togglehideboop")) {
            config.setHideBoopEnabled(!config.getHideBoopEnabled());
            sendMessage(EnumChatFormatting.GREEN + "Set hideboop to " + config.getHideBoopEnabled());
        } else if (cmd.equals("whitelist")) {
            try {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("list")) {
                        StringBuilder msg = new StringBuilder(EnumChatFormatting.BOLD + "" + EnumChatFormatting.LIGHT_PURPLE + "Whitelisted users to Boop! on join\n");
                        if (BoopMod.instance.whitelistConfig.read().size() == 0) {
                            msg.append(EnumChatFormatting.YELLOW + "No one ): Add someone by doing /boopmod whitelist add (user)");
                        }
                        for (String message : BoopMod.instance.whitelistConfig.read()) {
                            msg.append(EnumChatFormatting.AQUA + " ").append(message).append(".\n");
                        }
                        sendMessage(msg.toString());
                        return;
                    } else if (args.length > 2) {
                        String username = args[2];
                        if (args[1].equalsIgnoreCase("add")) {
                            if (inWhitelist(username)) {
                                sendMessage(EnumChatFormatting.RED + username + " is already whitelisted!");
                                return;
                            }
                            BoopMod.instance.whitelistConfig.add(username);
                            sendMessage(EnumChatFormatting.GREEN + "Added " + username + " to the whitelist!");
                            return;
                        } else if (args[1].equalsIgnoreCase("remove")) {
                            if (!inWhitelist(username)) {
                                sendMessage(EnumChatFormatting.RED + username + " is not whitelisted!");
                                return;
                            }
                            BoopMod.instance.whitelistConfig.remove(username);
                            sendMessage(EnumChatFormatting.GREEN + "Removed " + username + " from the whitelist!");
                            return;
                        }
                    }
                }
                sendHelpMessage();
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(EnumChatFormatting.RED + "Something wrong happened that shouldn't... awkward.");
            }
        } else {
            sendHelpMessage();
        }
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
                EnumChatFormatting.YELLOW + "/" + getCommandName() + " whitelist add (username) | " + EnumChatFormatting.LIGHT_PURPLE + "Adds a user to the boopmod whitelist\n" +
                EnumChatFormatting.YELLOW + "/" + getCommandName() + " whitelist remove (username) | " + EnumChatFormatting.LIGHT_PURPLE + "Removes a user from the boopmod whitelist\n" +
                EnumChatFormatting.YELLOW + "/" + getCommandName() + " whitelist list | " + EnumChatFormatting.LIGHT_PURPLE + " Lists the current boopmod whitelist\n\n";
        sendMessage(msg);
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> defaultSubcommands = new ArrayList<>(Arrays.asList("toggle", "toggleboopback", "toggleboopjoin", "togglehideboop", "whitelist"));

        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, defaultSubcommands.toArray(new String[0]));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("whitelist")) {
                List<String> whitelistSubcommands = new ArrayList<>(Arrays.asList("add", "remove", "list"));
                return getListOfStringsMatchingLastWord(args, whitelistSubcommands.toArray(new String[0]));
            }
        }
        return null;
    }

    private Boolean inWhitelist(String name) throws IOException, ClassNotFoundException {
        for (String s : BoopMod.instance.whitelistConfig.read()) {
            if (s.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    private void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(message));
    }
}
