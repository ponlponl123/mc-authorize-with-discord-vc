package ponlponl123.mcAuthorizeWithDiscordVc.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import ponlponl123.mcAuthorizeWithDiscordVc.McAuthorizeWithDiscordVc;
import ponlponl123.mcAuthorizeWithDiscordVc.SubCommand;

public class RemoveCommand implements SubCommand {
    private final McAuthorizeWithDiscordVc plugin;

    public RemoveCommand(McAuthorizeWithDiscordVc plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("mcawd.admin")) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUsage: /mcawd remove <player>");
            return;
        }

        String playerName = args[1];
        FileConfiguration config = plugin.getConfig();

        if (!config.contains("allowed-members." + playerName)) {
            sender.sendMessage("§cPlayer does not exist.");
            return;
        }

        config.set("allowed-members." + playerName, null);
        plugin.saveConfig();
        sender.sendMessage("§aRemoved " + playerName + " from the allowed members list.");
    }
}
