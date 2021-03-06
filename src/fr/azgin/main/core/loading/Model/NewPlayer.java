package fr.azgin.main.core.loading.Model;

import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.api.interfaces.Client;
import com.craftmend.openaudiomc.api.interfaces.MediaApi;
import com.mongodb.client.MongoCollection;
import fr.azgin.main.MainClass;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class NewPlayer {

    private final MainClass mainClass = MainClass.getInstance();

    private Player _p = null;

    private final MongoCollection<Document> players = this.mainClass.database.getCollection("players");

    private Document document = null;

    public NewPlayer(Player player, Document doc){

        _p = player;

        this.document = doc;
    }

    public Player get_p() {
        return _p;
    }

    public Document getDocument() {
        return document;
    }

    /*
     * LEVEL FUNCTION
     */
    public int getLevel(){
        return Integer.parseInt(this.document.get("level").toString());
    }

    public void setLevel(Integer xp){
        Bson modification = new Document("level", xp);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }
    // -----------------------------------------------------------------------------

    /*
        XP FUNCTION
     */
    public Integer getXP(){
        return Integer.parseInt(this.document.get("xp").toString());
    }

    public void setXP(Integer xp){
        Bson modification = new Document("xp", xp);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();

        this.checkXp();
    }

    public void addXP(Integer xp){
        Bson modification = new Document("xp", this.getXP() + xp);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();

        this.checkXp();
    }

    public void checkXp(){

        Document doc = this.document;
        int xp = Integer.parseInt(doc.get("xp").toString());
        int level = Integer.parseInt(doc.get("level").toString());
        int nextlevel = level + 1;
        double needed_xp = 100*Math.pow(1.1, level);

        if(xp >= needed_xp){
            // level up user
            this.setLevel(nextlevel);
            this.setXP(0);
            this._p.sendMessage("Vous venez de level up !");

            this.sendTitle(ChatColor.YELLOW + "Level up niveau " + ChatColor.GREEN + nextlevel, ChatColor.BLUE + "Vous venez de level up !", 100);
            this.playSounds("https://www.youtube.com/watch?v=J_UUbG_N4gE");
            this.sendTotenAnimation();

        }
    }

    public double xpmax(){
        Document doc = this.document;
        int xp = Integer.parseInt(doc.get("xp").toString());
        int level = Integer.parseInt(doc.get("level").toString());
        int nextlevel = level + 1;

        return 100*Math.pow(1.1, level);
    }

    public double getXpPourcent(){
        Document doc = this.document;
        int level = Integer.parseInt(doc.get("level").toString());
        int xp = Integer.parseInt(doc.get("xp").toString());

        double needed_xp = 100*Math.pow(1.1, level);

        return (100*xp) / needed_xp;
    }
    // -------------------------------------------------------

    /*
        FACTION FUNCTION
     */
    public String getFACTION(){
        Object faction = this.document.get("faction");

        if(faction != null){
            return this.document.get("faction").toString();
        } else {
            return "none";
        }
    }

    public double getFACTIONXP(){
        Object faction = this.document.get("faction_xp");

        if(faction != null){
            return Integer.parseInt(this.document.get("faction_xp").toString());
        } else {
            return 0;
        }
    }

    public void setFACTION(String faction){
        Bson modification = new Document("faction", faction);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }

    public void setFACTIONXP(Integer xp){
        Bson modification = new Document("faction", xp);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }

    public void addFACTIONXP(Integer xp){
        Bson modification = new Document("faction", this.getFACTIONXP() + xp);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }
    // ------------------------------------------------------------------


    /*
        MANA FUNCTION
     */
    public Integer getMANA(){
        return Integer.parseInt(this.document.get("mana").toString());
    }


    public void removeMana(int amount){
        Bson modification = new Document("mana", this.getMANA() - amount);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }

    public void addMANA(Integer mana){
        Bson modification = new Document("mana", this.getMANA() + mana);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();

    }

    public void setMANA(Integer mana){
        Bson modification = new Document("mana", mana);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();

        this.checkXp();
    }

    public double getMaxMana(){
        Document doc = this.document;
        int level = Integer.parseInt(doc.get("level").toString());
        int mana = Integer.parseInt(doc.get("mana").toString());

        return 100+(10*level);
    }

    public double getManaPourcent(){
        Document doc = this.document;
        int level = Integer.parseInt(doc.get("level").toString());
        int mana = Integer.parseInt(doc.get("mana").toString());

        double needed_mana = 100+(10*level);

        return (100*mana) / needed_mana;

    }

    ///
    // Classe function
    ////

    public void setClasse(String classe){
        Bson modification = new Document("classe", classe);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }

    public String getClasse(){
        Document doc = this.document;
        Object classe = doc.get("classe");

        if(classe != null){
            return classe.toString();
        }

        return "null";
    }

    ///
    // Classe function
    ////

    public void setDieu(String classe){
        Bson modification = new Document("dieu", classe);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }

    public String getDieu(){
        Document doc = this.document;
        Object classe = doc.get("dieu");

        if(classe != null){
            return classe.toString();
        }

        return "null";
    }



    // ----------------------------------------------------

    /*
        CRAFT FUNCTION
     */
    public Integer getCraft(){
        return Integer.parseInt(this.document.get("craft_level").toString());
    }

    public Integer getCraftXP(){
        return Integer.parseInt(this.document.get("craft_xp").toString());
    }

    public void setCraft(Integer level){
        Bson modification = new Document("craft_level", level);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }

    public void setCRAFTXP(Integer xp){
        Bson modification = new Document("craft_xp", this.getCraftXP() + xp);
        Bson modifcation_query = new Document("$set", modification);

        this.players.updateOne(this.document, modifcation_query);

        this.updatePlayer();
    }



    //-------------------------------------------------------------------------------------


    public void sendCMessage(String text){

        String prefix = mainClass.config.getString("prefix");

        this.get_p().sendMessage(prefix + text);
    }


    /**
     * update player
     */
    public void updatePlayer(){
        this.document = players.find(new Document("uuid", this._p.getUniqueId().toString())).first();
    }

    public boolean hasPermissions(String permission){
        return _p.hasPermission(permission);
    }

    public void playSounds(String url){
        AudioApi api = AudioApi.getInstance();
        Client client = api.getClient(this._p.getUniqueId());
        MediaApi mediaApi = api.getMediaApi();

        if(client != null && client.isConnected()){
            mediaApi.playMedia(client, url);
        }
    }

    public void sendTitle(String title, String subtitle, Integer time){
        this._p.sendTitle(title, subtitle, 10, time, 20);
    }

    public void sendTotenAnimation(){
        for (int i = 0; i < 3; i++) {
            this.mainClass.getServer().dispatchCommand(Bukkit.getConsoleSender(), "iaplaytotemanimation animatedtitles:level " + this._p.getName());
        }
    }

    public void clearChat(){
        for (int i = 0; i < 50; i++) {
            this.get_p().sendMessage("");
        }
    }

    public void sendCountdownMessage(String id, String message, Integer countdown){

        Player p = this.get_p();

        if(!p.hasMetadata("mineslash_countdown_message_"+id)){
            p.sendMessage(message);

            p.setMetadata("mineslash_countdown_message_"+id, new FixedMetadataValue(mainClass, 1));

            Bukkit.getScheduler().scheduleSyncDelayedTask(mainClass, () -> {
                p.removeMetadata("mineslash_countdown_message_"+id, mainClass);
            }, (20L * countdown));
        }

    }


    public String getMetadata(String id){
        List<MetadataValue> _meta = this.get_p().getMetadata(id);

        if(_meta.size() > 0){
            MetadataValue _val = _meta.get(_meta.size() - 1);
            return _val.asString();
        }

        return null;
    }

    public void setMetadata(String id, String value){
        this.get_p().setMetadata(id, new FixedMetadataValue(mainClass, value));
    }

    public void removeMeta(String id){
        this.get_p().removeMetadata(id, mainClass);
    }

}
