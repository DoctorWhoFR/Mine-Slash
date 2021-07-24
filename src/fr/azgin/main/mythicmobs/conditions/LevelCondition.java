package fr.azgin.main.mythicmobs.conditions;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import org.bukkit.Bukkit;
import org.bukkit.metadata.FixedMetadataValue;

public class LevelCondition extends SkillCondition implements IEntityCondition {


    private final Integer min_level;
    private final MainClass main = MainClass.getInstance();

    public LevelCondition(String line, MythicLineConfig config) {
        super(line);

        this.min_level = config.getInteger("level");
    }

    @Override
    public boolean check(AbstractEntity abstractEntity) {

        if(abstractEntity.isPlayer()){
            NewPlayer np = this.main.getPlayerByUUID(abstractEntity.getUniqueId());
            Integer level = np.getLevel();

            if(level > min_level){

                if(!np.get_p().hasMetadata("level_skill_countdown")){
                    np.get_p().setMetadata("level_skill_countdown", new FixedMetadataValue(main, "1"));
                    np.get_p().sendMessage("Vous n'avez pas le niveau requis pour utilisez cette compétence !");

                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> np.get_p().removeMetadata("level_skill_countdown", main), 20*10);
                }
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

}
