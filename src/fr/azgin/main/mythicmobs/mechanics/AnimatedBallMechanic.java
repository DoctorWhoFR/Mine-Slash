package fr.azgin.main.mythicmobs.mechanics;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import fr.azgin.main.MainClass;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.Particle;

import java.util.Collection;

public class AnimatedBallMechanic extends SkillMechanic implements ITargetedEntitySkill {

    MainClass main = MainClass.getInstance();

    boolean autoOrient = true;
    Float size = 1F;
    String particle ="";


    public AnimatedBallMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);


        this.autoOrient = mlc.getBoolean("autoorient");
        this.size = mlc.getFloat("size");
        this.particle = mlc.getString("particle");

    }


    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        Collection<AbstractEntity> collections = skillMetadata.getEntityTargets();
        AbstractEntity target = (AbstractEntity) collections.toArray()[0];

        EffectManager em = new EffectManager(main);
        AnimatedBallEffect mechanic = new AnimatedBallEffect(em);
        mechanic.autoOrient = this.autoOrient;
        mechanic.size = this.size;
        mechanic.setEntity(abstractEntity.getBukkitEntity());
        mechanic.particle = Particle.valueOf(this.particle);
        mechanic.run();


        return false;
    }


}
