package fr.azgin.main.Commands;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ParticleTEst implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            NewPlayer np = this.main.getPlayer(p);

            p.sendMessage("mana:"+np.getMANA() + "xp:" + np.getXP() + "level:"+np.getLevel());
            p.sendMessage(p.getUniqueId().toString());


//            ParticleEffect.FLAME.display(p.getLocation());
//
//            Location pluslocation = new Location(p.getWorld(), p.getLocation().getX() + 5, p.getLocation().getY(), p.getLocation().getZ());
//            Location pluslocationx = new Location(p.getWorld(), p.getLocation().getX() + 5, p.getLocation().getY() + 2, p.getLocation().getZ());
//
//            new ParticleBuilder(ParticleEffect.FLAME, pluslocation)
//                    .setOffsetY(1f)
//                    .setSpeed(0.1f)
//                    .display();
//
//            ItemStack item = new ItemStack(Material.DIAMOND_AXE);
//            new ParticleBuilder(ParticleEffect.ITEM_CRACK, pluslocationx)
//                    .setParticleData(new ItemTexture(item))
//                    .display();

        }

        return true;
    }
}
