package fr.azgin.main.Commands;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import fr.azgin.main.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Date;

public class BugCommand implements CommandExecutor {

    MainClass main = MainClass.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        StringBuilder final_text = new StringBuilder();

        if(sender instanceof Player){

            Player p = (Player) sender;


            if(!(p.hasMetadata("bug_countdown"))){

                if(args.length >= 1) {

                    p.setMetadata("bug_countdown", new FixedMetadataValue(main, "1"));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> p.removeMetadata("bug_countdown", main), (20*60)*5);

                    for(String word : args){
                        final_text.append(" ").append(word);
                    }


                    // Using the builder
                    WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/866267817057714177/QnYIu90tbqqeTujZDjBHUVmikk-oSCPsNiiRGsnlTQ3tg6DMjgOGeS2IL-R-Api59TA0"); // or id, token
                    builder.setThreadFactory((job) -> {
                        Thread thread = new Thread(job);
                        thread.setName("webhook_"+p.getUniqueId().toString());
                        thread.setDaemon(true);
                        return thread;
                    });
                    builder.setWait(true);
                    WebhookClient client = builder.build();

                    Date date = new Date();
                    long time = date.getTime();
                    Timestamp ts = new Timestamp(time);

                    WebhookEmbed embed = new WebhookEmbedBuilder()
                            .setColor(0xFF00EE)
                            .setAuthor(new WebhookEmbed.EmbedAuthor("BUG REPORTER", "", ""))
                            .setTitle(new WebhookEmbed.EmbedTitle("BUG REPORT", "https://www.mineslash.net/"))
                            .setDescription("**NOUVEAU BUG REPORT** \nDATE: " + ts)
                            .addField(new WebhookEmbed.EmbedField(true, "**Joueur:**",p.getName()))
                            .addField(new WebhookEmbed.EmbedField(true, "**Serveur:**", p.getServer().getName()))
                            .addField(new WebhookEmbed.EmbedField(true, "**Bugs:**", final_text.toString()))
                            .build();

                    client.send(embed);

                    client.close();


                    p.sendMessage(final_text.toString());

                    return true;
                }

            } else {
                p.sendMessage("Vous devez attendre 5minutes avant de renvoyé une demande d'aide.");
                return true;
            }

        }


        return false;
    }
}
