package me.iiahmedyt.follow.command;

import me.iiahmedyt.follow.StrikeFollow;
import me.iiahmedyt.follow.api.events.FollowEvent;
import me.iiahmedyt.follow.util.CC;
import me.iiahmedyt.follow.util.FollowPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FollowCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!sender.hasPermission("StrikeFollow.use")){
            sender.sendMessage(CC.trans("&cYou dont have permissions to run this command"));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(CC.trans(CC.getMessage("Follow.Usage")));
            return true;
        }
        String name = args[0];
        Player follower = ((Player) sender).getPlayer();
        Player followed = Bukkit.getPlayer(name);
        StrikeFollow instance = StrikeFollow.getInstance();
        FollowPlayer followPlayer = instance.getFollowPlayer(follower);
        if(followed == null){
            follower.sendMessage(CC.trans(CC.getMessage("Follow.Fail.Not-Exist"), follower));
            return true;
        }
        if(followed.equals(follower)){
            follower.sendMessage(CC.trans(CC.getMessage("Follow.Fail.Follow-Self")));
            return true;
        }
        if(followPlayer.isFollowing()){
            follower.sendMessage(replace(CC.trans
                    (CC.getMessage("Follow.Fail.CurrentlyFollowing"),
                            follower), follower, followed));
            return true;
        }
        FollowEvent event = new FollowEvent(follower, followed);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) return true;
        if(followPlayer.follow(instance.getFollowPlayer(followed))){
            follower.sendMessage(CC.trans(replace(CC.getMessage("Follow.Success"),
                    follower, followed), follower));
        } else {
            follower.sendMessage(replace(CC.trans
                            (CC.getMessage("Follow.Fail.CurrentlyFollowing"),
                                    follower), follower, followed));
        }
        return true;
    }

    public String replace(String string, Player follower, Player followed){
        return string.replace("<followed>", followed.getName())
                .replace("<player>", follower.getName());
    }

}
