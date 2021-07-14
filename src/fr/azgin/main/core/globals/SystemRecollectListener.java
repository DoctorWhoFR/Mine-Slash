package fr.azgin.main.core.globals;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import fr.azgin.main.MainClass;
import fr.azgin.main.mythicmobs.MythicMobManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SystemRecollectListener implements Listener {

    MainClass mainClass = MainClass.getInstance();

    MythicMobManager mmm = new MythicMobManager();


    /**
     * recolt system
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player p = event.getPlayer();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(p.getLocation()));

        boolean bol = MainClass.getPerms().has(p, "mineslash_admin");

        double point = calculateStuffRecoltePoint(p);

        double final_amount = 1 + point;

        if(p.getWorld().getName().equalsIgnoreCase("lttm") && !bol){

            Block block = event.getBlock();

            // Check if player is not on a no build zone
            if(set.getRegions().isEmpty()){
                if(block.getType() == Material.OAK_WOOD) {
                    mmm.giveMythicMobsItems("RodinBrut", p, final_amount);

                } else if(block.getType() == Material.SPRUCE_WOOD) {
                    mmm.giveMythicMobsItems("RodinBouleau", p, final_amount);

                }  else if(block.getType() == Material.BIRCH_WOOD) {
                    mmm.giveMythicMobsItems("RondinChatain", p, final_amount);

                }  else if(block.getType() == Material.JUNGLE_WOOD) {
                    mmm.giveMythicMobsItems("RodinPin", p, final_amount);

                } else if(block.getType() == Material.ACACIA_WOOD) {
                    mmm.giveMythicMobsItems("RodinCedre", p, final_amount);

                } else if(block.getType() == Material.POLISHED_DIORITE){
                    mmm.giveMythicMobsItems("pierret1", p, final_amount);

                } else if(block.getType() == Material.DIORITE) {
                    mmm.giveMythicMobsItems("pierret2", p, final_amount);

                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);

            }
            event.setDropItems(false);


        }

    }


    /**
     * Calculate the amount of item get with stuff recolte
     * @param p
     */
    public double calculateStuffRecoltePoint(Player p){
        Inventory inv = p.getInventory();
        EntityEquipment entityEquipment = p.getEquipment();
        double point = 0;

        if(entityEquipment != null){
            ItemStack head = entityEquipment.getHelmet();
            ItemStack heada = mmm.getMythicMobsItems("stuff_recolte_tete_t1");

            ItemStack torce = entityEquipment.getChestplate();
            ItemStack torca = mmm.getMythicMobsItems("stuff_recolte_torce_t1");

            ItemStack bas = entityEquipment.getLeggings();
            ItemStack basa = mmm.getMythicMobsItems("stuff_recolte_bas_t1");

            ItemStack chaussure = entityEquipment.getBoots();
            ItemStack chaussurea = mmm.getMythicMobsItems("stuff_recolte_chaussure_t1");


            if(head != null && head.getType() == Material.LEATHER_HELMET && head.getItemMeta().getDisplayName().equalsIgnoreCase(heada.getItemMeta().getDisplayName())){
                point += 1;
            }

            if(torce != null){
                point += 1;
            }

            return point;
        }

        return 0;
    }




    @EventHandler
    public void testregion(RegionEnteredEvent event){
        Player p = event.getPlayer();
        String region_name = event.getRegionName();
        p.sendMessage(region_name + "test");

        if(p!=null){
            if(region_name.equalsIgnoreCase("testing")){

                String img_king = "%img_king%";
                /*
                 * We parse the placeholders using "setPlaceholders"
                 * This would turn %vault_rank% into the name of the Group, that the
                 * joining player has.
                 */
                img_king = PlaceholderAPI.setPlaceholders(event.getPlayer(), img_king);


                p.sendTitle(img_king + ChatColor.GOLD + "Entrer dans SkyEden." + ChatColor.WHITE + img_king, ChatColor.GREEN + "Zone Safe", 30,30,30);

                p.sendMessage(event.getRegionName());
            }
        }
    }




}
