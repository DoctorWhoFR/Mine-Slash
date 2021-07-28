package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConfigReloadCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("mineslash.reload")){
                main.reloadConfig();
                main.config = main.getConfig();
                p.sendMessage(MainClass.prefix + "§7Les fichiers de config ont était reload.");
            }
            return true;
        }

        return false;
    }
}
