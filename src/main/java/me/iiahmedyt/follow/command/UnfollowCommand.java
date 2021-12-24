package me.iiahmedyt.follow.command;

import me.iiahmedyt.follow.StrikeFollow;
import me.iiahmedyt.follow.api.events.UnfollowEvent;
import me.iiahmedyt.follow.util.CC;
import me.iiahmedyt.follow.util.FollowPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnfollowCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!sender.hasPermission("StrikeFollow.use")){
            sender.sendMessage(CC.trans("&cYou dont have permissions to run this command"));
            return true;
        }
        if(args.length == 0){
            Player player = ((Player) sender).getPlayer();
            StrikeFollow instance = StrikeFollow.getInstance();
            FollowPlayer followPlayer = instance.getFollowPlayer(player);
            if(!followPlayer.isFollowing()){
                player.sendMessage(CC.trans(CC.getMessage("Unfollow.Fail"), player));
                return true;
            }
            UnfollowEvent event = new UnfollowEvent(player, followPlayer.getFollowing().getPlayer());
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()) return true;
            player.sendMessage(CC.trans(CC.getMessage("Unfollow.success"), player)
                    .replace("<unfollowed>", followPlayer.getFollowing().getPlayer().getName()));
            followPlayer.unfollow();
        }
        return true;
    }

}