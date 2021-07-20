package fr.azgin.main.mythicmobs.mechanics;

import de.slikey.effectlib.EffectManager;
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

public class TextParticleMechanic extends SkillMechanic implements ITargetedEntitySkill {

    MainClass main = MainClass.getInstance();

    String particle;
    String color;
    String text = "Salut";
    Integer offsetY = 0;
    Integer offsetX = 0;
    Integer offsetZ = 0;
    Integer duration = 0;
    String sound = "";

    public TextParticleMechanic(String skill, MythicLineConfig mlc) {
        super(skill, mlc);

        this.text = mlc.getString("text");
        this.offsetX = mlc.getInteger("offsetX");
        this.offsetY = mlc.getInteger("offsetY");
        this.particle = mlc.getString("particle");
        this.duration = mlc.getInteger("duration");

    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        EffectManager em = new EffectManager(main);
        TextEffect textEffect = new TextEffect(em);
        textEffect.text = this.text;
        textEffect.particleOffsetY = this.offsetY;
        textEffect.particleOffsetX = this.offsetX;
        textEffect.particleOffsetZ = this.offsetZ;
        textEffect.particle = Particle.valueOf(this.particle);
        textEffect.duration = this.duration;
        textEffect.setEntity(abstractEntity.getBukkitEntity());
        textEffect.run();

        return false;
    }


}
