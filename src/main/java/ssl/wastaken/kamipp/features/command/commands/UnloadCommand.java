package ssl.wastaken.kamipp.features.command.commands;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.features.command.Command;

public class UnloadCommand
        extends Command {
    public UnloadCommand() {
        super("unload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        Kami.unload(true);
    }
}

