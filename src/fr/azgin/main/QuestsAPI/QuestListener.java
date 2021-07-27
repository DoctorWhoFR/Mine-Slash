package fr.azgin.main.QuestsAPI;

import fr.azgin.main.MainClass;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;
import me.blackvein.quests.events.quest.QuestTakeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

public class QuestListener implements Listener {

    MainClass main = MainClass.getInstance();
    Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");

    @EventHandler
    public void questTakeEvent(QuestTakeEvent event){

        Player p = event.getQuester().getPlayer();
        Quest test = event.getQuest();

        String quest_player_metakey = "mineslash_quests";

        String quest_name = test.getName();
        String quest_description = test.getName();
        String quest_id = test.getId();

        p.setMetadata(quest_player_metakey + "_" + quest_id + "_name", new FixedMetadataValue(main,""));

    }

}
