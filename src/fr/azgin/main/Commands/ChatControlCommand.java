package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ChatControlCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(strings.length == 1){
            Player p = (Player) commandSender;
            if(p.hasPermission("mineslash_clearall")){
                if(strings[0].equalsIgnoreCase("clear")){
                    Collection<? extends Player> playerCollection = this.main.getServer().getOnlinePlayers();

                    for(Player pt : playerCollection){
                        for (int i = 0; i < 30; i++) {
                            pt.sendMessage(ChatColor.WHITE + "");
                        }
                    }


                }

                p.sendMessage(MainClass.prefix + "§7Vous venez de clear le chat générale.");

            }
        }

        return true;
    }

}
