package me.iiahmedyt.follow.api;

import me.iiahmedyt.follow.StrikeFollow;
import me.iiahmedyt.follow.util.FollowPlayer;
import org.bukkit.entity.Player;

public class StrikeFollowAPI {

    final StrikeFollow instance;

    public StrikeFollowAPI(StrikeFollow instance){
        this.instance = instance;
    }

    public FollowPlayer getFollowPlayer(Player player){
        return instance.getFollowPlayer(player);
    }

}
