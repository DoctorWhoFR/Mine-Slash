package fr.azgin.main.Commands;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MountCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 0){
                if(!p.hasMetadata("mount_spawned")) {
                    BukkitAPIHelper mb = MythicMobs.inst().getAPIHelper();
                    NewPlayer np = main.getPlayer(p);

                    if(p.hasMetadata("selected_mount")){

                        List<MetadataValue> selected_mountmeta = p.getMetadata("selected_mount");
                        MetadataValue selected_mount = selected_mountmeta.get(selected_mountmeta.size()-1);


                        MythicMob mob = mb.getMythicMob(selected_mount.asString());
                        AbstractLocation loc = BukkitAdapter.adapt(p.getLocation());

                        ActiveMob active = mob.spawn(loc, 1);
                        active.setOwner(p.getUniqueId());
                        Entity entity = active.getEntity().getBukkitEntity();

                        p.setMetadata("mineslash_mythic", new FixedMetadataValue(main, active.getUniqueId()));

                        np.sendCMessage("§7Vous devez montez sur votre monture dans les §d1m §7(clique droit)");
                        p.setMetadata("need_to_mount", new FixedMetadataValue(main, "1"));

                        int mount_delete_time = main.config.getInt("mounted_delete_time");

                        p.setMetadata("mount_spawned", new FixedMetadataValue(main, "1"));

                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                            if(p.hasMetadata("need_to_mount")){
                                active.setDespawned();
                                MythicMobs.inst().getMobManager().unregisterActiveMob(active);
                                active.getEntity().remove();

                                np.sendCMessage("§7Votre monture vient de disparraitre ");
                                p.removeMetadata("need_to_mount", main);
                                p.removeMetadata("mount_spawned", main);
                            }
                        }, (20L *mount_delete_time));
                    } else {
                        np.sendCMessage("§7Vous devez d'abbord choisir une monture.");
                    }
                }

            } else {
                MongoCollection<Document> montures = this.main.database.getCollection("montures");
                FindIterable<Document> montures_list = montures.find(new Document("uuid", p.getUniqueId().toString()).append("monture", args[0]));

                if(montures_list.first() != null){
                    p.setMetadata("selected_mount", new FixedMetadataValue(main, args[0]));
                    p.sendMessage(MainClass.prefix + "§7Vous venez de selectionnez la monture: §d" + args[0]);
                } else {
                    p.sendMessage(MainClass.prefix + "§7La monture demande n'existe pas, ou vous n'avez pas la monture.");
                }
            }


            return true;
        }
        return false;
    }
}
