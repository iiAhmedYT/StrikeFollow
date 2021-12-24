package me.iiahmedyt.follow.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.iiahmedyt.follow.StrikeFollow;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CC {

    public static String trans(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String trans(String string, Player player){
        boolean papi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        if(papi) return PlaceholderAPI
                .setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', string));
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String getMessage(String path){
        return StrikeFollow.getInstance().getMessages().getString("prefix")
                + StrikeFollow.getInstance().getMessages().getString(path);
    }

}
