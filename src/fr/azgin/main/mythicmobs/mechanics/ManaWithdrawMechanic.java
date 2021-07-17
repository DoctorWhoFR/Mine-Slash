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

    int mana_to_withdraw = 0;

    public ManaWithdrawMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);

        this.mana_to_withdraw = mlc.getInteger("amount");
    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        if(abstractEntity.isPlayer()){
            NewPlayer np = this.main.getPlayerByUUID(abstractEntity.getUniqueId());

            np.removeMana(this.mana_to_withdraw);

            this.main.getLogger().info("removed "+this.mana_to_withdraw+" mana from " + abstractEntity.getName());
        }


        return false;
    }
}
