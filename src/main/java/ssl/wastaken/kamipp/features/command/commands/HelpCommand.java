package ssl.wastaken.kamipp.features.command.commands;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.features.command.Command;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("commands");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("You can use following commands: ");
        for (Command command : Kami.commandManager.getCommands()) {
            HelpCommand.sendMessage(Kami.commandManager.getPrefix() + command.getName());
        }
    }
}

