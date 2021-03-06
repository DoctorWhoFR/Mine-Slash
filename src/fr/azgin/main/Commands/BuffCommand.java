package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class BuffCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)){

            if(args.length == 1){
                Player p = Bukkit.getPlayer(args[0]);

                if(p!=null){

                    NewPlayer np = main.getPlayer(p);

                    if(!p.hasMetadata("buff_countdown")){
                        p.setMetadata("buff_countdown", new FixedMetadataValue(main, 1));

                        switch (np.getDieu()){
                            case "poseidon":
                                np.get_p().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (20*60)*15, 1));
                                break;
                            case "hades":
                                np.get_p().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (20*60)*15, 1));
                                break;
                            case "hephaistos":
                                np.get_p().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (20*60)*30, 1));
                                break;
                        }

                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> p.removeMetadata("buff_countdown", main), (20*60)*main.config.getInt("buff_countdown"));

                        np.sendCMessage("??7Vous venez d'obtenir votre buff.");

                    } else {
                        np.sendCMessage("??7Vous devez attendre ??d5minutes ??7avant de pouvoir reprendre les buffs");
                    }
                    return true;


                } else {
                    main.logger.warning("Une commande buff ?? ??tait effectuer sur un joueur inconnu, potentiellement une mauvaise configuration.");
                }
            }

        }


        return false;
    }
}
