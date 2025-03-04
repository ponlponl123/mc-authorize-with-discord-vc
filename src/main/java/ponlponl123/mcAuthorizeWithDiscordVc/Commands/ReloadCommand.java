package ponlponl123.mcAuthorizeWithDiscordVc.Commands;

import org.bukkit.command.CommandSender;
import ponlponl123.mcAuthorizeWithDiscordVc.McAuthorizeWithDiscordVc;
import ponlponl123.mcAuthorizeWithDiscordVc.SubCommand;

public class ReloadCommand implements SubCommand {
    private final McAuthorizeWithDiscordVc plugin;

    public ReloadCommand(McAuthorizeWithDiscordVc plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("mcawd.admin")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return;
        }

        plugin.reloadConfig();
        sender.sendMessage("§aMyPlugin configuration reloaded!");
    }
}
