package me.iiahmedyt.follow.listener;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.events.*;
import me.iiahmedyt.follow.StrikeFollow;
import me.iiahmedyt.follow.util.CC;
import me.iiahmedyt.follow.util.FollowPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ActionListener implements Listener {

    /*Match*/
    @EventHandler
    public void fightEvent(FightStartEvent event){
        StrikeFollow instance = StrikeFollow.getInstance();
        StrikePracticeAPI api = StrikePractice.getAPI();
        event.getFight().getPlayersInFight().forEach(player -> {
            FollowPlayer followPlayer = instance.getFollowPlayer(player);
            followPlayer.getFollowers().forEach(follower -> {
                follower.sendMessage(CC.trans(CC.getMessage("Log.Fight.Start"), follower)
                        .replace("<followed>", player.getName()));
                api.addSpectator(follower, player);
            });
        });
    }

    @EventHandler
    public void fightEndEvent(FightEndEvent event){
        StrikeFollow instance = StrikeFollow.getInstance();
        StrikePracticeAPI api = StrikePractice.getAPI();
        event.getFight().getPlayersInFight().forEach(player -> {
            FollowPlayer followPlayer = instance.getFollowPlayer(player);
            followPlayer.getFollowers().forEach(follower -> {
                follower.sendMessage(CC.trans(CC.getMessage("Log.Fight.End"), follower)
                        .replace("<followed>", player.getName()));
            });
        });
    }

    /*Spectating*/
    @EventHandler
    public void spectateEvent(PlayerStartSpectatingEvent event){
        Player player = event.getPlayer();
        StrikeFollow instance = StrikeFollow.getInstance();
        FollowPlayer followPlayer = instance.getFollowPlayer(player);
        StrikePracticeAPI api = StrikePractice.getAPI();
        followPlayer.getFollowers().forEach(follower -> {
            follower.sendMessage(CC.trans(CC.getMessage("Log.Spectate.Start"), follower)
                    .replace("<followed>", player.getName()));
            if(!event.getFight().getSpectators().contains(follower))
                api.addSpectator(follower, event.getFight().getPlayersInFight().get(0));
        });
    }

    @EventHandler
    public void spectateLeaveEvent(PlayerStopSpectatingEvent event){
        Player player = event.getPlayer();
        StrikeFollow instance = StrikeFollow.getInstance();
        FollowPlayer followPlayer = instance.getFollowPlayer(player);
        StrikePracticeAPI api = StrikePractice.getAPI();
        followPlayer.getFollowers().forEach(follower -> {
            follower.sendMessage(CC.trans(CC.getMessage("Log.Spectate.End"), follower)
                    .replace("<followed>", player.getName()));
            follower.teleport(player);
            if(event.getFight().getSpectators().contains(follower))
                api.removeSpectator(follower, true);
        });
    }

    /*Party*/
    @EventHandler
    public void partyCreate(PartyCreatedEvent event){
        Player player = event.getParty().getOwnerPlayer();
        StrikeFollow instance = StrikeFollow.getInstance();
        FollowPlayer followPlayer = instance.getFollowPlayer(player);
        StrikePracticeAPI api = StrikePractice.getAPI();
        followPlayer.getFollowers().forEach(follower -> {
            follower.sendMessage(CC.trans(CC.getMessage("Log.Party.Create"), follower)
                    .replace("<followed>", player.getName()));
        });
    }

    @EventHandler
    public void partyDisband(PartyDisbandEvent event){
        Player player = event.getParty().getOwnerPlayer();
        StrikeFollow instance = StrikeFollow.getInstance();
        FollowPlayer followPlayer = instance.getFollowPlayer(player);
        StrikePracticeAPI api = StrikePractice.getAPI();
        followPlayer.getFollowers().forEach(follower -> {
            follower.sendMessage(CC.trans(CC.getMessage("Log.Party.Disband"), follower)
                    .replace("<followed>", player.getName()));
        });
    }

    /*Cancelling queues (that event doesnt actually exist yet D:)
    public void queueEvent(PlayerQueueEvent event){
        FollowPlayer player = StrikeFollow.getInstance().getFollowPlayer(event.getPlayer());
        if(player.isFollowing()){
            event.setCancelled(true);
        }
    }
    */
}
