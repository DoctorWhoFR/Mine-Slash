package fr.azgin.main.mythicmobs.conditions;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
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

                if(!np.get_p().getPersistentDataContainer().has(new NamespacedKey(main, "testing1"), PersistentDataType.STRING)){
                    np.get_p().getPersistentDataContainer().set(new NamespacedKey(main, "testing1"), PersistentDataType.STRING, "loved");
                    np.get_p().sendMessage("Vous n'avez pas la classe requise."+ " Classe requise:" + this.classe + "| Classe: " + np.getClasse());

                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> np.get_p().getPersistentDataContainer().remove(new NamespacedKey(main, "testing1")), 20*10);
                }
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

}
