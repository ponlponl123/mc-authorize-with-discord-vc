package ponlponl123.mcAuthorizeWithDiscordVc.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import ponlponl123.mcAuthorizeWithDiscordVc.McAuthorizeWithDiscordVc;
import ponlponl123.mcAuthorizeWithDiscordVc.SubCommand;

public class EditCommand implements SubCommand {
    private final McAuthorizeWithDiscordVc plugin;

    public EditCommand(McAuthorizeWithDiscordVc plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("mcawd.admin")) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return;
        }

        if (args.length < 3) {
            sender.sendMessage("§cUsage: /mcawd edit <player> <new_id>");
            return;
        }

        String playerName = args[1];
        String newId = args[2];

        FileConfiguration config = plugin.getConfig();

        if (!config.contains("allowed-members." + playerName)) {
            sender.sendMessage("§cPlayer does not exist. Use /mcawd add to add them.");
            return;
        }

        config.set("allowed-members." + playerName + ".id", newId);
        plugin.saveConfig();
        sender.sendMessage("§aUpdated " + playerName + " ID to: " + newId);
    }
}
