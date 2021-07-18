package fr.azgin.main.mythicmobs;

import fr.azgin.main.MainClass;
import fr.azgin.main.mythicmobs.conditions.ClasseCondition;
import fr.azgin.main.mythicmobs.conditions.ManaConditionChec;
import fr.azgin.main.mythicmobs.drops.XPDropMythic;
import fr.azgin.main.mythicmobs.mechanics.ManaWithdrawMechanic;
import fr.azgin.main.mythicmobs.mechanics.PlaySoundMechanic;
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
            this.mainClass.getLogger().info("-- Registered XP drop!");
        }
    }

    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event)	{

        if(event.getMechanicName().equalsIgnoreCase("MANAWITHDRAW"))	{
            SkillMechanic mechanic = new ManaWithdrawMechanic("manawithdraw", event.getConfig());
            event.register(mechanic);
            this.mainClass.getLogger().info("-- Registered WITHER mechanic!");
        } else if (event.getMechanicName().equalsIgnoreCase("PLAYSSOUND")){
            SkillMechanic mechanic = new PlaySoundMechanic("PLAYSSOUND", event.getConfig());
            event.register(mechanic);
            this.mainClass.getLogger().info("-- Registered PLAYSSOUND mechanic!");
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
            this.mainClass.getLogger().info("-- Registered InVehicle dondition!");
        } else if(event.getConditionName().equalsIgnoreCase("CLASSECHECK")) {
            SkillCondition condition = new ClasseCondition("CLASSECHECK", event.getConfig());
            event.register(condition);
            this.mainClass.getLogger().info("-- registered class check condition");
        }
    }


}
