package fr.azgin.main.mythicmobs.mechanics;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;

public class ManaWithdrawMechanic extends SkillMechanic implements ITargetedEntitySkill {

    MainClass main = MainClass.getInstance();

    public ManaWithdrawMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);


    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        if(abstractEntity.isPlayer()){
            NewPlayer np = this.main.getPlayerByUUID(abstractEntity.getUniqueId());

            np.removeMana(100);

            this.main.getLogger().info("removed 100 mana from " + abstractEntity.getName());
        }


        return false;
    }
}
