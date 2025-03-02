package ponlponl123.mcAuthorizeWithDiscordVc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;
import java.util.logging.Level;

public class LoginListener implements Listener {

    private final McAuthorizeWithDiscordVc plugin;

    public LoginListener(McAuthorizeWithDiscordVc plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        // Debug log when user is logging in
        plugin.getLogger().log(Level.INFO, "Player " + event.getPlayer().getName() + " is logging in! with " + event.getHostname());

        boolean auth = this.plugin.bot.Authorization(event.getPlayer());

        if (!auth)
        {
            // Disallow player when not joined in discord voice chat
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Cannot be authorize with discord");
        }
    }
}
