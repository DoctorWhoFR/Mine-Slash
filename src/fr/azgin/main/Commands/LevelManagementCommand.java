package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelManagementCommand implements CommandExecutor {

    MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){

            Player p = (Player) commandSender;
            NewPlayer np = this.mainClass.getPlayer(p);

            if(strings.length != 0){
                if(p.isOp()){
                    if(strings[0].equalsIgnoreCase("level")){
                        if(strings[1].equalsIgnoreCase("set") && strings[2] != null){
                            np.setLevel(Integer.parseInt(strings[2]));
                        } else if(strings[1].equalsIgnoreCase("add") && strings[2] != null){
                            np.setLevel(np.getLevel() + Integer.parseInt(strings[2]));
                        }
                    } else if(strings[0].equalsIgnoreCase("xp")){
                        if(strings[1].equalsIgnoreCase("set") && strings[2] != null){
                            np.setXP(Integer.parseInt(strings[2]));
                        } else if(strings[1].equalsIgnoreCase("add") && strings[2] != null){
                            np.addMANA(Integer.parseInt(strings[2]));
                        }
                    }
                }
            } else {
                p.sendMessage(np.getDocument().toString());
            }




            return true;
        }



        return false;
    }

}
