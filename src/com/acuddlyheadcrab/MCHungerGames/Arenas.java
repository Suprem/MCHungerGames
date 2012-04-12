package com.acuddlyheadcrab.MCHungerGames;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.acuddlyheadcrab.util.ConfigKeys;
import com.acuddlyheadcrab.util.Utility;

public class Arenas {
    
	private static FileConfiguration config;
    public static HungerGames hungergames;
    public Arenas(HungerGames instance){
    	hungergames = instance;
	}
    
    public static void submitNewArena(String name, Location center, double maxdist, List<String> gms, List<String> tribs, boolean ingame){
    	String arenapath = ConfigKeys.ARENAS+name;
    	config.set(arenapath, null);
    	config.set(getPathType(name, "center.World"), center.getWorld().getName());
    	config.set(getPathType(name, "center.x"), center.getX());
    	config.set(getPathType(name, "center.y"), center.getY());
    	config.set(getPathType(name, "center.z"), center.getZ());
    	config.set(getPathType(name, "maxdist"), maxdist);
    	config.set(getPathType(name, "gms"), gms);
    	config.set(getPathType(name, "tribs"), tribs);
    	config.set(getPathType(name, "ingame"), ingame);
    	hungergames.saveConfig();
    }
    
    public static void initConfig(){
        config = hungergames.getConfig();
    }
    
    public static void deleteArena(String arenakey){
    	configSet(getPathType(arenakey, "self"), null);
    }
    
    public static Location getCenter(String arenakey){
        String worldstring = config.getString(getPathType(arenakey, "center.world"));
        World world = Bukkit.getWorld(worldstring);
        if(world==null) throw new NullPointerException("Could not find world \""+worldstring+"\"");
        double
            x = config.getDouble(getPathType(arenakey, "center.x")),
            y = config.getDouble(getPathType(arenakey, "center.y")),
            z = config.getDouble(getPathType(arenakey, "center.z"))
        ;
        Location center = new Location(world, x, y, z);
        return center;
    }
    
    public static double getMaxDist(String arenakey){
        return config.getDouble(getPathType(arenakey, "maxdist"));
    }
    
    public static List<String> getGMs(String arenakey){
        return config.getStringList(getPathType(arenakey, "gms"));
    }
    
    public static List<String> getTribs(String arenakey){
        return config.getStringList(getPathType(arenakey, "tribs"));
    }
    
    public static boolean isInGame(String arenakey){
        return config.getBoolean(getPathType(arenakey,"ingame"));
    }
    
    public static void setCenter(String arenakey, Location loc){
        configSet(getPathType(arenakey, "center.world"), loc.getWorld().getName());
        configSet(getPathType(arenakey, "center.x"), loc.getX());
        configSet(getPathType(arenakey, "center.y"), loc.getY());
        configSet(getPathType(arenakey, "center.z"), loc.getZ());
    }
    
    public static void setMaxDist(String arenakey, double maxdist){
        configSet(getPathType(arenakey, "maxdist"), maxdist);
    }
    
    public static void setGMs(String arenakey, List<String> gms){
        configSet(getPathType(arenakey, "gms"), gms);
    }
    
    public static void addGM(String arenakey, String player){
    	List<String> gms = getGMs(arenakey);
    	gms.add(player);
    	setGMs(arenakey, gms);
    }
    
    public static void removeGM(String arenakey, String player){
    	List<String> gms = getGMs(arenakey);
    	gms.remove(player);
    	setGMs(arenakey, gms);
    }
    
    public static void setTribs(String arenakey, List<String> tribs){
        configSet(getPathType(arenakey, "tribs"), tribs);
    }
    
    public static void addTrib(String arenakey, String player){
    	List<String> tribs = getTribs(arenakey);
    	tribs.add(player);
    	setTribs(arenakey, tribs);
    }
    
    public static void removeTrib(String arenakey, String player){
    	List<String> tribs = getTribs(arenakey);
    	tribs.remove(player);
    	setTribs(arenakey, tribs);
    }
    
    public static void setInGame(String arenakey, boolean ingame){
        configSet(getPathType(arenakey, "ingame"), ingame);
    }
    
    
    public static void configSet(String path, Object object){
        config.set(path, object);
        hungergames.saveConfig();
    }
    
    public static String getPathType(String arenakey, String type){
        String arenapath = ConfigKeys.ARENAS.key()+arenakey;
        if(type.equalsIgnoreCase("center.world")) return arenapath+ConfigKeys.ARN_CENTER_WRLD.key();
        if(type.equalsIgnoreCase("center.x")) return arenapath+ConfigKeys.ARN_CENTER_X.key();
        if(type.equalsIgnoreCase("center.y")) return arenapath+ConfigKeys.ARN_CENTER_Y.key();
        if(type.equalsIgnoreCase("center.z")) return arenapath+ConfigKeys.ARN_CENTER_Z.key();
        if(type.equalsIgnoreCase("maxdist"))return arenapath+ConfigKeys.ARN_MAXDIST.key();
        if(type.equalsIgnoreCase("gms"))return arenapath+ConfigKeys.ARN_GMS.key();
        if(type.equalsIgnoreCase("tribs"))return arenapath+ConfigKeys.ARN_TRIBS.key();
        if(type.equalsIgnoreCase("ingame"))return arenapath+ConfigKeys.ARN_INGAME.key();
        if(type.equalsIgnoreCase("self")) return arenapath;
        return null;
    }
    
    public static boolean isWithinArena(String arenakey, Location loc){
        if(loc.distance(getCenter(arenakey))<=getMaxDist(arenakey)) return true; 
        else return false;
    }
    
    public static String getNearbyArena(Location loc){
        for(String arenakey : Utility.getArenasKeys())
            if(isWithinArena(arenakey, loc)) return arenakey;
        return null;
    }
    
    public static boolean isTribute(String arenakey, Player player){
        for(String trib : getTribs(arenakey))
            if(Bukkit.getPlayer(trib)==null&&player.equals(Bukkit.getPlayer(trib))) return true;
        return false;
    }
    
    public static String getArenaByTrib(Player player){
        for(String arenakey : Utility.getArenasKeys())
            if(isTribute(arenakey, player)) return arenakey;
        return null;
    }
    
    public static boolean isGM(String arenakey, Player player){
        for(String gm : getGMs(arenakey))
            if(Bukkit.getPlayer(gm)==null&&player.equals(Bukkit.getPlayer(gm))) return true;
        return false;
    }
    
    public static String getArenaByGM(Player player){
        for(String arenakey : Utility.getArenasKeys())
            if(isGM(arenakey, player)) return arenakey;
        return null;
    }
}
