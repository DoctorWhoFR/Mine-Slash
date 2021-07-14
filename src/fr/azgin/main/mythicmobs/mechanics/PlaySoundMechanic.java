package fr.azgin.main.mythicmobs.mechanics;

import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.api.interfaces.Client;
import com.craftmend.openaudiomc.api.interfaces.MediaApi;
import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;

public class PlaySoundMechanic extends SkillMechanic implements ITargetedEntitySkill {

    MainClass main = MainClass.getInstance();

    String sound = "";

    public PlaySoundMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);

        String str = mlc.getString("sound").strip();
        this.main.getLogger().info(str);
        this.sound = str;
    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        if(abstractEntity.isPlayer()){
            NewPlayer np = this.main.getPlayerByUUID(abstractEntity.getUniqueId());
            AudioApi api = AudioApi.getInstance();
            MediaApi mediaApi = api.getMediaApi();
            
            Client user_client = api.getClient(abstractEntity.getUniqueId());

            if(user_client != null && user_client.isConnected()){
                if(this.sound.equals("smash")){
                    mediaApi.playMedia(user_client, "https://www.youtube.com/watch?v=XMEfm-z9z4A");
                }
            }



        }

        return false;
    }

}
