package ponlponl123.mcAuthorizeWithDiscordVc.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import ponlponl123.mcAuthorizeWithDiscordVc.McAuthorizeWithDiscordVc;
import ponlponl123.mcAuthorizeWithDiscordVc.SubCommand;

public class AddCommand implements SubCommand {
    private final McAuthorizeWithDiscordVc plugin;

    public AddCommand(McAuthorizeWithDiscordVc plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("mcawd.admin")) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return;
        }

        if (args.length < 3) {
            sender.sendMessage("§cUsage: /mcawd add <player> <id>");
            return;
        }

        String playerName = args[1];
        String id = args[2];

        FileConfiguration config = plugin.getConfig();

        if (config.contains("allowed-members." + playerName)) {
            sender.sendMessage("§cPlayer already exists. Use /mcawd edit to update.");
            return;
        }

        config.set("allowed-members." + playerName + ".id", id);
        plugin.saveConfig();
        sender.sendMessage("§aAdded " + playerName + " with ID: " + id);
    }
}
