package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.mythicmobs.MythicMobManager;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractItemStack;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.AbstractWorld;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.entities.BukkitWolf;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.items.ItemManager;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import io.lumine.xikage.mythicmobs.skills.SkillCaster;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class MountCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            MythicMobManager mmm = new MythicMobManager();

            BukkitAPIHelper mb = MythicMobs.inst().getAPIHelper();
            ItemManager mm = MythicMobs.inst().getItemManager();

            MythicMob mob = mb.getMythicMob("wingedspectraltiger");
            AbstractWorld world = BukkitAdapter.adapt(p.getWorld());
            AbstractLocation loc = BukkitAdapter.adapt(p.getLocation());


            ActiveMob active = mob.spawn(loc, 1);
            active.setOwner(p.getUniqueId());

            NewPlayer np = main.getPlayer(p);
            np.sendCMessage("§7Vous devez montez sur votre monture dans les §d1m §7(clique droit)");
            p.setMetadata("need_to_mount", new FixedMetadataValue(main, "1"));

            Entity entity = active.getEntity().getBukkitEntity();

            int mount_delete_time = main.config.getInt("mounted_delete_time");

            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                if(p.hasMetadata("need_to_mount")){
                    entity.remove();
                    np.sendCMessage("§7Votre monture vient de disparraitre ");
                }
            }, (20L *mount_delete_time));

            return true;
        }
        return false;
    }
}
