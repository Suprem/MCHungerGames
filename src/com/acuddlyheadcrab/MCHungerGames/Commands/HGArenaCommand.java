package com.acuddlyheadcrab.MCHungerGames.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

public class HGArenaCommand implements CommandExecutor{
    
	private static HungerGames hungergames;
    public HGArenaCommand(HungerGames instance){hungergames = instance;}
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args){
        
        FileConfiguration config = hungergames.getConfig();
        
        boolean isplayer = sender instanceof Player;
        Player player = isplayer ? (Player) sender : null;
        
        if(isplayer) PluginInfo.sendPluginInfo(sender.getName()+": /"+label+Utility.concatArray(args, " "));
        
        if(cmd.getName().equalsIgnoreCase("hgarena")){
            try{
                String arg1 = args[0];
                
                boolean
                    hga_info = arg1.equalsIgnoreCase("info"),
                    hga_new = arg1.equalsIgnoreCase("new"),
                    hga_del = arg1.equalsIgnoreCase("del"),
                    hga_list = arg1.equalsIgnoreCase("list"),
                    hga_tp = arg1.equalsIgnoreCase("tp"),
                    hga_tpall = arg1.equalsIgnoreCase("tpall")||arg1.equalsIgnoreCase("tpa"),
                    hga_rename = arg1.equalsIgnoreCase("rename")
                ;
                
                if(hga_info){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hga info command");
                    if(sender.hasPermission(Perms.HGA_INFO.perm())){
                        try{
                            String arg2 = args[1];
                            
                            String 
                                arenakey = Utility.getArenaByKey(arg2),
                                arenapath = ConfigKeys.ARENAS.key()+arenakey
                            ;
                            
                            if(arenakey!=null){
                                Location corn = null;
                                try{
                                    corn = Arenas.getCenter(arenakey);
                                }catch(NullPointerException e){
                                    System.out.println("Message: "+e.getMessage());
                                    sender.sendMessage("Could not find world!");
                                }
                                
                                if(corn==null){
                                    PluginInfo.wrongFormatMsg(sender, "There was an error parsing the location key!");
                                    return true;
                                }
                                
                                double
                                    limit = config.getDouble(arenapath+ConfigKeys.ARN_MAXDIST.key()),
                                    x = corn.getBlockX(),
                                    y = corn.getBlockY(),
                                    z = corn.getBlockZ()
                                ;
                                
                                boolean arena_ingame = Arenas.isInGame(arenakey);
                                
                                ChatColor
                                    main = arena_ingame ? ChatColor.GREEN : ChatColor.BLUE,
                                    underline = ChatColor.UNDERLINE,
                                    onlineplayer = ChatColor.DARK_GREEN,
                                    offlineplayer = ChatColor.DARK_GRAY,
                                    offlineformat = ChatColor.STRIKETHROUGH,
                                    reset = ChatColor.RESET
                                ;
                                
                                String
                                    ingame = arena_ingame ? ChatColor.LIGHT_PURPLE+""+ChatColor.ITALIC+"IN GAME" : ChatColor.DARK_GRAY+""+ChatColor.ITALIC+"NOT IN GAME",
                                    gms = "",
                                    tribs = "",
                                    value = ChatColor.RESET+""+ChatColor.GRAY
                                ;
                                
                                for(String gm : Arenas.getGMs(arenakey)){
                                    String status = ChatColor.GRAY+"";
                                    Player gm_player = Bukkit.getPlayerExact(gm);
                                    status = gm_player==null ? offlineplayer+""+offlineformat : reset+""+onlineplayer;
                                    gms = gms.concat(status+gm+" ");
                                }
                                
                                for(String trib : Arenas.getTribs(arenakey)){
                                    String status = ChatColor.GRAY+"";
                                    Player trib_player = Bukkit.getPlayerExact(trib);
                                    status = trib_player==null ? offlineplayer+""+offlineformat : reset+""+onlineplayer;
                                    tribs = tribs.concat(status+trib+" ");
                                }
                                
                                sender.sendMessage(main+""+"Arena name: "+value+arenakey+"        ".concat(ingame));
                                sender.sendMessage(main+"    Cornucopia: "+value+x+", "+y+", "+z+"    ");
                                sender.sendMessage(main+"    World: "+value+corn.getWorld().getName());
                                sender.sendMessage(main+"    MaxDistance: "+value+limit);
                                if(isplayer){
                                    int dist = (int) player.getLocation().distance(corn);
                                    player.sendMessage(main+"    Current distance from center: "+value+dist);
                                }
                                sender.sendMessage(main+"    Gamemakers:"+ChatColor.RESET+ChatColor.ITALIC+onlineplayer+"    online"+offlineplayer+"    offline");
                                sender.sendMessage(gms);
                                sender.sendMessage(main+"    "+underline+"Tributes:");
                                sender.sendMessage(tribs);
                                return true;
                            } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+arg2+"\"");
                        }catch(IndexOutOfBoundsException e){
                            PluginInfo.wrongFormatMsg(sender, "/hga info <arena>"); return true;
                        }
                    } else PluginInfo.sendNoPermMsg(sender); return true;
                } 
                
                if(hga_new){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hga new command");
                    if(sender.hasPermission(Perms.HGA_NEW.perm())){
                        if(isplayer){
                            try{
                                String name = args[1];
                                double limit = Double.parseDouble(args[2]);
                                
                                if(Utility.getArenasKeys().contains(name)){
                                    PluginInfo.wrongFormatMsg(sender, "Arena \""+name+"\" has already been set!"); return true;
                                }
                                
                                Arenas.submitNewArena(name, player.getLocation(), limit, null, null, false);
                                sender.sendMessage(ChatColor.GREEN+"Sucessfully made new arena \""+ChatColor.GOLD+name+ChatColor.GREEN+"\" at your location");
                                return true;
                            }catch(IndexOutOfBoundsException e){
                                PluginInfo.wrongFormatMsg(sender, "/hga new <name> <maxdistance>");
                            }catch(NumberFormatException e){
                                PluginInfo.wrongFormatMsg(sender, "Could not parse \""+args[2]+"\" as a number");
                            }
                        } else PluginInfo.sendOnlyPlayerMsg(sender); return true;
                    } else PluginInfo.sendNoPermMsg(sender); return true;
                }
                
                if(hga_del){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hga del command");
                    if(sender.hasPermission(Perms.HGA_DEL.perm())){
                        try{
                            String name = args[1];
                            
                            if(Utility.getArenasKeys().contains(name)){
                                Arenas.deleteArena(name);
                                sender.sendMessage(ChatColor.GREEN+"Deleted \""+ChatColor.GOLD+name+ChatColor.GREEN+"\" from the config!");
                                return true;
                            } else PluginInfo.wrongFormatMsg(sender, "Could not find \""+name+"\""); return true;
                        }catch(IndexOutOfBoundsException e){
                            PluginInfo.wrongFormatMsg(sender, "/hga del <arena name>"); return true;
                        }
                    }
                }
                
                if(hga_list){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hga list command");
                    if(sender.hasPermission(Perms.HGA_LIST.perm())){
                        String arenas = "";
                        
                        for(String arena : Utility.getArenasKeys()){
                            arenas = arenas.concat(arena+" ");
                        }
                        
                        sender.sendMessage(ChatColor.AQUA+"Arenas: "+arenas);
                        return true;
                    }
                }
                
                if(hga_tp){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hga tp command");
                    if(isplayer){
                        if(sender.hasPermission(Perms.HGA_TP.perm())){
                            try{
                                String arg2 = args[1];
                                
                                String arenakey = Utility.getArenaByKey(arg2);
                                
                                if(arenakey!=null){
                                    player.teleport(Arenas.getCenter(arenakey));
                                    player.sendMessage("Teleported you to the center of "+arenakey);
                                } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+arg2+"\""); return true;
                            }catch(IndexOutOfBoundsException e){
                                PluginInfo.wrongFormatMsg(sender, "/hga tp <arena>"); return true;
                            }
                        } else PluginInfo.sendNoPermMsg(sender);
                    } else PluginInfo.sendOnlyPlayerMsg(sender);
                }
                
                if(hga_tpall){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hga tp command");
                    if(sender.hasPermission(Perms.HGA_TPALL.perm())){
                        try{
                            String arenakey = Utility.getArenaByKey(args[1]);
                            
                            if(arenakey!=null){
                                Arenas.tpAllOnlineTribs(arenakey);
                                sender.sendMessage("Teleported all online tributes for "+arenakey);
                                return true;
                            } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+args[1]+"\""); return true;
                        }catch(IndexOutOfBoundsException e){
                            PluginInfo.wrongFormatMsg(sender, "/hga tpall <arena name>");
                            return true;
                        }
                    } else PluginInfo.sendNoPermMsg(sender);
                }
                
                if(hga_rename){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hga tp command");
                    if(sender.hasPermission(Perms.HGA_RENAME.perm())){
                        try{
                            String 
                                arenakey = Utility.getArenaByKey(args[1]),
                                renameto = args[2]
                            ;
                            if(arenakey!=null){
                                Arenas.renameArena(arenakey, renameto);
                                sender.sendMessage(ChatColor.GREEN+"Renamed \""+arenakey+"\" to \""+renameto+"\"");
                                return true;
                            } else PluginInfo.wrongFormatMsg(sender, "Could not find the arena \""+args[1]+"\""); return true;
                        }catch(IndexOutOfBoundsException e){
                            PluginInfo.wrongFormatMsg(sender, "/hga rename <arena name> <rename to>");
                            return true;
                        }
                    } else PluginInfo.sendNoPermMsg(sender); return true;
                }
            }catch(IndexOutOfBoundsException e){}
            if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted to show /hga branch help");
                PluginInfo.sendCommandInfo(sender, "/hga", "");
                PluginInfo.sendCommandInfo(sender, "     info", "Gives info on an arena");
                PluginInfo.sendCommandInfo(sender, "     new", "Create a new arena at your location");
                PluginInfo.sendCommandInfo(sender, "     del", "Delete an arena from the config");
                PluginInfo.sendCommandInfo(sender, "     list", "Lists all arenas");
                PluginInfo.sendCommandInfo(sender, "     tp", "Teleport to the cornucopia");
                PluginInfo.sendCommandInfo(sender, "     tpall", "Teleport all tributes to the cornucopia");
                PluginInfo.sendCommandInfo(sender, "     rename", "Rename an arena");
        }
        
        
        return true;
    }
        
}
