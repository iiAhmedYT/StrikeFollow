package me.iiahmedyt.follow.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.iiahmedyt.follow.StrikeFollow;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIntegeration extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "StrikeFollow";
    }

    @Override
    public @NotNull String getAuthor() {
        return "iiAhmedYT";
    }

    @Override
    public @NotNull String getVersion() {
        return "2.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String placeholder) {
        FollowPlayer followPlayer = StrikeFollow.getInstance().getFollowPlayer(player);
        if(placeholder.equalsIgnoreCase("isFollowing")){
            return String.valueOf(followPlayer.isFollowing());
        }
        if(placeholder.equalsIgnoreCase("following")){
            if(!followPlayer.isFollowing()) return "NONE";
            String name = followPlayer.getFollowing().getPlayer().getName();
            return name == null? "NONE" : name;
        }
        return null;
    }
}
