/*
 * This program was made by DrAzgin, you can't use this without my consent. This project is private and you can't sell it, use it for ourself or anything else.
 *
 * This program is licensed.
 * Contact: max.benamara@gmail.com
 */

package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.mythicmobs.MythicMobManager;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class CraftCommand implements CommandExecutor {

    MainClass mainClass = MainClass.getInstance();



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            NewPlayer np = mainClass.getPlayer(p);

            if(args.length == 1){
                MemorySection _crafts = (MemorySection) this.mainClass.crafts_list.get("crafts");

                assert _crafts != null;
                MemorySection _craft = (MemorySection) _crafts.get(args[0]);

                if(_craft != null){
                    List<String> _items = _craft.getStringList("items");
                    Inventory _inventory = p.getInventory();
                    boolean _needitem = false;
                    MythicMobManager mmm = new MythicMobManager();

                    for(String item : _items){

                        String[] _split = item.split(" ");
                        ItemStack _item = mmm.getMythicMobsItems(_split[0]);
                        _item.setAmount(Integer.parseInt(_split[1]));

                        if(!_inventory.contains(_item)){
                            _needitem = true;
                            break;
                        }

                    }

                    if(_needitem){
                        np.sendCMessage("§7Il vous manque des items pour le craft.");
                    } else {
                        ItemStack _item = mmm.getMythicMobsItems(_craft.getString("item_id"));

                        if(_item != null){
                            np.sendCMessage("§7Vous venez de craftez l'item");
                            _inventory.addItem(_item);

                            for(String item : _items){

                                String[] _split = item.split(" ");
                                ItemStack _idtem = mmm.getMythicMobsItems(_split[0]);
                                _idtem.setAmount(Integer.parseInt(_split[1]));

                                _inventory.remove(_idtem);

                            }
                        } else {
                            np.sendCMessage("§7Une erreur c'est produite, merci de contacter un staff avec la commande §d/bug <explications>");
                        }
                    }
                } else {
                    np.sendCMessage("§7Le craft n'existe pas.");
                }
            }



   }




        return true;
    }
}
