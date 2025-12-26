package commands;
import org.example.*;

public interface Command {
    String description();
    void execute(Game game, Player player, String arg);
}
