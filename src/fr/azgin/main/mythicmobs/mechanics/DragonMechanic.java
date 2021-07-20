package fr.azgin.main.mythicmobs.mechanics;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ArcEffect;
import de.slikey.effectlib.effect.DragonEffect;
import fr.azgin.main.MainClass;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;

public class DragonMechanic extends SkillMechanic implements ITargetedEntitySkill {

    MainClass main = MainClass.getInstance();



    public DragonMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);


    }


    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        EffectManager em = new EffectManager(main);
        DragonEffect mechanic = new DragonEffect(em);
        mechanic.setEntity(abstractEntity.getBukkitEntity());
        mechanic.setTargetEntity(abstractEntity.getBukkitEntity());
        mechanic.setTargetLocation(abstractEntity.getBukkitEntity().getLocation());
        mechanic.run();


        return false;
    }


}
