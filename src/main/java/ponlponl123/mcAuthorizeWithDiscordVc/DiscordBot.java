package ponlponl123.mcAuthorizeWithDiscordVc;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DiscordBot extends ListenerAdapter {

    private final McAuthorizeWithDiscordVc plugin;
    public JDA client;

    public DiscordBot(McAuthorizeWithDiscordVc plugin) {
        this.plugin = plugin;
    }

    public void startBot() throws LoginException, InterruptedException {
        String token = plugin.getConfig().getString("discord-client-token"); // Replace with your bot token

        if (isTokenInvalid(token)) {
            System.out.println("McAuthorizeWithDiscordVc: Discord client token is empty or invalid!");
            return;
        }

        client = JDABuilder.createDefault(token) // Create bot instance with token
            .setActivity(Activity.playing("Minecraft Server")) // Set bot status
            .addEventListeners(this) // Register event listeners
            .build().awaitReady(); // Build the bot
    }

    @Override
    public void onReady(ReadyEvent event) { // Override onReady method
        System.out.println("Bot logged in as: " + event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        try {
            AudioChannel oldChannel = event.getOldValue();
            AudioChannel newChannel = event.getNewValue();
            System.out.println("Voice state update: oldChannel=" + oldChannel + ", newChannel=" + newChannel);

            if (oldChannel != null && newChannel == null) {
                ConfigurationSection allowedMembersSection = this.plugin.getConfig().getConfigurationSection("allowed-members");

                Map<String, String> allowedMembers = new HashMap<>();
                if (allowedMembersSection != null) {
                    for (String key : allowedMembersSection.getKeys(false)) {
                        String id = allowedMembersSection.getString(key + ".id"); // Get the "id" field
                        if (id != null) {
                            allowedMembers.put(key, id);
                        }
                    }
                }

                // find the player
                allowedMembers.forEach((key, value) -> {
                    if (value.equals(event.getEntity().getId())) {
                        Player player = this.plugin.getServer().getPlayer(key);
                        if (player != null) {
                            System.out.println("Scheduling kick for player: " + player.getName());
                            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                                player.kickPlayer("Cannot be authorize with discord");
                            });
                        }
                        return;
                    }
                });
            }
        } catch (IllegalStateException e) {
            System.err.println("An error occurred while processing voice state update: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean Authorization(Player player) {
        List<String> allowedServers = this.plugin.getConfig().getStringList("allowed-servers");
        ConfigurationSection allowedMembersSection = this.plugin.getConfig().getConfigurationSection("allowed-members");

        Map<String, String> allowedMembers = new HashMap<>();

        if (allowedMembersSection != null) {
            for (String key : allowedMembersSection.getKeys(false)) {
                String id = allowedMembersSection.getString(key + ".id"); // Get the "id" field
                if (id != null) {
                    allowedMembers.put(key, id);
                }
            }
        }

        String allowedPlayer = allowedMembers.get(player.getName());
        if (allowedPlayer != null && !allowedPlayer.isEmpty()) {
            for (String allowedServer : allowedServers) {
                System.out.println("Authorizing server: " + allowedServer + " for player: " + allowedPlayer + " by name: " + player.getName());
                Guild guild = client.getGuildById(allowedServer);
                if (guild == null) continue;
                Member member = guild.getMemberById(allowedPlayer);
                if (member == null) continue;
                if ( Objects.requireNonNull(member.getVoiceState()).getChannel() != null && member.getVoiceState().getChannel().getMembers().contains(member) )
                    return true;
            }
        }

        return false;
    }

    private boolean isTokenInvalid(String token) {
        return token == null || token.isEmpty() || token.equals("MY_DISCORD_CLIENT_TOKEN");
    }
}
