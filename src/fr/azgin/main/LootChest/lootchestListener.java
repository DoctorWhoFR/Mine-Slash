package fr.azgin.main.LootChest;

import fr.azgin.main.MainClass;
import fr.azgin.main.mythicmobs.MythicMobManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class lootchestListener implements Listener {

    MainClass main = MainClass.getInstance();

    @EventHandler
    public void onPlayerInteractWithChest(PlayerInteractEvent event){

        /*
          Check if player right clicked on a block
         */
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();


            assert block != null;
            if(block.getType() == Material.CHEST){
                Player p = event.getPlayer();
                boolean loot_chest_admin = p.hasMetadata("chest_posing_check");
                NamespacedKey mineslash_namekey = new NamespacedKey(main, "mineslash_lootchest");

                TileState state = (TileState) block.getState();
                PersistentDataContainer container = state.getPersistentDataContainer();

                if(loot_chest_admin){
                    event.setCancelled(true);

                    List<MetadataValue> loot_chest_admin_data = p.getMetadata("chest_posing_check");
                    int size = loot_chest_admin_data.size();
                    MetadataValue meta = loot_chest_admin_data.get(size-1);

                    if(block.getType() == Material.CHEST){
                        container.set(mineslash_namekey, PersistentDataType.STRING, meta.asString());
                        p.removeMetadata("chest_posing_check", main);
                        p.sendMessage("Vous avez avez succès défini ce coffre comme le coffre lootable:" + meta.asString());

                        state.update();
                    }
                } else {
                /*
                Now checking if chest is a lootable chest
                 */
                    if(container.has(mineslash_namekey, PersistentDataType.STRING)){

                        event.setCancelled(true);

                        String chest_loot_id = container.get(mineslash_namekey, PersistentDataType.STRING);
                        p.sendMessage(chest_loot_id + " test");

                        assert chest_loot_id != null;
                        MemorySection chests_sections = (MemorySection) main.config.get("chests");

                        assert chests_sections != null;
                        MemorySection chest_loot = (MemorySection) chests_sections. get(chest_loot_id);

                        assert chest_loot != null;
                        boolean already_looted = p.hasMetadata("mineslash_lootchest_looted");

                        int countdown = chest_loot.getInt("countdown");
                        String chest_name = chest_loot.getString("name");
                        List<String> loots = chest_loot.getStringList("loots");

                        if(!already_looted){


                            MythicMobManager mmm = new MythicMobManager();

                            for(String loot : loots){
                                ItemStack item = mmm.getMythicMobsItems(loot);

                                if(item != null){
                                    p.getInventory().addItem(item);
                                }
                            }

                            p.setMetadata("mineslash_lootchest_looted", new FixedMetadataValue(main, true));

                            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                                p.removeMetadata("mineslash_lootchest_looted", main);
                                p.sendMessage("Vous pouvez reloot le coffre: " + chest_name);
                            }, (20L * countdown));

                        } else {
                            p.sendMessage("Vous devez attendre: " + countdown);
                        }


                    }

                }

            }

        }

    }
    
}
