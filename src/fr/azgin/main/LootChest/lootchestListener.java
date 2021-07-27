package fr.azgin.main.LootChest;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Objects;

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
                NewPlayer np = this.main.getPlayer(p);
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
                        np.sendCMessage("§7Vous avez avez succès défini ce coffre comme le coffre lootable: §d" + meta.asString());

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
                                    boolean _check = lootChest(loots, p);

                                    if(!_check){
                                        np.sendCMessage("§7Vous n'avez loot aucun objet.");
                                    }

                                    p.setMetadata("mineslash_lootchest_looted_"+chest_loot_id, new FixedMetadataValue(main, true));

                                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                                        p.removeMetadata("mineslash_lootchest_looted_"+chest_loot_id, main);
                                        np.sendCMessage("§7Vous pouvez reloot le coffre: §d" + chest_name);
                                    }, (20L * countdown));


                            } else {
                                np.sendCMessage("§7Vous devez attendre: §d" + countdown + "s");
                            }
                            /*
                            if global chest
                             */
                        } else if (type == 1){
                             if(state.hasMetadata("lootchest_global_countdown")){
                                List<MetadataValue> _countdown = state.getMetadata("lootchest_global_countdown");
                                MetadataValue _meta = _countdown.get(_countdown.size() - 1);



                                if(_meta.asInt() == 0){
                                    boolean _check = lootChest(loots, p);

                                    if(!_check){
                                        np.sendCMessage("§7Vous n'avez loot aucun objet.");
                                    }

                                    state.setMetadata("lootchest_global_countdown", new FixedMetadataValue(main, 1));
                                    state.update();

                                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                                        state.setMetadata("lootchest_global_countdown", new FixedMetadataValue(main, 0));
                                        Bukkit.broadcastMessage("§7Le coffre global: §d" + chest_name + " §7et de nouveau disponible.");
                                        state.update();
                                    }, (20L * countdown));


                                    np.sendCMessage("§7Vous venez de loot le coffre globale: §d" + chest_name);
                                } else {
                                    np.sendCMessage("§7Ce coffre à déjà était loot.");
                                }
                            } else {
                                 state.setMetadata("lootchest_global_countdown", new FixedMetadataValue(main, 0));
                                 state.update();
                             }
                        } else if(type == 2) {
                            if(!p.hasMetadata("mineslash_lootchest_looted_"+chest_loot_id)){

                                Inventory inv = p.getInventory();
                                MythicMobManager mmm = new MythicMobManager();
                                String needed = chest_loot.getString("needed");

                                if (needed != null) {
                                    String[] needed_splited = needed.split(" ");

                                    if(needed_splited.length == 2){

                                        ItemStack _needed = mmm.getMythicMobsItems(needed_splited[0]);
                                        _needed.setAmount(Integer.parseInt(needed_splited[1]));

                                        if(inv.contains(_needed)){

                                            inv.remove(_needed);

                                            boolean _check = lootChest(loots, p);

                                            if(!_check){
                                                np.sendCMessage("§7Vous n'avez loot aucun objet.");
                                            }

                                            p.setMetadata("mineslash_lootchest_looted_"+chest_loot_id, new FixedMetadataValue(main, 1));

                                            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                                                p.removeMetadata("mineslash_lootchest_looted_"+chest_loot_id, main);
                                                np.sendCMessage("§7Vous pouvez reloot le coffre: §d" + chest_name);
                                            }, (20L * countdown));
                                        } else {
                                            np.sendCMessage("§7vous devez avoir l'item: " + "x"+needed_splited[1] + " - " + Objects.requireNonNull(_needed.getItemMeta()).getDisplayName());
                                        }

                                    } else {
                                        p.sendMessage("error please contact admin");
                                    }
                                } else {
                                    p.sendMessage("error please contact admin");
                                }


                            } else {
                                np.sendCMessage( "Vous devez attendre, entre chaque utilisation, même avec l'item demander.");
                            }
                        }


                    }

                }

            }

        }

    }

    public boolean lootChest(List<String> loots, Player p){
        MythicMobManager mmm = new MythicMobManager();
        boolean looted = false;
        NewPlayer np = main.getPlayer(p);

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

                    if(!looted){
                        looted = true;
                    }
                    if(item.getItemMeta() != null){
                        np.sendCMessage("§7Vous venez de récupérez l'object: §d" + item.getItemMeta().getDisplayName());
                    }
                }
            }
        }

        return looted;
    }

    public boolean calculateChance(Integer chance){
        if (Math.random() * 100 < chance) {
            return true;
        }

        return false;
    }
}
