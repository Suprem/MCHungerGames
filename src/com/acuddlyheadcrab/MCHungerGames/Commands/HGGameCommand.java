package com.acuddlyheadcrab.MCHungerGames.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.acuddlyheadcrab.MCHungerGames.Arenas;
import com.acuddlyheadcrab.MCHungerGames.HungerGames;
import com.acuddlyheadcrab.util.ConfigKeys;
import com.acuddlyheadcrab.util.Perms;
import com.acuddlyheadcrab.util.PluginInfo;
import com.acuddlyheadcrab.util.Utility;





public class HGGameCommand implements CommandExecutor{
    
    private static HungerGames hungergames;
    public HGGameCommand(HungerGames instance){hungergames = instance;}
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
        
        FileConfiguration config = hungergames.getConfig();
        
        if(cmd.getName().equalsIgnoreCase("hggame")){
            try{
                String arg1 = args[0];
                
                boolean
                    start = arg1.equalsIgnoreCase("start"),
                    stop = arg1.equalsIgnoreCase("stop")
                ;
                
                if(start){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgg start command");
                    try{
                        String arg2 = args[1];
                        
                        String arenakey = Utility.getArenaByKey(arg2);
                        
                        if(arenakey!=null){
                            if(sender.hasPermission(Perms.HGG_START.perm())||Utility.isGameMakersArena(sender, arenakey)){

                                
                                try{
                                    final int arg3 = Integer.parseInt(args[2]);
                                    final ChatColor color = ChatColor.YELLOW;
                                    
                                    Bukkit.broadcastMessage(color+"Hunger Games are starting in the arena "+arenakey+" in "+arg3+" seconds");
                                    
                                    try{
                                        Arenas.setInGame(arenakey, true);
                                        List<String> games = config.getStringList(ConfigKeys.CURRENT_GAMES.key());
                                        games.add(arenakey);
                                        Arenas.configSet(ConfigKeys.CURRENT_GAMES.key(), games);
                                        return true;
                                    }catch(NullPointerException e){
                                        PluginInfo.wrongFormatMsg(sender, arenakey+" does not have enough data to start a game");
                                    }
                                    return true;
                                }catch(IndexOutOfBoundsException e){
                                    PluginInfo.wrongFormatMsg(sender, "/hgg start <arena> [countdown (seconds]");
                                }
                            } else PluginInfo.sendNoPermMsg(sender);
                        } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+arg2+"\"");
                    }catch(IndexOutOfBoundsException e){PluginInfo.wrongFormatMsg(sender, "/hgg start <arena> [countdown (seconds]");}
                }
                
                if(stop){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hgg stop command");
                    try{
                        String arg2 = args[1];
                        
                        String arenakey = Utility.getArenaByKey(arg2);
                        
                        if(arenakey!=null){
                            if(sender.hasPermission(Perms.HGG_STOP.perm())||Utility.isGameMakersArena(sender, arenakey)){
                                
                            	Arenas.setInGame(arenakey, false);
                                List<String> games = config.getStringList(ConfigKeys.CURRENT_GAMES.key());
                                games.remove(arenakey);
                                Arenas.configSet(ConfigKeys.CURRENT_GAMES.key(), games);
                                
                            } else PluginInfo.sendNoPermMsg(sender);
                        } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+arg1+"\"");
                    }catch(IndexOutOfBoundsException e){}
                }
            }catch(IndexOutOfBoundsException e){
                String cur_games = config.getString(ConfigKeys.CURRENT_GAMES.key());
                cur_games = cur_games.trim();
                if(cur_games==""||cur_games=="[]"){
                    cur_games = ChatColor.DARK_GRAY+"(none)";
                }
                PluginInfo.sendCommandInfo(sender, "    /hgg start <arena>", "Starts a game");
                PluginInfo.sendCommandInfo(sender, "    /hgg stop <arena>", "Stops an ongoing game");
                sender.sendMessage(ChatColor.GOLD+"Current Games: "+cur_games);
            }
        }
        
        return true;
    }
        
}