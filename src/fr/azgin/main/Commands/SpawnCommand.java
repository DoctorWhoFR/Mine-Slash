package fr.azgin.main.Commands;

import de.tr7zw.nbtapi.NBTEntity;
import fr.azgin.main.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            World skycityworld = Bukkit.getWorld("skycitytest");

            if(skycityworld != null){
                Location spawn_skycity = new Location(skycityworld, 347.069,133,-2088.585);

                p.teleport(spawn_skycity);
                p.sendMessage("&6Téléportation au spawn");
            } else {


                p.sendMessage("Il n'y a actuellement pas le spawn sur ce serveur (serveur de dev, event ???)");
            }
        }

        return true;
    }
}
