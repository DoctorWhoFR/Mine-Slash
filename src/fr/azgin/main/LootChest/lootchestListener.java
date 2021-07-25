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
import java.util.Random;

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
                        state.setMetadata("lootchest_global_countdown", new FixedMetadataValue(main, 0));
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

                        assert chest_loot_id != null;
                        MemorySection chests_sections = (MemorySection) main.config.get("chests");

                        assert chests_sections != null;
                        MemorySection chest_loot = (MemorySection) chests_sections. get(chest_loot_id);

                        assert chest_loot != null;

                        int countdown = chest_loot.getInt("countdown");
                        String chest_name = chest_loot.getString("name");
                        List<String> loots = chest_loot.getStringList("loots");
                        int type = chest_loot.getInt("type");

                        /*
                        IF PER PLAYER CHEST
                         */
                        if(type == 0){
                            boolean already_looted = p.hasMetadata("mineslash_lootchest_looted_"+chest_loot_id);

                            if(!already_looted){

                                /*
                                * Per player chest
                                 */
                                    lootChest(loots, p);

                                    p.setMetadata("mineslash_lootchest_looted_"+chest_loot_id, new FixedMetadataValue(main, true));

                                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                                        p.removeMetadata("mineslash_lootchest_looted_"+chest_loot_id, main);
                                        p.sendMessage("Vous pouvez reloot le coffre: " + chest_name);
                                    }, (20L * countdown));


                            } else {
                                p.sendMessage("Vous devez attendre: " + countdown);
                            }
                            /*
                            if global chest
                             */
                        } else if (type == 1){
                             if(state.hasMetadata("lootchest_global_countdown")){
                                List<MetadataValue> _countdown = state.getMetadata("lootchest_global_countdown");
                                MetadataValue _meta = _countdown.get(_countdown.size() - 1);



                                if(_meta.asInt() == 0){
                                    lootChest(loots, p);
                                    state.setMetadata("lootchest_global_countdown", new FixedMetadataValue(main, 1));
                                    state.update();

                                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                                        state.setMetadata("lootchest_global_countdown", new FixedMetadataValue(main, 0));
                                        Bukkit.broadcastMessage("Vous pouvez reloot le coffre: " + chest_name);
                                        state.update();
                                    }, (20L * countdown));


                                    p.sendMessage("Vous venez de loot le coffre globale: " + chest_name);
                                } else {
                                    p.sendMessage("ce coffre à déjà était loot.");
                                }
                            }
                        }


                    }

                }

            }

        }

    }

    public void lootChest(List<String> loots, Player p){
        MythicMobManager mmm = new MythicMobManager();

        for(String loot : loots){
            String[] splited = loot.split(" ");
            String name = splited[0];
            String chance = splited[1];
            String amount = splited[2];
            boolean chance_check = this.calculateChance(Integer.parseInt(chance));
            ItemStack item = mmm.getMythicMobsItems(name);

            if(item != null){
                item.setAmount(Integer.parseInt(amount));
                if(chance_check){
                    p.getInventory().addItem(item);

                    if(item.getItemMeta() != null){
                        p.sendMessage("Vous venez de récupérez l'object: " + item.getItemMeta().getDisplayName());
                    }
                }
            }
        }
    }

    public boolean calculateChance(Integer chance){
        if (Math.random() * 100 < chance) {
            return true;
        }

        return false;
    }
}
