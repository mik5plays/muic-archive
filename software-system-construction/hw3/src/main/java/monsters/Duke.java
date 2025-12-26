package monsters;

import enums.MonsterType;
import objects.*;
import org.example.Game;
import org.example.Player;
import org.example.Room;

public class Duke extends Monster {
    public Duke() {
        super("Duke",
                "The strongest of them all. Only the most experienced are worthy to defeat it.",
                MonsterType.DUKE,
                null,
                0,
                800,
                800,
                15,
                1,
                0.1,
                2);
    }

    @Override
    public void onKill(Game game, Player player) {
        Room room = player.getPosition();
        System.out.println("Congratulations, you killed the Duke!");
        game.onFinish();
    }
}
