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

    ItemManager im = new ItemManager(MythicMobs.inst());
    MobManager  m = new MobManager(MythicMobs.inst());

    public MythicMobManager MythicMobManager(){
        return this;
    }

    public MythicItem getItems(String item_name){

        Optional<MythicItem> mythicMobOptional = im.getItem(item_name);

        return mythicMobOptional.orElse(null);
    }

    public ItemStack getMythicMobsItems(String name){
        ItemManager im = new ItemManager(MythicMobs.inst());

        return im.getItemStack(name);
    }

    public void giveMythicMobsItems(String name, Player p, double amount){
        ItemManager im = new ItemManager(MythicMobs.inst());
        ItemStack imItemStack = im.getItemStack(name);

        for (int i = 0; i < amount; i++) {
            p.getInventory().addItem(imItemStack);
        }
    }

    public void getMobs(Player p ,String name){
        MobManager mb = new MobManager(MythicMobs.inst());

        MythicMob mob = mb.getMythicMob(name);

    }

}
