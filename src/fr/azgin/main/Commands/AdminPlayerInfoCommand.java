package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminPlayerInfoCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            NewPlayer np = this.main.getPlayer(p);


            if(strings.length == 1){
                Player t = Bukkit.getPlayer(strings[0]);

                if(t != null){
                    NewPlayer nt = main.getPlayer(t);

                    if(p.hasPermission("mineslash.admininfo")){
                        np.sendCMessage("§7mana: §d"+ nt.getMANA() + "§7xp: §d" + nt.getXP() + " §7level: §d" + nt.getLevel());
                        p.sendMessage(t.getUniqueId().toString());
                    }
                }

                return true;
            }


        }

        return true;
    }
}
