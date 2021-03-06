package com.acuddlyheadcrab.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
<<<<<<< HEAD
import org.bukkit.ChatColor;
=======
>>>>>>> 3a1fcf241cf70fef8f4771a629fb08d57e1b5b37
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

<<<<<<< HEAD
import com.acuddlyheadcrab.MCHungerGames.Arenas;
=======
>>>>>>> 3a1fcf241cf70fef8f4771a629fb08d57e1b5b37
import com.acuddlyheadcrab.MCHungerGames.HungerGames;



public class Utility {
    public static HungerGames HungerGamesPlugin;
    public Utility(HungerGames instance) {HungerGamesPlugin = instance;}
    
    
    public final static Logger log = Logger.getLogger("Minecraft");
    
    public static FileConfiguration config;
    
    // done
    public static Map<String, String> getCommandsAndDescs() {
        Map<String, String> map = new HashMap<String, String>();
        List<List<Object>> l_list = getVandK(HungerGamesPlugin.getDescription().getCommands());
        List<Object> cmds = l_list.get(0);
        List<Object> descs = l_list.get(1);
        for (int c = 0; c < cmds.size(); c++) {
            String cmd = cmds.get(c).toString();
            String desc = " ";
            List<List<Object>> l_list2 = getVandK((Map<?, ?>) descs.get(c));
            List<Object> ks = l_list2.get(0);
            List<Object> vs = l_list2.get(1);
            for (int c2 = 0; c2 < ks.size(); c2++)
                if (ks.get(c2).toString().equalsIgnoreCase("description"))
                    desc = vs.get(c2).toString();
            map.put(cmd, desc);
        }
        return map;
    }
    

    // done
    public static List<List<Object>> getVandK(Map<?, ?> map) {
        List<Object> k_list = new ArrayList<Object>();
        List<Object> v_list = new ArrayList<Object>();
        Iterator<?> k_itr = map.keySet().iterator();
        while (k_itr.hasNext()) {
            Object ob = k_itr.next();
            k_list.add(ob);
        }
        Iterator<?> v_itr = map.values().iterator();
        while (v_itr.hasNext()) {
            Object ob = v_itr.next();
            v_list.add(ob);
        }
        List<List<Object>> obj_list = new ArrayList<List<Object>>();
        obj_list.add(k_list);
        obj_list.add(v_list);
        return obj_list;
    }

    public static List<String> getArenasKeys() {
        config = HungerGames.getInstConfig();
        
        Set<String> str_set = config.getConfigurationSection("Arenas").getKeys(false);
        String[] keyarr = new String[str_set.size()];
        keyarr = str_set.toArray(keyarr);
        
        return Arrays.asList(keyarr);
    }
    
    
    public static String toLocKey(Location loc) {
        double x = loc.getX(), y = loc.getY(), z = loc.getZ();
        String world = loc.getWorld().getName();
        String key = (String.format("%4$s, %1$s, %2$s, %3$s", x, y, z, world));
        return key;
    }
    
    public static Location parseLocKey(String spawnkey) {
        String[] sarr = spawnkey.split(", ");
        World world = Bukkit.getWorlds().get(0);
        try{
            world = Bukkit.getWorld(sarr[0]);
            
            if(world!=null){
                return new Location(world, Double.parseDouble(sarr[1]), Double.parseDouble(sarr[2]), Double.parseDouble(sarr[3]));
            } else PluginInfo.sendPluginInfo("Error parsing location key! Could not find world \""+sarr[0]+"\"");
        }catch(NullPointerException e){
            PluginInfo.sendPluginInfo("Error parsing location key! Please make sure your config makes sense");
        }
        return null;
    }
    
    public static void spawnCCPChest(Block block){
        
        Block above = block.getRelative(BlockFace.UP);
        
        boolean
            empty = above.isEmpty(),
            liquid = above.isLiquid(),
            grass = above.getType()==Material.GRASS,
            brownshroom = above.getType()==Material.BROWN_MUSHROOM,
            redshroom = above.getType()==Material.RED_MUSHROOM,
            rose = above.getType()==Material.RED_ROSE,
            flower= above.getType()==Material.YELLOW_FLOWER,
            vine = above.getType()==Material.VINE,
            transparent =empty||liquid||grass||brownshroom||redshroom||rose||flower||vine 
        ;
        
        if(transparent){
            above.setType(Material.CHEST);
        }
    }

    public static String getArenaByKey(String arg1) {
        for(String arenas : getArenasKeys()){
            if(arg1.equalsIgnoreCase(arenas)) return arenas;
        }
        return null;
    }
    
    public static boolean isGameMakersArena(CommandSender sender, String arena) {
        if(sender instanceof Player){
            String arenakey = getArenaByKey(arena);
            if(arenakey!=null){
                if(config.getStringList(ConfigKeys.ARENAS.key()+arenakey+ConfigKeys.ARN_GMS.key()).contains(sender.getName())) return true;
            }
        }
        return false;
    }


    public static String concatArray(String[] array, String param) {
        return concatList(Arrays.asList(array), param);
    }


    public static String concatList(List<String> list, String param) {
        String returnable = "";
        for(String i : list)
            returnable = returnable.concat(param+i);
        return returnable;
    }
    
    public static void repelPlayer(Player player, Location repulsive, int force){
        Location ploc = player.getLocation();
        Vector 
            direction = ploc.toVector().subtract(repulsive.toVector()),
            reverse = direction.multiply(force)
        ;
//        bug: if the player is looking away from the boundry, they will be pushed back into the boundry when they hit it.
        player.setVelocity(reverse);
    }
    
    public static Location getRandomChunkLocation(Location origin, double distance){
        Random rand = new Random();
        Block block = null;
        for(int c=0;c<20;){
            block = origin.getWorld().getHighestBlockAt(
                origin.getChunk().getBlock(
                    rand.nextInt(16),
                    origin.getBlockY(),
                    rand.nextInt(16)
                ).getLocation()
            );
            if(block.getLocation().distance(origin)==distance) break; else c++;
        }
        return block.getWorld().getHighestBlockAt(block.getLocation()).getLocation();
    }
    
    public static List<Location> getRandomChunkLocs(Location origin, int x){
        List<Location> loclist = new ArrayList<Location>();
        for(int c=0;c<x;c++)
            loclist.add(getRandomChunkLocation(origin, 10));
        return loclist;
    }
    
<<<<<<< HEAD
    public enum ChatProximity{
        SELF,
        GLOBAL,
        CLEAR,
        GARBLED,
        INAUDIBLE;
    }
    
    public static ChatProximity getChatProximity(Player talkingplayer, Player recip){
        String arenakey = Arenas.getArenaByTrib(recip);
        if(talkingplayer==recip) return ChatProximity.SELF;
        if(arenakey!=null){
            if(Arenas.isInGame(arenakey)){
                double distance = recip.getLocation().distance(talkingplayer.getLocation());
                if(distance<=36) return ChatProximity.CLEAR;
                else if(distance<=42) return ChatProximity.GARBLED;
                else return ChatProximity.INAUDIBLE;
            }
        }
        return ChatProximity.GLOBAL;
    }


    public static void sendChatProxMessage(Player recip, Player talkingplayer, String msg) {
        switch (Utility.getChatProximity(talkingplayer, recip)) {
            case CLEAR: recip.sendMessage(msg); break;
            case GLOBAL: recip.sendMessage(msg); break;
            case GARBLED: recip.sendMessage(ChatColor.DARK_GRAY+""+ChatColor.MAGIC+msg); break;
            default: break;
        }
    }
    
}
=======
    public static int canHearMessage(Player talkingplayer, Player recip){
        /**
         * Yeah I know this is weird... Later, I will use an enum like 
         * CLEAR, //0
         * GARBLED, //1
         * NOMSG; //2
         **/
        String arenakey = Arenas.getArenaByTrib(recip);
        if(arenakey!=null){
            if(Arenas.isInGame(arenakey)){
                double distance = recip.getLocation().distance(talkingplayer.getLocation());
                if(distance<=36){
                    return 0;
                } else if(distance<=42){
                    return 1;
                }
            }
        } else return 2;
    }
    
}
>>>>>>> 3a1fcf241cf70fef8f4771a629fb08d57e1b5b37
