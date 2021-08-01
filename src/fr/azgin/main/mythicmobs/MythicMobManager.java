package fr.azgin.main.mythicmobs;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.items.ItemManager;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class MythicMobManager {

    public MythicMobManager MythicMobManager(){
        return this;
    }

    public Optional<MythicItem> getItems(String item_name){
        ItemManager im = MythicMobs.inst().getItemManager();


        return im.getItem(item_name);
    }

    public ItemStack getMythicMobsItems(String name){
        ItemManager im = MythicMobs.inst().getItemManager();

        return im.getItemStack(name);
    }

    public void giveMythicMobsItems(String name, Player p, double amount){
        ItemManager im = MythicMobs.inst().getItemManager();
        ItemStack imItemStack = im.getItemStack(name);

        for (int i = 0; i < amount; i++) {
            p.getInventory().addItem(imItemStack);
        }
    }

    public MythicMob getMobs(String name){
        MobManager mb = MythicMobs.inst().getMobManager();

        return mb.getMythicMob(name);
    }

}
