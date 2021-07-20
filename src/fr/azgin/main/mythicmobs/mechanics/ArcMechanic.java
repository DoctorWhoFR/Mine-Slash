package fr.azgin.main.mythicmobs.mechanics;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.ArcEffect;
import fr.azgin.main.MainClass;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.Particle;

import java.util.Collection;

public class ArcMechanic extends SkillMechanic implements ITargetedEntitySkill {

    MainClass main = MainClass.getInstance();

    Float Height = 2F;
    Boolean autoOrient = false;
    Integer Period = 0;


    public ArcMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);

        this.Height = mlc.getFloat("height");
        this.autoOrient = mlc.getBoolean("autoOrient");
        this.Period = mlc.getInteger("period");

    }


    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        EffectManager em = new EffectManager(main);
        ArcEffect mechanic = new ArcEffect(em);
        mechanic.setEntity(abstractEntity.getBukkitEntity());
        mechanic.setTargetEntity(abstractEntity.getBukkitEntity());
        mechanic.setTargetLocation(abstractEntity.getBukkitEntity().getLocation());
        mechanic.height = this.Height;
        mechanic.autoOrient = this.autoOrient;
        mechanic.period = this.Period;
        mechanic.run();


        return false;
    }


}
