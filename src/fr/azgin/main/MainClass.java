package fr.azgin.main;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pusher.rest.Pusher;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.azgin.main.Commands.*;
import fr.azgin.main.core.globals.SkyCityProtection;
import fr.azgin.main.core.globals.SystemRecollectListener;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.core.loading.PlayerLoadingEventListener;
import fr.azgin.main.mythicmobs.MythicMobsInitiationListener;
import fr.azgin.main.protocolib.MineSlashExpansion;
import io.lumine.xikage.mythicmobs.utils.logging.ConsoleColor;
import net.jitse.npclib.NPCLib;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public class    MainClass extends JavaPlugin  {


    private NPCLib library;
    public Logger logger = this.getLogger();

    private static MainClass instance;

    public static MainClass getInstance() {
        return instance;
    }

    public NPCLib getNPCLib() {
        return library;
    }

    public Pusher pusher = new Pusher("1195753", "b737cb64ef9bc46deb46", "976a6169cb2ebce201dc");

    public MongoDatabase database = null;

    public int counter = 0;

    public List<NewPlayer> playerList = new ArrayList<NewPlayer>();

    public World skycityworld = Bukkit.getWorld("skycitytest");

    public Location spawn_skycity = new Location(skycityworld, 347.069,133,-2088.585);

    public Location getSpawn_skycity(){
        return this.spawn_skycity;
    }

    public static Permission perms = null;

    private static Economy econ = null;

    public static String SKYMAP = "skycity";
    public static String LTTM = "lttm";


    public MongoClient client = null;

    public void sendLogMessage(String message){
        logger.info("\n" + "\n" + "\n" + "\n" + message + ConsoleColor.WHITE + "\n"  + "\n" + "\n" + "\n");
    }

    public WorldGuardPlugin getWorldGuard() {
        return (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

    public static ArrayList<String> classes_lists = new ArrayList<String>();
    public static ArrayList<String> dieux_lists = new ArrayList<String>();

    @Override
    public void onEnable() {

        classes_lists.add("guerrier");
        classes_lists.add("mage");
        classes_lists.add("archer");


        dieux_lists.add("poseidon");
        dieux_lists.add("hades");
        dieux_lists.add("hephaistos");

       try {
           ConnectionString connString = new ConnectionString(
                   "mongodb://AdminSammy:tardisloved123@163.172.15.88:27017"
           );
           MongoClientSettings settings = MongoClientSettings.builder()
                   .applyConnectionString(connString)
                   .retryWrites(true)
                   .build();
           MongoClient mongoClient = MongoClients.create(settings);
           this.database = mongoClient.getDatabase("mineslash");
           this.client = mongoClient;

           MongoCollection<Document> test = database.getCollection("players");

           sendLogMessage(ConsoleColor.BLUE+"MongoDB loaded" + ConsoleColor.GREEN + " Players loaded: " + test.countDocuments());

       } catch (IllegalStateException e) {

       }



        instance = this;

        this.library = new NPCLib(this);

        this.getCommand("test").setExecutor(new TestCommands());
        this.getCommand("particle").setExecutor(new ParticleTEst());
        this.getCommand("changelevel").setExecutor(new PusherFakeCommand());
        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("plugins").setExecutor(new Plugins());
        this.getCommand("chatcontroll").setExecutor(new ChatControlCommand());
        this.getCommand("levelmanagement").setExecutor(new LevelManagementCommand());
        this.getCommand("bal").setExecutor(new ballanceCommand());
        this.getCommand("classe").setExecutor(new ClasseCommand());
        this.getCommand("resetclasse").setExecutor(new resetClasseCommand());
        this.getCommand("dieu").setExecutor(new DieuCommand());
        this.getCommand("buff").setExecutor(new BuffCommand());
        this.getCommand("bug").setExecutor(new BugCommand());

        Bukkit.getPluginManager().registerEvents(new SystemRecollectListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoadingEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MythicMobsInitiationListener(), this);
        Bukkit.getPluginManager().registerEvents(new SkyCityProtection(), this);

        sendLogMessage(ConsoleColor.YELLOW + "Mine&Slash" + ConsoleColor.CYAN + "loaded !");

        /*
        Protocolib
         */
        new MineSlashExpansion().register();

        /*
        Vault
         */
        if (!setupEconomy() ) {
            this.logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setupPermissions();


    }

    @Override
    public void onDisable() {

        this.client.close();
        this.logger.info("Closing connection to database to prevent multiple handle error");


    }

    public static Permission getPerms() {
        return perms;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public Economy getEcon(){
        return econ;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public void loadPlayerIntoList(NewPlayer np){
        this.playerList.add(this.counter, np);

        np.get_p().setMetadata("server_id", new FixedMetadataValue(this, this.counter));

        this.counter++;

        this.getServer().broadcastMessage(ChatColor.GREEN + "> " + np.get_p().getName() + " vient de rejoindre ! LVL: " + np.getLevel());
    }

    public NewPlayer getPlayer(Player p){

        if(this.playerList.size() != 0){
            MetadataValue metadataValue = p.getMetadata("server_id").get(0);
            NewPlayer np = this.playerList.get(metadataValue.asInt());

            return np;
        }

        return null;
    }

    public NewPlayer getPlayerByUUID(UUID uuid){

        Player p = Bukkit.getPlayer(uuid);
        MetadataValue metadataValue = p.getMetadata("server_id").get(0);
        NewPlayer np = this.playerList.get(metadataValue.asInt());

        return np;
    }
}
