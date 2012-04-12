package com.acuddlyheadcrab.MCHungerGames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class HGListener implements Listener {
    public static HungerGames plugin;
    public HGListener(HungerGames instance) {plugin = instance;}
    
    public static FileConfiguration config;
    
    
//    this works
    @EventHandler
    public void playercaht(PlayerChatEvent e){
        System.out.println(e.getPlayer().getName()+": "+e.getMessage());
    }
    
    
////    done
//  @EventHandler(priority = EventPriority.HIGH)
//  public void onPlayerChat(PlayerChatEvent event) {
//      if(event.getMessage().equalsIgnoreCase("show me arenas")){
//          event.getPlayer().sendMessage(""+arenalist);
//      }
////        TODO: work on later once areans are fixed
////        Player player = event.getPlayer();
////        
////        boolean ingame = Util.isInGame(player);
////        boolean nearbychat = config.getBoolean("Options.During_games.nearbyTributeChat");
////        
////        List<Player> p_list = Arrays.asList(Bukkit.getOnlinePlayers());
////        
////        if(ingame){
////            if(nearbychat){
////                
////            }
////        } else {
////            p_list.removeAll(Arena.getAllTributes());
////            for(Player players : p_list){
////                players.sendMessage(event.getMessage());
////            }
////        }
//  }
//  
//  
////    TODO: dont let them move out of arena, while in countdown
//  @EventHandler
//  public void onPlayerMove(PlayerMoveEvent event) {
//      
//      Player player = event.getPlayer();
//      Location
//          to_loc = event.getTo(),
//          from_loc = event.getFrom()
//      ;
//      
//      boolean
//          to = Util.isWithinArena(to_loc),
//          from = Util.isWithinArena(from_loc)
//      ;
//      
//      
//      if(to){
//          Arena arena = Util.getNearbyArena(from_loc);
//          if(arena.isInGame()){
//              player.sendMessage("Noooo you're not supposed to -okay...");
//          } else {
//              player.sendMessage(ChatColor.LIGHT_PURPLE+"You are now entering "+arena.getName());
//          }
//      }
//      
//      if(from){
//          Arena arena = Util.getNearbyArena(from_loc);
//          if(arena.isInGame()){
//              player.sendMessage("Noooo you're not supposed to -okay...");
//          } else {
//              player.sendMessage(ChatColor.LIGHT_PURPLE+"You are now leaving "+arena.getName());
//          }
//      }
//      
//      
//  }
//  
////    done
//  @EventHandler
//  public void onPlayerTP(PlayerTeleportEvent event) {
//      Player player = event.getPlayer();
//      Arena arena = Util.getAssignedArena(player);
//      boolean ingame = Util.isInGame(player);
//      boolean notp = config.getBoolean("Options.During_games.noTeleporting");
//      
//      if (ingame && notp){
//          event.setCancelled(true);
//          PluginIO.sendPluginInfo(event.getPlayer()+"'s teleport was cancelled (Player is a tribute for "+arena.getName()+")");
//      }
//  }
//
//  
//  @EventHandler
//  public void onPlayerDeath(PlayerDeathEvent event){
//      Player player = event.getEntity();
//      Arena arena = Util.getAssignedArena(player);
//      World world = player.getWorld();
//      
////        TODO: FINISH
//      int dur = world.getThunderDuration();
//      world.setThunderDuration(20);
//      world.strikeLightningEffect(player.getLocation());
//      world.setThunderDuration(dur);
//      
//      if(Util.isInGame(player));
//          for(Player players : arena.getOnlineTributes()){
//              Location loc = players.getLocation();
//              loc.getWorld().createExplosion(loc.getBlock().getRelative(BlockFace.UP, 4).getLocation(), 0);
//          }
//  }
    
//  TODO: add disconnect thing
    
}