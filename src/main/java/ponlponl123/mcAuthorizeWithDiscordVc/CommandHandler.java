package ponlponl123.mcAuthorizeWithDiscordVc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ponlponl123.mcAuthorizeWithDiscordVc.Commands.AddCommand;
import ponlponl123.mcAuthorizeWithDiscordVc.Commands.EditCommand;
import ponlponl123.mcAuthorizeWithDiscordVc.Commands.ReloadCommand;
import ponlponl123.mcAuthorizeWithDiscordVc.Commands.RemoveCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandHandler(McAuthorizeWithDiscordVc plugin) {
        subCommands.put("reload", new ReloadCommand(plugin));
        subCommands.put("add", new AddCommand(plugin));
        subCommands.put("remove", new RemoveCommand(plugin));
        subCommands.put("edit", new EditCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /mcawd <reload|add|remove|edit>");
            return true;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage("§cUnknown subcommand. Use: reload, add, remove, or edit.");
        }
        return true;
    }
}