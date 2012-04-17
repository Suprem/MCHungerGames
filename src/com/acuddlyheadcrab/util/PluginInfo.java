package com.acuddlyheadcrab.util;

import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.acuddlyheadcrab.MCHungerGames.HungerGames;



public class PluginInfo {
    public final static Logger log = Logger.getLogger("Minecraft");
    
    public static HungerGames plugin;
    public PluginInfo(HungerGames instance){plugin = instance;}
    
    public static final boolean debug = true;
    
    private static ChatColor red = ChatColor.RED;
    private static ChatColor aqua = ChatColor.AQUA;
    private static ChatColor gray = ChatColor.GRAY;
    
    public static void sendPluginInfo(String message) {
        PluginDescriptionFile plugdes = plugin.getDescription();
        String pluginname = plugdes.getName();
        log.info("[" + pluginname + "] " + message);
    }
    
    public static void sendTestMsg(CommandSender sender, String msg) {
        sender.sendMessage("        MCHungerGames TEST:");
        sender.sendMessage(msg);
    }

    public static void sendNoPermMsg(CommandSender sender) {
        sender.sendMessage(red + "You don't have permissions to do this!");
    }
    

    public static void sendOnlyPlayerMsg(CommandSender sender) {
        sender.sendMessage(red + "You must be a player to do this!");
    }
    
    public static void sendAlreadyInGameMsg(CommandSender sender, String arena) {
        sender.sendMessage(red + arena+"is currently in game!");
    }
    
    public static void sendCommandInfo(CommandSender sender, String cmd, String desc){
        sender.sendMessage(ChatColor.DARK_PURPLE+cmd+gray+": "+desc);
    }
    
    public static void sendCommandsHelp(CommandSender sender) {
        String v = plugin.getDescription().getVersion();
        sender.sendMessage(aqua + "    MC Hunger Games v" + v);
        Map<String, String> cmd_map = Utility.getCommandsAndDescs();
        for (String cmd : cmd_map.keySet()) {
            String desc = cmd_map.get(cmd);
            sender.sendMessage(red + cmd + gray + ": " + desc);
        }
        sender.sendMessage(red+"hg reload"+gray+": "+"Reloads MCHungerGames' config");
    }
    
    
    public static void wrongFormatMsg(CommandSender sender, String msg) {
        sender.sendMessage(red+msg);
    }

}
