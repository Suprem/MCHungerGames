package com.acuddlyheadcrab.MCHungerGames.commands;

import java.util.List;

import org.bukkit.Bukkit;
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





public class HGGameCommand implements CommandExecutor{
    
    private static HungerGames hungergames;
    public HGGameCommand(HungerGames instance){hungergames = instance;}
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
        
        FileConfiguration config = hungergames.getConfig();
        
        if(sender instanceof Player) PluginInfo.sendPluginInfo(sender.getName()+": /"+label+Utility.concatArray(args, " "));
        
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
                        
                        final String arenakey = Utility.getArenaByKey(arg2);
                        
                        if(arenakey!=null){
                            if(sender.hasPermission(Perms.HGG_START.perm())||Utility.isGameMakersArena(sender, arenakey)){

                                
                                try{
                                    final int countdown = Integer.parseInt(args[2]);
                                    final ChatColor color = ChatColor.YELLOW;
                                    
                                    Bukkit.broadcastMessage(color+"Hunger Games are starting in the arena "+arenakey+" in "+countdown+" seconds");
                                    
                                    try{
                                        Arenas.startGame(arenakey, countdown);
                                        return true;
//                                        the following probably wont happen (right now)
                                    }catch(NullPointerException e){
                                        PluginInfo.wrongFormatMsg(sender, arenakey+" does not have enough data to start a game");
                                        e.printStackTrace();
                                        return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    PluginInfo.wrongFormatMsg(sender, "/hgg start <arena> [countdown (seconds]"); return true;
                                }
                            } else PluginInfo.sendNoPermMsg(sender);
                        } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+arg2+"\"");
                    }catch(IndexOutOfBoundsException e){PluginInfo.wrongFormatMsg(sender, "/hgg start <arena> [countdown (seconds]");}
                    return true;
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
                                sender.sendMessage(ChatColor.LIGHT_PURPLE+"Force stopped game in "+arenakey);
                            } else PluginInfo.sendNoPermMsg(sender);
                        } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+arg2+"\"");
                    }catch(IndexOutOfBoundsException e){PluginInfo.wrongFormatMsg(sender, "/hgg stop <arena>");}
                    return true;
                }
                
                PluginInfo.sendCommandInfo(sender, "/hgg", "");
                PluginInfo.sendCommandInfo(sender, "    start <arena>", "Starts a game");
                PluginInfo.sendCommandInfo(sender, "    stop <arena>", "Stops an ongoing game");
                
            }catch(IndexOutOfBoundsException e){
                String cur_games = config.getString(ConfigKeys.CURRENT_GAMES.key());
                cur_games = cur_games.trim();
                if(cur_games==""||cur_games=="[]"){
                    cur_games = ChatColor.DARK_GRAY+"(none)";
                }
                sender.sendMessage(ChatColor.GOLD+"Current Games: "+cur_games);
            }
        }
        
        return true;
    }
        
}