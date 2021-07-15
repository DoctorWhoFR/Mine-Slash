package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class resetClasseCommand implements CommandExecutor {

    MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(!(sender instanceof Player)){
            if(args.length == 1){
                Player p = Bukkit.getPlayer(args[0]);
                NewPlayer np = mainClass.getPlayer(p);
                np.setClasse(null);
                if (p != null) {
                    p.sendMessage("Vous venez de reset votre classe");
                }
            }
        }

        return false;
    }
}
