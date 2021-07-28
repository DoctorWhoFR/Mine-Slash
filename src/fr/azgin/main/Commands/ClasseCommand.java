package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.mythicmobs.MythicMobManager;
import io.lumine.xikage.mythicmobs.adapters.AbstractItemStack;
import io.lumine.xikage.mythicmobs.items.ItemManager;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.Opt;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClasseCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();
    MythicMobManager mmm = new MythicMobManager();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            if(strings.length == 1){

                Player player = (Player) commandSender;
                String _classe = strings[0];

                NewPlayer np = main.getPlayer(player);

                if(np.getClasse().equals("null")){
                    if(MainClass.classes_lists.contains(_classe)){
                        np.setClasse(_classe);
                        np.sendCMessage("§7Vous venez de choisir la classe: §d" + _classe);
                    } else {
                        np.sendCMessage("§7La classe que vous avez choisi n'existe pas !");
                    }

                    return true;
                } else {

                    np.sendCMessage("§7vous avez déjà une §dclasse, §7vous pouvez reset votre classe en parlant a §dSirius le maitre des armes.");

                    return true;
                }
            }
        }
        return false;
    }
}
