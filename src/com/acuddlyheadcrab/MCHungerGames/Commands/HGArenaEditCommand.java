package com.acuddlyheadcrab.MCHungerGames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.acuddlyheadcrab.MCHungerGames.Arenas;
import com.acuddlyheadcrab.MCHungerGames.HungerGames;
import com.acuddlyheadcrab.util.ConfigKeys;
import com.acuddlyheadcrab.util.Perms;
import com.acuddlyheadcrab.util.PluginInfo;
import com.acuddlyheadcrab.util.Utility;

public class HGArenaEditCommand implements CommandExecutor{
    
	private static HungerGames hungergames;
    public HGArenaEditCommand(HungerGames instance){hungergames = instance;}
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
        
        FileConfiguration config = hungergames.getConfig();
        
        boolean isplayer = sender instanceof Player;
        Player player = isplayer ? (Player) sender : null;
        
        if(isplayer) PluginInfo.sendPluginInfo(sender.getName()+": /"+label+Utility.concatArray(args, " "));

        if(cmd.getName().equalsIgnoreCase("hgaedit")){
            try{
                String arg1 = args[0];
                
                String arenakey = Utility.getArenaByKey(arg1);
                
                if(arenakey!=null){
                    
                    try{
                        String arg2 = args[1];
                        
                        boolean
                            corncp = arg2.equalsIgnoreCase("cornucopia")||arg2.equalsIgnoreCase("corncp")||arg2.equalsIgnoreCase("ccp"),
                            setccp = arg2.equalsIgnoreCase("setcornucopia")||arg2.equalsIgnoreCase("setcorncp")||arg2.equalsIgnoreCase("setccp")||corncp,
                            limit = arg2.equalsIgnoreCase("limit"),
                            addgm = arg2.equalsIgnoreCase("addgm"),
                            addtrib = arg2.equalsIgnoreCase("addtrib"),
                            removegm = arg2.equalsIgnoreCase("removegm"),
                            removetrib = arg2.equalsIgnoreCase("removetrib")
                        ;
                        
                        if(setccp){
                            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgae <arena> setccp command");
                            if(sender.hasPermission(Perms.HGAE_SETCCP.perm())||Utility.isGameMakersArena(sender, arenakey)){
                                if(isplayer){
                                	Arenas.setCenter(arenakey, player.getLocation());
                                    player.sendMessage(ChatColor.GREEN+"Set your location as the center of "+arenakey);
                                    return true;
                                } else PluginInfo.sendOnlyPlayerMsg(sender); return true;
                            } else PluginInfo.sendNoPermMsg(sender); return true;
                        }
                        
                        if(limit){
                            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgae <arena> setlimit command");
                            if(sender.hasPermission(Perms.HGAE_LIMIT.perm())||Utility.isGameMakersArena(sender, arenakey)){
                                try{
                                    String arg3 = args[2];
                                    try{
                                    	Arenas.configSet(arenakey, Double.parseDouble(arg3));
                                        player.sendMessage(ChatColor.GREEN+"Set "+arenakey+"'s max distance to "+arg3);
                                        return true;
                                    }catch(NumberFormatException e){
                                        PluginInfo.wrongFormatMsg(sender, arg3+" is not a valid number!"); return true;
                                    }
                                }catch(IndexOutOfBoundsException e){PluginInfo.wrongFormatMsg(sender, "/hgae <arena> limit (number)"); return true;}
                            } else PluginInfo.sendNoPermMsg(sender); return true;
                        }
                        
                        if(addgm){
                            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgae <arena> addgm command");
                            if(sender.hasPermission(Perms.HGAE_ADDGM.perm())){
                                try{
                                    String arg3 = args[2];
                                    
                                    try{
                                    	Arenas.addGM(arenakey, arg3);
                                        sender.sendMessage(ChatColor.GREEN+"Added "+arg3+" to "+arenakey+"'s gamemakers");
                                        return true;
                                    }catch(NullPointerException e){
                                        PluginInfo.wrongFormatMsg(sender, "Could not find the player \""+arg3+"\""); return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    PluginInfo.wrongFormatMsg(sender, "/hgae <arena> addgm <player>"); return true;
                                }
                            } else PluginInfo.sendNoPermMsg(sender); return true;
                        }
                        
                        if(addtrib){
                            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgae <arena> addtrib command");
                            if(sender.hasPermission(Perms.HGAE_ADDTRIB.perm())||Utility.isGameMakersArena(sender, arenakey)){
                                try{
                                    String arg3 = args[2];
                                    
                                    try{
                                    	Arenas.addTrib(arenakey, arg3);
                                        sender.sendMessage(ChatColor.GREEN+"Added "+arg3+" to "+arenakey+"'s tributes");
                                         return true;
                                    }catch(NullPointerException e){
                                        PluginInfo.wrongFormatMsg(sender, "Could not find the player \""+arg3+"\""); return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    PluginInfo.wrongFormatMsg(sender, "/hgae <arena> addtrib <player>"); return true;
                                }
                            } else PluginInfo.sendNoPermMsg(sender); return true;
                        }
                        
                        if(removegm){
                            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgae <arena> removegm command");
                            if(sender.hasPermission(Perms.HGAE_REMOVEGM.perm())){
                                try{
                                    String arg3 = args[2];
                                    
                                    try{
                                        Arenas.removeGM(arenakey, arg3);
                                        sender.sendMessage(ChatColor.GREEN+"Removed "+arg3+" from "+arenakey+"'s gamemakers");
                                         return true;
                                    }catch(NullPointerException e){
                                        PluginInfo.wrongFormatMsg(sender, "Could not find the player \""+arg3+"\""); return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    PluginInfo.wrongFormatMsg(sender, "/hgae <arena> removegm <player>"); return true;
                                }
                            } else PluginInfo.sendNoPermMsg(sender); return true;
                        }
                        
                        if(removetrib){
                            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgae <arena> removetrib command");
                            if(sender.hasPermission(Perms.HGAE_REMOVETRIB.perm())||Utility.isGameMakersArena(sender, arenakey)){
                                try{
                                    String arg3 = args[2];
                                    
                                    try{
                                        Arenas.removeTrib(arenakey, arg3);
                                        sender.sendMessage(ChatColor.GREEN+"Removed "+arg3+" from "+arenakey+"'s tributes");
                                         return true;
                                    }catch(NullPointerException e){
                                        PluginInfo.wrongFormatMsg(sender, "Could not find the player \""+arg3+"\""); return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    PluginInfo.wrongFormatMsg(sender, "/hgae <arena> removetrib <player>"); return true;
                                }
                            } else PluginInfo.sendNoPermMsg(sender); return true;
                        }
                        
                        
                    }catch(IndexOutOfBoundsException e){}
                } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+arg1+"\""); return true;
            }catch(IndexOutOfBoundsException e){}
            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted to show /hgae branch help");
            PluginInfo.sendCommandInfo(sender, "/hgae <arena>", "");
            PluginInfo.sendCommandInfo(sender, "     cornucopia (ccp)", "Set the center to your location");
            PluginInfo.sendCommandInfo(sender, "     limit", "Create a new arena at your location");
            PluginInfo.sendCommandInfo(sender, "     addgm", "Add a gamemaker");
            PluginInfo.sendCommandInfo(sender, "     addtrib", "Add a tribute");
            PluginInfo.sendCommandInfo(sender, "     removegm", "Remove a gamemaker");
            PluginInfo.sendCommandInfo(sender, "     removetrib", "Remove a tribute");
        } 
        
        return true;
    }
        
}