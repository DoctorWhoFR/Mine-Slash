package fr.azgin.main.core.loading;

import com.mongodb.client.MongoCollection;
import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.mythicmobs.MythicMobManager;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @version 1.0
 * @author Azgin
 * @apiNote Main event class for managing all player's connections stuffs
 *
 * @see org.bukkit.event.Listener
 */
public class PlayerLoadingEventListener implements Listener {

    MainClass mainClass = MainClass.getInstance();

    public String wood_t1 = ChatColor.BLUE + "[TIER 1] " + ChatColor.YELLOW + "WOOD";
    public String wood_t2 = "";
    public String wood_t3 = "";
    public String wood_t4 = "";

    /**
     * load player into database
     * @event PlayerJoinEvent
     * @param event
     */
    @EventHandler
    public void loadPlayer(PlayerJoinEvent event){

        Player p = event.getPlayer();


        if(p.isOp()){
            p.sendMessage("test");
            p.setGameMode(GameMode.CREATIVE);
        }

        if(p.getWorld().getName().equals("world")){
            World skycityworld = Bukkit.getWorld("skycitytest");
            if(skycityworld != null){
                Location spawn_skycity = new Location(skycityworld, 347.069,133,-2088.585);
                p.teleport(spawn_skycity);
            }
        }

        /**
         * Create new_player and add to server list
         */
        MongoCollection<Document> players = this.mainClass.database.getCollection("players");

        Document doc = players.find(new Document("uuid", p.getUniqueId().toString())).first();

        if(doc != null){
            NewPlayer np = new NewPlayer(p, doc);

            this.mainClass.loadPlayerIntoList(np);

            np.get_p().sendMessage(np.getXP().toString());

            this.setPlayerStats(p);
            this.welcomeMessage(np);
        } else {
            Document document = new Document("uuid", p.getUniqueId().toString()).append("display_name", p.getDisplayName()).append("dieu", null).append("level", "1").append("xp", "0").append("mana", "100").append("faction", null).append("faction_level", "1").append("faction_xp", "0").append("craft_level", "1").append("craft_xp", "0").append("classe", null);
            players.insertOne(document);

            NewPlayer np = new NewPlayer(p, document);

            this.mainClass.loadPlayerIntoList(np);

            this.setPlayerStats(p);
            this.welcomeMessage(np);
        }


    }

    /**
     * Relative start message content
      * @param np
     */
    public void welcomeMessage(NewPlayer np){
        Player p = np.get_p();
        np.clearChat();

        TextComponent message = new TextComponent(ChatColor.DARK_AQUA + "Rejoindre le spawn");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spawn"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.BLUE + "Pour vous t??l??portez au spawn !")));

        TextComponent message_audio = new TextComponent(ChatColor.DARK_GREEN + "Pour une meilleur exp??rience clique sur moi");
        message_audio.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/audio"));
        message_audio.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.BLUE + "Clique sur moi ! Pour une exp??rience j'avais vue !")));

        p.sendMessage(ChatColor.DARK_GREEN + "???????????????????????????????????????????????????" );
        p.sendMessage(ChatColor.DARK_AQUA+">"+ChatColor.GOLD+" Bienvenue sur "+ChatColor.GREEN+"Mine&Slash !");
        p.sendMessage("");
        p.sendMessage("" + p.isOp());
        p.sendMessage("");
        p.sendMessage(ChatColor.DARK_AQUA+">"+ChatColor.GOLD+" Votre niveau: " + np.getLevel());
        p.sendMessage(ChatColor.DARK_AQUA+">"+ChatColor.GOLD+" Location: " + Objects.requireNonNull(p.getLocation().getWorld()).getName());
        p.sendMessage("");
        p.spigot().sendMessage(message);
        p.sendMessage("");
        p.spigot().sendMessage(message_audio);
        p.sendMessage("");
        p.sendMessage(ChatColor.DARK_AQUA+">"+ChatColor.GOLD+" Vie: " +  Math.round(p.getHealth()) + "/" + Math.round(Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()));
        p.sendMessage(ChatColor.DARK_GREEN + "???????????????????????????????????????????????????" );

    }

    /**
     * Loved set player stat ^^
     *
     * * @param p
     *
     */
    public void setPlayerStats(Player p){

        // get the player
        NewPlayer np = this.mainClass.getPlayer(p);

        // calculate the health of the player based on the level, 20 based + 0.1ptn per level.
        double powered = 0.3*np.getLevel();
        double health = 20+(powered);

        // set player health
        Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
        p.setHealth(health);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();

        NewPlayer np = this.mainClass.getPlayer(p);

        this.mainClass.getServer().broadcastMessage(MainClass.prefix + "??7Le joueur ??d" + p.getName() +  "??7 vient de quitter le serveur!");
    }


    /**
     * function for mana system
     *
     * @param e
     */
    @EventHandler
    public void onPlayerWalkUpdateGui(PlayerToggleSneakEvent e) {

        Player p = e.getPlayer();

        NewPlayer np = this.mainClass.getPlayer(p);

        if(!e.isSneaking()){
            if(np.getMANA() + 30 < np.getMaxMana()){
                np.addMANA(30);
            } else {
                np.setMANA((int) np.getMaxMana());
            }
        }

    }

    @EventHandler
    public void onPlayerMountEvent(EntityMountEvent event){
        Player p = (Player) event.getEntity();

        if(p.hasMetadata("need_to_mount")){
            p.removeMetadata("need_to_mount", mainClass);
        }
    }

    @EventHandler
    public void onPlayerDismountEvent(EntityDismountEvent event){

        if(event.getEntity() instanceof Player){

            Player p = (Player) event.getEntity();
            Entity ent = event.getDismounted();

            if(p.hasMetadata("mineslash_mythic")){

                MobManager mb = MythicMobs.inst().getMobManager();

                List<MetadataValue> mythic_id_list = p.getMetadata("mineslash_mythic");
                MetadataValue mythic_id = mythic_id_list.get(mythic_id_list.size()-1);

                Optional<ActiveMob> active = mb.getActiveMob(UUID.fromString(mythic_id.asString()));
                NewPlayer np = mainClass.getPlayer(p);

                int mount_delete_time = mainClass.config.getInt("mounted_delete_time");

                p.setMetadata("need_to_mount", new FixedMetadataValue(mainClass, "1"));

                Bukkit.getScheduler().scheduleSyncDelayedTask(mainClass, () -> {
                    if(p.hasMetadata("need_to_mount")){
                        p.removeMetadata("need_to_mount", mainClass);

                        if(active.isPresent()){

                            ActiveMob _active = active.get();

                            if(_active.getOwner().get() == p.getUniqueId()){
                                np.sendCMessage("??7Votre monture vient de disparraitre.");
                                _active.setDespawned();
                                MythicMobs.inst().getMobManager().unregisterActiveMob(_active);
                                _active.getEntity().remove();
                            }

                        } else {
                            mainClass.sendLogMessage("Mythicmobs c'est de la merde");
                        }
                        p.removeMetadata("mount_spawned", mainClass);
                    }

                }, ((20L * (mount_delete_time)+40)));

            }

        }
    }

//    @EventHandler
//    public void test(BlockPlaceEvent event){
//        Block block = event.getBlock();
//        TileState state = (TileState) block.getState();
//        PersistentDataContainer data = state.getPersistentDataContainer();
//
//    }


}
