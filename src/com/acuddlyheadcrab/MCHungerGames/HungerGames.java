package com.acuddlyheadcrab.MCHungerGames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.acuddlyheadcrab.MCHungerGames.Commands.CornucopiaCommand;
import com.acuddlyheadcrab.MCHungerGames.Commands.HGArenaCommand;
import com.acuddlyheadcrab.MCHungerGames.Commands.HGArenaEditCommand;
import com.acuddlyheadcrab.MCHungerGames.Commands.HGGameCommand;
import com.acuddlyheadcrab.MCHungerGames.Commands.HungerGamesCommand;
import com.acuddlyheadcrab.util.PluginInfo;
import com.acuddlyheadcrab.util.Utility;

public class HungerGames extends JavaPlugin {
    
    public static HungerGames plugin;
    public static PluginDescriptionFile plugdes;
    public static FileConfiguration config;
    
    public HGListener hglistener = new HGListener(this);
    public PluginInfo pluginIO = new PluginInfo(this);
    public Utility util = new Utility(this);
    public Arenas arenas = new Arenas(this);
    
    @Override
    public void onEnable() {
        loadConfig();
        initCommands();
        Arenas.initConfig();
        plugdes = getDescription();
        System.out.println("PLUGDESC NAME: "+plugdes.getName());
        getServer().getPluginManager().registerEvents(hglistener, this);
    }
    
    @Override
    public void onDisable() {
        PluginInfo.sendPluginInfo("is now disabled");
    }
    
    public void initCommands(){
        getCommand("hungergames").setExecutor(new HungerGamesCommand(this));
        getCommand("hgarena").setExecutor(new HGArenaCommand(this));
        getCommand("hgaedit").setExecutor(new HGArenaEditCommand(this));
        getCommand("hggame").setExecutor(new HGGameCommand(this));
        getCommand("spawnccp").setExecutor(new CornucopiaCommand(this));
    }
    
    public void loadConfig() {
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }
    
    public static JavaPlugin getPlugin(){
        return plugin;
    }
    
    public static FileConfiguration getInstConfig(){
        /** Only safe AFTER plugin is enabled */
        if(config==null){throw new NullPointerException("CONFIG IS NULL!");} else return config;
    }

}
