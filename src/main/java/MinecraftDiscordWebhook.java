import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Entry point for the template plugin. You should edit
 * this comment by explaining the main purpose of your
 * plugin
 *
 * You should also edit these tags below.
 *
 * @author alexpado
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class MinecraftDiscordWebhook extends JavaPlugin {
    private String url;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Copy the config.yml in the plugin configuration folder if it doesn't exists.
        this.saveDefaultConfig();

        config = this.getConfig();
        url = config.getString("url");
        if (config.getBoolean("onServerStart")) {
            sendDiscordMessage(config.getString("startMessage"));
        }

        if (config.getBoolean("onJoin")) {
            String message = config.getString("joinMessage");
            getServer().getPluginManager().registerEvents(new onJoin(url, message), this);
        }

        if (config.getBoolean("onLeave")) {
            String message = config.getString("leaveMessage");
            getServer().getPluginManager().registerEvents(new onLeave(url, message), this);
        }
    }

    private void sendDiscordMessage(String message) {
        DiscordWebhook webhook = new DiscordWebhook(url);
        webhook.setContent(message);
        try {
            webhook.execute();
        } catch (MalformedURLException e) {
            System.out.println("[MinecraftDiscordWebhook] Invalid webhook URL");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (config.getBoolean("onServerStop")) {
            sendDiscordMessage(config.getString("stopMessage"));
        }
    }

}
