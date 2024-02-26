import java.util.LinkedList;
import java.util.Queue;

class PathNode {
    int x, y, steps;
    String path;

    public PathNode(int x, int y, int steps, String path) {
        this.x = x;
        this.y = y;
        this.steps = steps;
        this.path = path;
    }
}

public class PathFinder {
    private final GameMap gameMap;

    public PathFinder(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public String findPath() {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up
        String[] directionNames = {"Right", "Down", "Left", "Up"};
        boolean[][] visited = new boolean[gameMap.getHeight()][gameMap.getWidth()];
        Queue<PathNode> queue = new LinkedList<>();

        queue.add(new PathNode(gameMap.getStartX(), gameMap.getStartY(), 0, "Start at (" + gameMap.getStartX() + "," + gameMap.getStartY() + ")"));

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            if (current.x == gameMap.getFinishX() && current.y == gameMap.getFinishY()) {
                return current.path + "\nDone!";
            }

            for (int i = 0; i < directions.length; i++) {
                int[] dir = directions[i];
                int newX = current.x, newY = current.y;

                while (true) {
                    int checkX = newX + dir[0];
                    int checkY = newY + dir[1];

                    // Break if next move is out of bounds or hits a rock
                    if (checkX < 0 || checkX >= gameMap.getHeight() || checkY < 0 || checkY >= gameMap.getWidth() || checkY >= gameMap.getMap().get(checkX).length || gameMap.getMap().get(checkX)[checkY] == '0') {

                        break;
                    }
                    newX = checkX;
                    newY = checkY;

                    // Break if reaching the finish
                    if (gameMap.getMap().get(newX)[newY] == 'F') break;
                }

                // Prevent re-visiting the same spot
                if (!visited[newX][newY]) {
                    visited[newX][newY] = true;
                    String newPath = current.path + "\nMove " + directionNames[i] + " to (" + (newX + 1) + "," + (newY + 1) + ")";
                    queue.add(new PathNode(newX, newY, current.steps + 1, newPath));
                }
            }
        }

        return "No path found.";
    }
}
