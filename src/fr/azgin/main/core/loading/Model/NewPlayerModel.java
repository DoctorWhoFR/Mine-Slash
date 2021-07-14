package fr.azgin.main.core.loading.Model;

import org.bson.Document;

public class NewPlayerModel extends Document {

    public String level = "1";
    public String xp = "0";
    public String mana = "100";
    public String uuid = "";

    public NewPlayerModel(String uuid){
        this.uuid = uuid;
    }

}
