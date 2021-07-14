package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClasseCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


        if(strings.length == 2){

            Player player = Bukkit.getPlayer(strings[0]);
            String _classe = strings[1];

            if(player != null){
                NewPlayer np = main.getPlayer(player);

                if(np.getClasse().equals("null")){
                    if(MainClass.classes_lists.contains(_classe)){
                        np.setClasse(_classe);
                        np.get_p().sendMessage("Vous venez de choisir la classe: " + _classe);
                    } else {
                        np.get_p().sendMessage("La classe que vous avez choisi n'existe pas !");
                    }

                    return true;
                } else {
                    np.get_p().sendMessage("vous avez déjà une classe, vous pouvez changez de classe auprès du maitre de stage.");

                    return true;
                }
            }
        }

        return false;
    }
}
