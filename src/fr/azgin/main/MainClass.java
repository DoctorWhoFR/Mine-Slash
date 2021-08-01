/*
 * This program was made by DrAzgin, you can't use this without my consent. This project is private and you can't sell it, use it for ourself or anything else.
 *
 * This program is licensed.
 * Contact: max.benamara@gmail.com
 */

package fr.azgin.main;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pusher.rest.Pusher;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.azgin.main.Commands.*;
import fr.azgin.main.LootChest.lootchestCommand;
import fr.azgin.main.LootChest.lootchestListener;
import fr.azgin.main.advancedgui.GUIExtension;
import fr.azgin.main.core.globals.SkyCityProtection;
import fr.azgin.main.core.globals.SystemRecollectListener;
import fr.azgin.main.core.loading.Model.NewPlayer;
import fr.azgin.main.core.loading.PlayerLoadingEventListener;
import fr.azgin.main.mythicmobs.MythicMobsInitiationListener;
import fr.azgin.main.protocolib.MineSlashExpansion;
import io.lumine.xikage.mythicmobs.utils.logging.ConsoleColor;
import me.leoko.advancedgui.manager.LayoutManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class MainClass extends JavaPlugin  {


    private File crafts_file;

    public FileConfiguration crafts_list = null;

    public FileConfiguration config = null;

    public Logger logger = this.getLogger();

    private static MainClass instance;

    public static MainClass getInstance() {
        return instance;
    }

    public Pusher pusher = new Pusher("1195753", "b737cb64ef9bc46deb46", "976a6169cb2ebce201dc");

    public MongoDatabase database = null;

    public int counter = 0;

    public List<NewPlayer> playerList = new ArrayList<>();

    public World skycityworld = Bukkit.getWorld("skycitytest");

    public Location spawn_skycity = new Location(skycityworld, 347.069,133,-2088.585);

    public Location getSpawn_skycity(){
        return this.spawn_skycity;
    }

    public static Permission perms = null;

    private static Economy econ = null;

    public static String SKYMAP = "skycity";
    public static String LTTM = "lttm";

    public static String prefix = "";

    public MongoClient client = null;

    public void sendLogMessage(String message){
        logger.info("\n -------------------------------------------"+"\n" + "\n" + "\n" + "\n" + message + ConsoleColor.WHITE + "\n"  + "\n" + "\n" + "\n" + "-------------------------------------------");
    }

    public WorldGuardPlugin getWorldGuard() {
        return (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

    public static List<String> classes_lists = new ArrayList<String>();
    public static List<String> dieux_lists = new ArrayList<String>();

    public void createData() {
        crafts_file = new File(getDataFolder(),"crafts.yml");
        if(!crafts_file.exists()) {
            crafts_file.getParentFile().mkdirs();
            saveResource("crafts.yml",false);
        }
        crafts_list = new YamlConfiguration();
        try {
            crafts_list.load(crafts_file);
        }catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void saveData() throws IOException{
        crafts_list.save(crafts_file);
    }
    public FileConfiguration getData() {
        return this.crafts_list;
    }

    @Override
    public void onEnable() {
        instance = this;

        // save a copy of the defautl confing if
        this.saveDefaultConfig();

        createData();

        MemorySection _crafts = (MemorySection) this.crafts_list.get("crafts");

        this.config = this.getConfig();

        prefix = this.config.getString("prefix");

        classes_lists = this.config.getStringList("classes");

        dieux_lists = this.config.getStringList("dieux");

        String mongodb_connect_url = this.config.getString("mongodb.url");
        String mongodb_databse = this.config.getString("mongodb.database");
        String mongodb_playerconnections = this.config.getString("mongodb.players_collections");

       assert mongodb_connect_url != null;
       ConnectionString connString = new ConnectionString(
               mongodb_connect_url
       );
       MongoClientSettings settings = MongoClientSettings.builder()
               .applyConnectionString(connString)
               .retryWrites(true)
               .build();
       MongoClient mongoClient = MongoClients.create(settings);

       assert mongodb_databse != null;
       this.database = mongoClient.getDatabase(mongodb_databse);


       this.client = mongoClient;

       assert mongodb_playerconnections != null;
       MongoCollection<Document> test = database.getCollection(mongodb_playerconnections);

       sendLogMessage(ConsoleColor.BLUE+"MongoDB loaded" + ConsoleColor.GREEN + " Players loaded: " + test.countDocuments());






        Objects.requireNonNull(this.getCommand("test")).setExecutor(new TestCommands());
        Objects.requireNonNull(this.getCommand("particle")).setExecutor(new AdminPlayerInfoCommand());
        Objects.requireNonNull(this.getCommand("changelevel")).setExecutor(new PusherFakeCommand());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(this.getCommand("plugins")).setExecutor(new Plugins());
        Objects.requireNonNull(this.getCommand("chatcontroll")).setExecutor(new ChatControlCommand());
        Objects.requireNonNull(this.getCommand("levelmanagement")).setExecutor(new LevelManagementCommand());
        Objects.requireNonNull(this.getCommand("bal")).setExecutor(new ballanceCommand());
        Objects.requireNonNull(this.getCommand("classe")).setExecutor(new ClasseCommand());
        Objects.requireNonNull(this.getCommand("resetclasse")).setExecutor(new resetClasseCommand());
        Objects.requireNonNull(this.getCommand("dieu")).setExecutor(new DieuCommand());
        Objects.requireNonNull(this.getCommand("buff")).setExecutor(new BuffCommand());
        Objects.requireNonNull(this.getCommand("bug")).setExecutor(new BugCommand());
        Objects.requireNonNull(this.getCommand("exportmythiccommand")).setExecutor(new ExportMythicCommand());
        Objects.requireNonNull(this.getCommand("lootchest")).setExecutor(new lootchestCommand());
        Objects.requireNonNull(this.getCommand("msreload")).setExecutor(new ConfigReloadCommand());
        Objects.requireNonNull(this.getCommand("mount")).setExecutor(new MountCommand());
        Objects.requireNonNull(this.getCommand("craft")).setExecutor(new CraftCommand());
        Objects.requireNonNull(this.getCommand("adminmount")).setExecutor(new adminmountCommand());


        Bukkit.getPluginManager().registerEvents(new SystemRecollectListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoadingEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MythicMobsInitiationListener(), this);
        Bukkit.getPluginManager().registerEvents(new SkyCityProtection(), this);

        Bukkit.getPluginManager().registerEvents(new lootchestListener(), this);

        sendLogMessage(ConsoleColor.YELLOW + "Mine&Slash" + ConsoleColor.CYAN + "loaded !" + ConsoleColor.WHITE);
        LayoutManager.getInstance().registerLayoutExtension(new GUIExtension(), this);

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

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if(rsp != null){
            perms = rsp.getProvider();
        }

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
        return true;
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

            return this.playerList.get(metadataValue.asInt());
        }

        return null;
    }

    public NewPlayer getPlayerByUUID(UUID uuid){

        Player p = Bukkit.getPlayer(uuid);
        MetadataValue metadataValue = null;
        if (p != null) {
            metadataValue = p.getMetadata("server_id").get(0);
        }

        assert metadataValue != null;
        return this.playerList.get(metadataValue.asInt());
    }
}
