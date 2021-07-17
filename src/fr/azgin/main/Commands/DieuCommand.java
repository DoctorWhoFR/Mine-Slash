package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DieuCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            if(sender instanceof Player){


                Player p = (Player) sender;
                NewPlayer np = main.getPlayer(p);

                if(np.getDieu().equals("null")){
                    String _dieu = args[0];

                    if(MainClass.dieux_lists.contains(_dieu)){
                        np.setDieu(_dieu);
                        np.get_p().sendMessage("Vous venez de choisir " + _dieu + " comme Dieu");
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "buff " + p.getName());
                    }
                } else {
                    np.get_p().sendMessage("Vous avez déjà un Dieu: " + np.getDieu());
                }
            }
        }



        return false;
    }



}
