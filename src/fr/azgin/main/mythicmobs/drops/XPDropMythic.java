package fr.azgin.main.mythicmobs.drops;

import fr.azgin.main.MainClass;
import fr.azgin.main.core.loading.Model.NewPlayer;
import io.lumine.xikage.mythicmobs.adapters.AbstractPlayer;
import io.lumine.xikage.mythicmobs.drops.*;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;

public class XPDropMythic extends Drop implements IIntangibleDrop {

    int amount = 10;

    MainClass main = MainClass.getInstance();

    public XPDropMythic(String line, MythicLineConfig config) {
        super(line, config);

        int test = config.getInteger("amountd");
        this.main.getLogger().info("XP " + test);
        this.amount = test;
    }

    public XPDropMythic(String line, MythicLineConfig config, double amount) {
        super(line, config, amount);
    }

    @Override
    public void giveDrop(AbstractPlayer abstractPlayer, DropMetadata dropMetadata) {
        if(abstractPlayer.isOnline()){
            this.main.getLogger().info(dropMetadata.getAmount() + " " + abstractPlayer.getName() + abstractPlayer.getUniqueId());
            NewPlayer np = this.main.getPlayerByUUID(abstractPlayer.getUniqueId());
            np.addXP(this.amount);
            this.main.getLogger().info("Added game");
        }
    }

}
