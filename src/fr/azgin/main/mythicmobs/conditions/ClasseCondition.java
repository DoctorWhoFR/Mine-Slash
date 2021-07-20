package fr.azgin.main.mythicmobs.conditions;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

public class ClasseCondition extends SkillCondition implements IEntityCondition {


    private String classe;
    private MainClass main = MainClass.getInstance();

    public ClasseCondition(String line, MythicLineConfig config) {
        super(line);

        this.classe = config.getString("classe");
    }

    @Override
    public boolean check(AbstractEntity abstractEntity) {

        if(abstractEntity.isPlayer()){
            NewPlayer np = this.main.getPlayerByUUID(abstractEntity.getUniqueId());
            String classe = np.getClasse();

            if(!classe.equals(this.classe)){

                if(!np.get_p().hasMetadata("classe_skill_countdown")){
                    np.get_p().setMetadata("classe_skill_countdown", new FixedMetadataValue(main, "1"));
                    np.get_p().sendMessage("Vous n'avez pas la classe requise."+ " Classe requise:" + this.classe + "| Classe: " + np.getClasse());

                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> np.get_p().removeMetadata("classe_skill_countdown", main), 20*10);
                }
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

}
