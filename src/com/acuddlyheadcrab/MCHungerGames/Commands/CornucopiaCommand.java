package com.acuddlyheadcrab.MCHungerGames.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.acuddlyheadcrab.MCHungerGames.HungerGames;
import com.acuddlyheadcrab.util.Perms;
import com.acuddlyheadcrab.util.PluginInfo;
import com.acuddlyheadcrab.util.Utility;





public class CornucopiaCommand implements CommandExecutor{
    
    @SuppressWarnings("unused")
    private static HungerGames hungergames;
    public CornucopiaCommand(HungerGames instance){hungergames = instance;}
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
        
        boolean isplayer = sender instanceof Player;
        Player player = isplayer ? (Player) sender : null;
        
        if(isplayer) PluginInfo.sendPluginInfo(sender.getName()+": "+cmd.getName());
        
        if(cmd.getName().equalsIgnoreCase("spawnccp")){
            if(isplayer){
                if(sender.hasPermission(Perms.SPC.perm())){
                    
                    Utility.spawnCCPChest(player.getTargetBlock(null, 10));
                    player.sendMessage(ChatColor.GREEN+"Spawned a chest");
                    
                } else PluginInfo.sendNoPermMsg(sender);
            } else PluginInfo.sendOnlyPlayerMsg(sender);
        }
        
        return true;
    }
        
}