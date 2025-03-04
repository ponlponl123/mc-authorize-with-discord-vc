package ponlponl123.mcAuthorizeWithDiscordVc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ponlponl123.mcAuthorizeWithDiscordVc.Commands.ReloadCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandHandler(McAuthorizeWithDiscordVc plugin) {
        subCommands.put("reload", new ReloadCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /mcawd <subcommand>");
            return true;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage("§cUnknown subcommand.");
        }
        return true;
    }
}