package me.iiahmedyt.follow.util;

import lombok.Getter;
import me.iiahmedyt.follow.StrikeFollow;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Getter
public class FollowPlayer {

    final Player player;
    final ArrayList<Player> followers;
    FollowPlayer following;

    public FollowPlayer(Player player){
        this.player = player;
        followers = new ArrayList<>();
    }

    public boolean isFollowing(){
        return following != null;
    }

    public boolean follow(FollowPlayer player){
        if(following.equals(player)) return false;
        following = player;
        following.addFollower(this.player);
        StrikeFollow.getInstance().followStart(this);
        return true;
    }

    public void unfollow(){
        if(!isFollowing()) return;
        following.removeFollower(player);
        following = null;
    }

    public void addFollower(Player follower){
        followers.add(follower);
    }
    public void removeFollower(Player player){
        followers.remove(player);
    }

}
