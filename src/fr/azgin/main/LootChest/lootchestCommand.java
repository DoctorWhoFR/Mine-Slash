package fr.azgin.main.LootChest;

import fr.azgin.main.MainClass;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class lootchestCommand implements CommandExecutor {

    MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(p.hasPermission("mineslash.lootchest.admin")){

                if(args.length == 1){

                    String chest_id = args[0];
                    boolean chest_posing_check = p.hasMetadata("chest_posing_check");

                    if(!chest_posing_check){

                        MemorySection chests_sections = (MemorySection) mainClass.config.get("chests");

                        assert chests_sections != null;
                        MemorySection chest = (MemorySection) chests_sections.get(chest_id);


                        if(chest != null){
                            p.sendMessage("vous pouvez maintenant cliquez sur le coffre pour le définir comme un coffre lootable");
                            p.setMetadata("chest_posing_check", new FixedMetadataValue(mainClass, chest_id));
                        }


                    }
                }


            }

        }

        return false;
    }
}
