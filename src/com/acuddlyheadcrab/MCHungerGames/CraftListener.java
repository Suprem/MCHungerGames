package com.acuddlyheadcrab.MCHungerGames;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.acuddlyheadcrab.MCHungerGames.events.*;
import com.acuddlyheadcrab.util.*;

/**
 * @author acuddlyheadcrab
 * 
 * This class is deprecated and unused.
 *
 */

public class CraftListener implements Listener {
    public static HungerGames plugin;
    public static EventPriority priority;
    public CraftListener(HungerGames instance) {plugin = instance;}
    
    public static FileConfiguration config;
    private static boolean eventdebug = false;
    
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
        
        boolean
            leaving = (from_arena!=null)&&((!from_arena.equals(to_arena))||to_arena==null),
            entering = (to_arena!=null)&&((!to_arena.equals(from_arena))||from_arena==null)
        ;
        
        if(leaving){
            if(eventdebug) PluginInfo.sendPluginInfo("Called PlayerLeaveArenaEvent with player "+player.getName());
            Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent(player, from, to, from_arena, PlayerPassArenaReason.PLAYERTP));
        }
        
        if(entering){
            if(eventdebug) PluginInfo.sendPluginInfo("Called PlayerEnterArenaEvent with player "+player.getName());
            Bukkit.getServer().getPluginManager().callEvent(new PlayerEnterArenaEvent(player, from, to, to_arena, PlayerPassArenaReason.PLAYERTP));
        }
        
        if(event.isCancelled()){System.out.println("PlayerMoveEvent was cancelled!");}
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
        
        boolean
            leaving = (from_arena!=null)&&((!from_arena.equals(to_arena))||to_arena==null),
            entering = (to_arena!=null)&&((!to_arena.equals(from_arena))||from_arena==null)
        ;
        
        if(leaving){
            if(eventdebug) PluginInfo.sendPluginInfo("Called PlayerEnterArenaEvent with player "+player.getName());
            Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent(player, from, to, from_arena, PlayerPassArenaReason.PLAYERMOVE));
        }
        
        if(entering){
            if(eventdebug) PluginInfo.sendPluginInfo("Called PlayerEnterArenaEvent with player "+player.getName());
            Bukkit.getServer().getPluginManager().callEvent(new PlayerEnterArenaEvent(player, from, to, to_arena, PlayerPassArenaReason.PLAYERMOVE));
        }
        
        if(event.isCancelled()){System.out.println("PlayerMoveEvent was cancelled!");}
    }
    
}