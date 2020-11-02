import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.net.MalformedURLException;

public class onJoin implements Listener {
    final String url;
    final String message;

    public onJoin(String url, String message) {
        this.url = url;
        this.message = message;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        DiscordWebhook wh = new DiscordWebhook(url);
        String messgage = String.format(message, event.getPlayer().getDisplayName());
        wh.setContent(messgage);
        try {
            wh.execute();
        } catch (MalformedURLException e) {
            System.out.println("[MinecraftDiscordWebhook] Invalid webhook URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
