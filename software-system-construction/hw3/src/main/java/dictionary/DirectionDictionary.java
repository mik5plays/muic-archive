package dictionary;

import enums.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DirectionDictionary implements Dictionary<Direction> {

    private Map<String, Direction> directionMap;

    public DirectionDictionary() {
        directionMap = new HashMap<>();

        directionMap.put("north", Direction.NORTH);
        directionMap.put("south", Direction.SOUTH);
        directionMap.put("east", Direction.EAST);
        directionMap.put("west", Direction.WEST);
        directionMap.put("n", Direction.NORTH);
        directionMap.put("s", Direction.SOUTH);
        directionMap.put("e", Direction.EAST);
        directionMap.put("w", Direction.WEST);

    }

    public Direction lookup(String direction) {
        Direction dir = directionMap.get(direction.toLowerCase());
        return Objects.requireNonNullElse(dir, Direction.UNKNOWN);
    }

}
