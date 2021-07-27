package fr.azgin.main.protocolib;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MineSlashExpansion extends PlaceholderExpansion {

    MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean canRegister(){
        return true;
    }


    @NotNull
    @Override
    public String getIdentifier() {
        return "mineslash";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "azginfr";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "1.0";
    }


    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier){

        if(this.mainClass.playerList.size() != 0){
            if(identifier.equals("level")) {
                if (player.isOnline() && player.getPlayer() != null) {

                        NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                        return String.valueOf(np.getLevel());
                }
            }

            if(identifier.equals("xp")) {
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(np.getXP());
                }
            }

            if(identifier.equals("xppourcent")) {
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(np.getXpPourcent());
                }
            }

            if(identifier.equalsIgnoreCase("xpmax")){
                if(player.isOnline() && player.getPlayer() != null){
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(Math.round(np.xpmax()));
                }
            }

            if(identifier.equalsIgnoreCase("faction")){
                if(player.isOnline() && player.getPlayer() != null){
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return np.getFACTION();
                }
            }

            if(identifier.equalsIgnoreCase("faction_xp")){
                if(player.isOnline() && player.getPlayer() != null){
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(np.getFACTIONXP());
                }
            }

            if(identifier.equalsIgnoreCase("craft")){
                if(player.isOnline() && player.getPlayer() != null){
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(np.getCraft());
                }
            }

            if(identifier.equalsIgnoreCase("craft_xp")){
                if(player.isOnline() && player.getPlayer() != null){
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(np.getCraftXP());
                }
            }

            if(identifier.equals("custommana")){
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(np.getMANA());
                }
            }

            if(identifier.equals("custommanapourcent")){
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(np.getManaPourcent());
                }
            }

            if(identifier.equals("custommanamax")){
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    return String.valueOf(Math.round(np.getMaxMana()));
                }
            }

            if(identifier.equals("classe")){
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());

                    String classe = np.getClasse();

                    if(classe.equals("null")){
                        return "AUCUNE CLASSE";
                    } else {
                        return String.valueOf(np.getClasse());
                    }

                }
            }

            if(identifier.equals("dieu")){
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());

                    String dieu = np.getDieu();

                    if(dieu.equals("null")){
                        return "AUCUN DIEU";
                    } else {
                        return dieu;
                    }

                }
            } else if(identifier.equals("gui_test")){
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    String _testmeta = "mineslash_gui_test_1";

                    if(np.get_p().hasMetadata(_testmeta)){
                        List<MetadataValue> dieu = np.get_p().getMetadata(_testmeta);
                        MetadataValue _val = dieu.get(dieu.size()-1);

                        return _val.asString();
                    } else {
                        return "null";
                    }

                }
            } else if(identifier.equals("dieu_buff")){
                if (player.isOnline() && player.getPlayer() != null) {
                    NewPlayer np = this.mainClass.getPlayer(player.getPlayer());
                    String _testmeta = "buff_countdown";

                    if(np.get_p().hasMetadata(_testmeta)){
                        return "true";
                    } else {
                        return "false";
                    }

                }
            }

        }

        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        return null;
    }
}
