package ponlponl123.mcAuthorizeWithDiscordVc;

import javax.security.auth.login.LoginException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class McAuthorizeWithDiscordVc extends JavaPlugin implements Listener {
    public DiscordBot bot;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("McAuthorizeWithDiscordVc Plugin Enabled!");

        saveDefaultConfig();

        // Setting up Discord Client
        getLogger().info("Logging in to Discord...");
        bot = new DiscordBot(this);
        try {
            bot.startBot();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("McAuthorizeWithDiscordVc Plugin Disabled!");
    }
}
