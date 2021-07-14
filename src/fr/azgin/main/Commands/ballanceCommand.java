package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ballanceCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            OfflinePlayer p = (OfflinePlayer) commandSender;
            if(p.getPlayer() != null){
                p.getPlayer().sendMessage(this.main.getEcon().format(this.main.getEcon().getBalance(p)));
                this.main.getEcon().depositPlayer(p, 1000);
            }
        }

        return false;
    }

}
