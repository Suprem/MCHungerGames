package com.acuddlyheadcrab.MCHungerGames.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerEnterArenaEvent extends PlayerMoveEvent{
    private static final HandlerList handlers = new HandlerList();
    private Location to;
    private Location from;
    private String arenakey;
    private PlayerPassArenaReason reason;
    
    public PlayerEnterArenaEvent(final Player player, final Location from, final Location to, final String  arenakey, final PlayerPassArenaReason reason){
        super(player, from, to);
        this.from = from;
        this.to = to;
        this.arenakey = arenakey;
        this.reason = reason;
    }
    
    @Override
    public Location getFrom(){
        return from;
    }
    
    @Override
    public void setFrom(Location from){
        this.from = from;
    }
    
    public Location getArenaTo(){
        return to;
    }
    
    public void setArenaTo(Location to){
        this.to = to;
    }
    
    public String getArenakey(){
        return arenakey;
    }
    
    public void setArenakey(String arenakey){
        this.arenakey = arenakey;
    }
    
    public PlayerPassArenaReason getReason() {
        return reason;
    }

    public void setReason(PlayerPassArenaReason reason) {
        this.reason = reason;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }

    
}