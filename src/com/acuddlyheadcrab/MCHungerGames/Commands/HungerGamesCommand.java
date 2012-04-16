package com.acuddlyheadcrab.MCHungerGames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.acuddlyheadcrab.MCHungerGames.HungerGames;
import com.acuddlyheadcrab.util.ConfigKeys;
import com.acuddlyheadcrab.util.Perms;
import com.acuddlyheadcrab.util.PluginInfo;
import com.acuddlyheadcrab.util.Utility;





public class HungerGamesCommand implements CommandExecutor{
    
    private static HungerGames hungergames;
    public HungerGamesCommand(HungerGames instance){hungergames = instance;}
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
        FileConfiguration config = hungergames.getConfig();
        
        if(sender instanceof Player) PluginInfo.sendPluginInfo(sender.getName()+": /"+label+Utility.concatArray(args, " "));
        
        if(cmd.getName().equalsIgnoreCase("hungergames")){
            try{
                String arg1 = args[0];
                if(arg1.equalsIgnoreCase("reload")){
                    if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted /hg reload command");
                    if(sender.hasPermission(Perms.HG_RELOAD.perm())){
                        hungergames.saveConfig();
                        PluginInfo.sendPluginInfo("reloaded by "+sender.getName());
                        sender.sendMessage(ChatColor.GREEN+"Reloaded MCHungerGames");
                    } else PluginInfo.sendNoPermMsg(sender);
                }
            }catch(IndexOutOfBoundsException e){
                if(config.getBoolean(ConfigKeys.OPTS_DEBUG_ONCMD.key())) PluginInfo.sendPluginInfo("Attempted to show main cmd tree help");
                PluginInfo.sendCommandsHelp(sender);
            }
        }
        
        return true;
    }
        
}
