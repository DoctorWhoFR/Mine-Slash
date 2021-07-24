package fr.azgin.main.Commands;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fr.azgin.main.MainClass;
import fr.azgin.main.mythicmobs.MythicMobManager;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.io.MythicConfig;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class ExportMythicCommand implements CommandExecutor {

    MainClass mainClass = MainClass.getInstance();
    private MongoCollection<Document> items = this.mainClass.database.getCollection("items");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        MythicMobManager mmm = new MythicMobManager();

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(p.hasPermission("mineslash.admin")){

                ArrayList<MythicItem> mobs = new ArrayList<>(MythicMobs.inst().getItemManager().getItems());

                Collections.sort(mobs);
                if (args.length > 0) {
                    sender.sendMessage(ChatColor.GOLD + "Items found containing " + args[0] + ": ");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "Items Loaded (" + mobs.size() + "): ");
                }

                for (MythicItem mm : mobs) {
                    MythicConfig config = mm.getConfig();
                    String name = mm.getDisplayName();
                    String mythic_type = mm.getMythicType();
                    List<String> lores = mm.getLore();
                    String internal_name = mm.getInternalName();
                    StringBuilder final_text = new StringBuilder();
                    String IconURL = config.getString("IconURL");

                    for(String lore : lores){
                        final_text.append("\n").append(lore);
                    }

                    Document item = new Document("internal_name", internal_name);

                    FindIterable<Document> doc = this.items.find(item);

                    if(doc.first() == null){
                        p.sendMessage("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                        p.sendMessage(ChatColor.GOLD + "EXPORTING >"  + ChatColor.RED +  "InternalName:" + internal_name);
                        p.sendMessage("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

                        item.append("name", name);
                        item.append("lore", final_text.toString());
                        item.append("type", mythic_type);
                        item.append("IconURL", Objects.requireNonNullElse(IconURL, "https://i.imgur.com/JDEL7HC.png"));

                        this.items.insertOne(item);
                    }


                }

                p.sendMessage("Exported all " + mobs.size() + "items");

                return true;
            }

        }



        return false;


    }
}
