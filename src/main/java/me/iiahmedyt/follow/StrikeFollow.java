package me.iiahmedyt.follow;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import lombok.Getter;
import me.iiahmedyt.follow.api.StrikeFollowAPI;
import me.iiahmedyt.follow.command.FollowCommand;
import me.iiahmedyt.follow.command.UnfollowCommand;
import me.iiahmedyt.follow.listener.JoinLeaveListener;
import me.iiahmedyt.follow.listener.ActionListener;
import me.iiahmedyt.follow.util.CC;
import me.iiahmedyt.follow.util.FollowPlayer;
import me.iiahmedyt.follow.util.PAPIntegeration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public final class StrikeFollow extends JavaPlugin {

    @Getter private static StrikeFollow instance;
    @Getter private static StrikeFollowAPI API;
    @Getter FileConfiguration messages;
    private HashMap<Player, FollowPlayer> followPlayers;

    @Override
    public void onEnable() {
        instance = this;
        loadFiles();
        followPlayers = new HashMap<>();
        API = new StrikeFollowAPI(instance);
        Arrays.asList(new JoinLeaveListener(), new ActionListener()).forEach(listener ->
                getServer().getPluginManager().registerEvents(listener, this));
        getServer().getPluginCommand("follow").setExecutor(new FollowCommand());
        getServer().getPluginCommand("unfollow").setExecutor(new UnfollowCommand());
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PAPIntegeration().register();
        }
    }

    private void loadFiles(){
        if(fileNotExist("config.yml")){
            saveResource("config.yml", false);
        }
        if(fileNotExist("messages.yml")){
            saveResource("messages.yml", false);
        }
        reloadConfig();
        messages = YamlConfiguration
                .loadConfiguration(new File(getDataFolder() + File.separator + "messages.yml"));
    }

    private boolean fileNotExist(String name){
        return !new File(getDataFolder() + File.separator + name).exists();
    }

    public void addFollowPlayer(Player player, FollowPlayer followPlayer){
        if(followPlayers.containsKey(player)) return;
        followPlayers.put(player, followPlayer);
    }

    public FollowPlayer getFollowPlayer(Player player) {
        return followPlayers.get(player);
    }

    public void removeFollowPlayer(Player player){
        getFollowPlayer(player).getFollowers().forEach(follower -> {
            follower.sendMessage(CC.trans(CC.getMessage("Unfollow.Auto"), follower)
                    .replace("<followed>", player.getName()));
            getFollowPlayer(player).unfollow();
        });
        followPlayers.remove(player);
    }

    @SuppressWarnings("all")
    public void followStart(FollowPlayer player){
        Player followed = player.getFollowing().getPlayer();
        StrikePracticeAPI api = StrikePractice.getAPI();
        if(api.isInFight(followed)){
            api.addSpectator(player.getPlayer(), followed);
            return;
        }
        if(api.isSpectator(followed)){
            api.addSpectator(player.getPlayer(), api.getSpectating(followed).getPlayersInFight().get(0));
            return;
        }
    }

}
