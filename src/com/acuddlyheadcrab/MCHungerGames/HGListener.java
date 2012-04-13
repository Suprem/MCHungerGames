package com.acuddlyheadcrab.MCHungerGames;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class HGListener implements Listener {
    public static HungerGames plugin;
    public HGListener(HungerGames instance) {plugin = instance;}
    
    public static FileConfiguration config;
    
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
                    repelPlayer(player, to);
                    return;
                }
            }
            if(Arenas.isInGame(from_arena)) player.sendMessage(ChatColor.LIGHT_PURPLE+"("+from_arena+" is currently in game)");
            player.sendMessage(yellow+"You are now leaving "+gold+from_arena);
        }
        
        if(entering){
            if(Arenas.isInGame(to_arena)){
                
                if(!Arenas.isGM(to_arena, player)){
                    player.sendMessage(red+"You are not allowed to enter "+to_arena+"!");
                    event.setCancelled(true);
                    player.teleport(from);
                    repelPlayer(player, to);
                    return;
//                    so like.... can I turn the player around and "bounce" them off the forcefeild?
                }
            }
            if(Arenas.isInGame(from_arena)) player.sendMessage(ChatColor.LIGHT_PURPLE+"("+to_arena+" is currently in game)");
            player.sendMessage(yellow+"You are now entering "+gold+to_arena);
        }
    }
    
    public void repelPlayer(Player player, Location repulsive){
        Location ploc = player.getLocation();
        Vector reverse = ploc.getDirection().multiply(-1);
//        bug: if the player is looking away from the boundry, they will be psuhed back into the boundry when they hit it.
        player.setVelocity(reverse);
    }
}