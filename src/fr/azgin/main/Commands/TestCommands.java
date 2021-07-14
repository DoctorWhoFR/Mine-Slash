package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommands implements CommandExecutor {


    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof ConsoleCommandSender){

            if(strings.length != 0){
                if(strings[0] != null && strings[1] != null){
                    int quantity = Integer.parseInt(strings[0]);
                    Player p = Bukkit.getPlayer(strings[1]);

                    if(p != null){
                        NewPlayer np = this.main.getPlayer(p);

                        np.addXP(quantity);
                        this.main.logger.info("[XP] added " + quantity + " to player");
                    }


                } else {
                    this.main.logger.warning("error invalid admin console command");
                }
            } else {
                this.main.logger.warning("error invalid admin console command");
            }

        }


        return true;
    }
}
