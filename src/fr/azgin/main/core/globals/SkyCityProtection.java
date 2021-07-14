package fr.azgin.main.core.globals;

import fr.azgin.main.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkyCityProtection implements Listener {

    /**
     * Block place protection
     * EVENT: BlockPlaceEvent
     *
     * @param event
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        boolean bol = MainClass.getPerms().has(p, "mineslash_admin");

        if (p.getWorld().getName().equalsIgnoreCase(MainClass.SKYMAP) && !bol) {
            event.setCancelled(true);
            event.setBuild(false);
            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire ceci !");
        }

    }

    /**
     * Block break protection
     * @param event
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        boolean bol = MainClass.getPerms().has(p, "mineslash_admin");

        if (p.getWorld().getName().equalsIgnoreCase(MainClass.SKYMAP) && !bol) {
            event.setCancelled(true);
            event.setDropItems(false);
            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire ceci !");
        }

    }


    /**
     * SkyEden spawn protection
     * @param e
     */
    @EventHandler
    public void fallPlayerProtectionTp(PlayerMoveEvent e){
        if(e.getPlayer().getWorld().getName().equals("skycitytest")){
            Player p = e.getPlayer();
            Location loc = p.getLocation();

            if(loc.getY() < 60){
                World skycityworld = Bukkit.getWorld("skycitytest");
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 10,100));
                Location spawn_skycity = new Location(skycityworld, 347.069,133,-2088.585);
                p.teleport(spawn_skycity);
                p.sendMessage("Be safe you can't fly for now ! ^^");
            }

        }
    }



}
