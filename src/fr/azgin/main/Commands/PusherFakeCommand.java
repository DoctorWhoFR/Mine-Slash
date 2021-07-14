package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.UserEventClassInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PusherFakeCommand implements CommandExecutor {

    MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){

            Player p = (Player) commandSender;

            if(strings.length != 0 && strings[0] != null){

                UserEventClassInfo info = new UserEventClassInfo();
                info.level = Integer.valueOf(strings[0]);
                info.message = "testing";

                mainClass.pusher.trigger("my-channel", "my-event-"+p.getName(), info);

            }



        }



        return true;
    }
}
