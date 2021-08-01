/*
 * This program was made by DrAzgin, you can't use this without my consent. This project is private and you can't sell it, use it for ourself or anything else.
 *
 * This program is licensed.
 * Contact: max.benamara@gmail.com
 */

package fr.azgin.main.Commands;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.mythicmobs.MythicMobManager;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class adminmountCommand implements CommandExecutor {


    MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {




        if(sender.hasPermission("mineslash.mountadmin")){

            if(args.length == 2){

                Player target = Bukkit.getPlayer(args[0]);
                NewPlayer nt = mainClass.getPlayer(target);


                if(target != null){

                    MobManager mmm = MythicMobs.inst().getMobManager();
                    MythicMob optinal = mmm.getMythicMob(args[1]);

                    if(optinal != null){
                        MongoCollection<Document> montures = mainClass.database.getCollection("montures");
                        montures.insertOne(new Document("uuid", target.getUniqueId().toString()).append("monture", args[1]));
                        nt.sendCMessage("§7Vous venez d'obtenir la monture: §d" + args[1]);
                    } else {
                        sender.sendMessage("§7Le mob: §d" + args[1] + "§7n'existe pas");
                    }

                } else {
                    sender.sendMessage("§7Aucun joueur trouver.");
                }

            } else {
                sender.sendMessage("/adminmount <player> <monture>");
            }

        }

        return true;
    }
}
