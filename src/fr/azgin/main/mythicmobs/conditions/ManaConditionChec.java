package fr.azgin.main.mythicmobs.conditions;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;

public class ManaConditionChec extends SkillCondition implements IEntityCondition {

    private int amount = 0;
    private MainClass main = MainClass.getInstance();

    public ManaConditionChec(String line, MythicLineConfig config) {
        super(line);

        this.amount = config.getInteger("amount");
    }

    @Override
    public boolean check(AbstractEntity abstractEntity) {

        if(abstractEntity.isPlayer()){
            NewPlayer np = this.main.getPlayerByUUID(abstractEntity.getUniqueId());

            np.get_p().sendMessage("test" + (np.getMANA() >= this.amount) + this.amount);
            return np.getMANA() >= this.amount;
        }

        return false;
    }
}
