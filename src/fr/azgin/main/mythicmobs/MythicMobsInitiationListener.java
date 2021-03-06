package fr.azgin.main.mythicmobs;

import fr.azgin.main.MainClass;
import fr.azgin.main.mythicmobs.conditions.ClasseCondition;
import fr.azgin.main.mythicmobs.conditions.LevelCondition;
import fr.azgin.main.mythicmobs.conditions.ManaConditionChec;
import fr.azgin.main.mythicmobs.drops.XPDropMythic;
import fr.azgin.main.mythicmobs.mechanics.*;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicConditionLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicDropLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.drops.Drop;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicMobsInitiationListener implements Listener {

    MainClass mainClass = MainClass.getInstance();

    /*
     * Registers all of the custom drops when MythicDropLoadEvent is called
     */
    @EventHandler
    public void onMythicDropLoad(MythicDropLoadEvent event)	{

       if(event.getDropName().equalsIgnoreCase("SPECIALXP")){
            Drop np = new XPDropMythic("SPECIALXP", event.getConfig());
            event.register(np);
        }
    }

    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event)	{

        if(event.getMechanicName().equalsIgnoreCase("MANAWITHDRAW"))	{
            SkillMechanic mechanic = new ManaWithdrawMechanic("manawithdraw", event.getConfig());
            event.register(mechanic);
        } else if (event.getMechanicName().equalsIgnoreCase("PLAYSSOUND")){
            SkillMechanic mechanic = new PlaySoundMechanic("PLAYSSOUND", event.getConfig());
            event.register(mechanic);
        } else if(event.getMechanicName().equalsIgnoreCase("TEXTEFFECT")){
            SkillMechanic mechanic = new TextParticleMechanic("TEXTEFFECT", event.getConfig());
            event.register(mechanic);
        }  else if(event.getMechanicName().equalsIgnoreCase("CLOUDEFFECT")){
            SkillMechanic mechanic = new CloudParticleMechanic("CLOUDEFFECT", event.getConfig());
            event.register(mechanic);
        } else if(event.getMechanicName().equalsIgnoreCase("ANIMATEDBALL")){
            SkillMechanic mechanic = new AnimatedBallMechanic("ANIMATEDBALL", event.getConfig());
            event.register(mechanic);
        } else if(event.getMechanicName().equalsIgnoreCase("ARCMECHANIC")){
            SkillMechanic mechanic = new ArcMechanic("ARCMECHANIC", event.getConfig());
            event.register(mechanic);
        } else if(event.getMechanicName().equalsIgnoreCase("DRAGONEFFECT")){
            SkillMechanic mechanic = new DragonMechanic("DRAGONEFFECT", event.getConfig());
            event.register(mechanic);
        }
    }



    /*
     * Registers all of the custom conditions when MythicConditionLoadEvent is called
     */
    @EventHandler
    public void onMythicConditionLoad(MythicConditionLoadEvent event)	{

        if(event.getConditionName().equalsIgnoreCase("MANACONDITION"))	{
            SkillCondition condition = new ManaConditionChec("MANACONDITION", event.getConfig());
            event.register(condition);
        } else if(event.getConditionName().equalsIgnoreCase("CLASSECONDITION")) {
            SkillCondition condition = new ClasseCondition("CLASSECONDITION", event.getConfig());
            event.register(condition);
        } else if(event.getConditionName().equalsIgnoreCase("LEVELCONDITION")) {
            SkillCondition condition = new LevelCondition("LEVELCONDITION", event.getConfig());
            event.register(condition);
        }
    }


}
