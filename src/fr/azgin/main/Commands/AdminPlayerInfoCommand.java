package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
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

                if(p.hasPermission("mineslash.admininfo")){
                    np.sendCMessage("§7mana: §d"+np.getMANA() + "§7xp: §d" + np.getXP() + " §7level: §d" + np.getLevel());
                    p.sendMessage(np.toString());
                }

                return true;
            }


        }

        return true;
    }
}
