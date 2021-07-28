package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.mythicmobs.MythicMobManager;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class resetClasseCommand implements CommandExecutor {

    MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(!(sender instanceof Player)){
            if(args.length == 1){
                Player p = Bukkit.getPlayer(args[0]);

                MythicMobManager mm = new MythicMobManager();

                ItemStack m = mm.getMythicMobsItems("book1");



                if (p != null) {
                    Inventory playerInventory = p.getInventory();

                    if(playerInventory.contains(m)){
                        NewPlayer np = mainClass.getPlayer(p);
                        np.setClasse(null);

                        np.sendCMessage("§7Vous venez de reset votre §dclasse");
                        playerInventory.remove(m);
                    }




                }


               }
            return true;
        }

        return false;
    }
}
