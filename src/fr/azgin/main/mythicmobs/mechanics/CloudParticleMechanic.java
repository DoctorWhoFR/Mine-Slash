package fr.azgin.main.mythicmobs.mechanics;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.TextEffect;
import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.Color;
import org.bukkit.Particle;

public class CloudParticleMechanic extends SkillMechanic implements ITargetedEntitySkill {

    Float particlesize;
    MainClass main = MainClass.getInstance();

    Integer offsetY = 0;
    Integer offsetX = 0;
    Integer offsetZ = 0;
    String cloudParticle = "";
    String particle = "";
    Integer duration = (20*5); // 20 tick = 1s;
    Float cloudSize = 0F;
    Float yOffset = 0F;

    public CloudParticleMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);

        this.offsetX = mlc.getInteger("offsetX");
        this.offsetY = mlc.getInteger("offsetY");
        this.offsetZ = mlc.getInteger("offsetZ");
        this.cloudParticle = mlc.getString("cloudparticle");
        this.particle = mlc.getString("particle");
        this.particlesize = mlc.getFloat("particlesize");
        this.cloudSize = mlc.getFloat("cloudsize");
        this.yOffset = mlc.getFloat("yOffset");

    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {



        EffectManager em = new EffectManager(main);
        CloudEffect effect = new CloudEffect(em);
        effect.cloudParticle = Particle.valueOf(this.cloudParticle);
        effect.cloudSize = this.cloudSize;
        effect.particleSize = this.particlesize;
        effect.yOffset = this.yOffset;
        effect.particleOffsetX = this.offsetX;
        effect.particleOffsetY = this.offsetY;
        effect.particleOffsetZ = this.offsetZ;
        effect.mainParticle = Particle.valueOf(this.particle);
        effect.setEntity(abstractEntity.getBukkitEntity());
        effect.duration = this.duration;
        effect.run();

        return false;
    }


}
