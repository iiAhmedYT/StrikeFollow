package me.iiahmedyt.follow.listener;

import me.iiahmedyt.follow.StrikeFollow;
import me.iiahmedyt.follow.util.FollowPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        FollowPlayer followPlayer = new FollowPlayer(player);
        StrikeFollow.getInstance().addFollowPlayer(player, followPlayer);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        StrikeFollow.getInstance().removeFollowPlayer(player);
    }

}
