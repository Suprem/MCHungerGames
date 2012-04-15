package com.acuddlyheadcrab.MCHungerGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.acuddlyheadcrab.util.Utility;


public class HungerListener implements Listener {
    public static HungerGames plugin;
    public static EventPriority priority;
    public HungerListener(HungerGames instance) {plugin = instance;}
    
    public static FileConfiguration config;
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        String arena = Arenas.getNearbyArena(player.getLocation());
        if(arena!=null){
            if(Arenas.isInGame(arena)){
                if(Arenas.isTribute(arena, player)){
                    Arenas.removeTrib(arena, player.getName());
                    // replace with broadcast to non-tributes
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+player.getName()+" has died!");
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTP(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        Location 
            to = event.getTo(),
            from = event.getFrom()
        ;
        String
            to_arena = Arenas.getNearbyArena(to),
            from_arena = Arenas.getNearbyArena(from)
        ;
        
        ChatColor
            gold = ChatColor.GOLD,
            yellow = ChatColor.YELLOW,
            red = ChatColor.RED
        ;  
        boolean
            leaving = (from_arena!=null)&&((!from_arena.equals(to_arena))||to_arena==null),
            entering = (to_arena!=null)&&((!to_arena.equals(from_arena))||from_arena==null)
        ;
        
        if(leaving){
            if(Arenas.isInGame(from_arena)){
                if(!Arenas.isGM(from_arena, player)){
                    player.sendMessage(red+"You are not allowed to leave "+from_arena+"!");
                    event.setCancelled(true);
                    return;
                }
                player.sendMessage(ChatColor.LIGHT_PURPLE+"("+to_arena+" is currently in game)");
            }
            player.sendMessage(yellow+"You are now leaving "+gold+from_arena);
        }
        
        if(entering){
            if(Arenas.isInGame(to_arena)){
                if(!Arenas.isGM(to_arena, player)){
                    player.sendMessage(red+"You are not allowed to enter "+to_arena+"!");
                    event.setCancelled(true);
                    return;
                }
                player.sendMessage(ChatColor.LIGHT_PURPLE+"("+to_arena+" is currently in game)");
            }
            player.sendMessage(yellow+"You are now entering "+gold+to_arena);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location 
            to = event.getTo(),
            from = event.getFrom()
        ;
        String
            to_arena = Arenas.getNearbyArena(to),
            from_arena = Arenas.getNearbyArena(from)
        ;
        
        ChatColor
            gold = ChatColor.GOLD,
            yellow = ChatColor.YELLOW,
            red = ChatColor.RED
        ;  
        boolean
            leaving = (from_arena!=null)&&((!from_arena.equals(to_arena))||to_arena==null),
            entering = (to_arena!=null)&&((!to_arena.equals(from_arena))||from_arena==null)
        ;
        
        if(leaving){
            if(Arenas.isInGame(from_arena)){
                if(!Arenas.isGM(from_arena, player)){
                    player.sendMessage(red+"You are not allowed to leave "+from_arena+"!");
                    event.setCancelled(true);
                    player.teleport(from);
                    Utility.repelPlayer(player);
                    return;
                }
                player.sendMessage(ChatColor.LIGHT_PURPLE+"("+to_arena+" is currently in game)");
            }
            player.sendMessage(yellow+"You are now leaving "+gold+from_arena);
        }
        
        if(entering){
            if(Arenas.isInGame(to_arena)){
                if(!Arenas.isGM(to_arena, player)){
                    player.sendMessage(red+"You are not allowed to enter "+to_arena+"!");
                    event.setCancelled(true);
                    player.teleport(from);
                    Utility.repelPlayer(player);
                    return;
                }
                player.sendMessage(ChatColor.LIGHT_PURPLE+"("+to_arena+" is currently in game)");
            }
            player.sendMessage(yellow+"You are now entering "+gold+to_arena);
        }
    }
    
}