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

        StringBuilder pathBuilder = new StringBuilder();
        int moveCount = 1;
        pathBuilder.append(moveCount++).append(". Start at (").append(gameMap.getStartY() + 1).append(",").append(gameMap.getStartX() + 1).append(")");

        queue.add(new PathNode(gameMap.getStartX(), gameMap.getStartY(), 0, "Start at (" + (gameMap.getStartY() + 1) + "," + (gameMap.getStartX() + 1) + ")"));
        visited[gameMap.getStartX()][gameMap.getStartY()] = true;

        boolean pathFound = false;

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            if (current.x == gameMap.getFinishX() && current.y == gameMap.getFinishY()) {
                pathFound = true;
                String[] pathSteps = current.path.split("\n");
                for (int i = 1; i < pathSteps.length; i++) {
                    pathBuilder.append("\n").append(moveCount++).append(". ").append(pathSteps[i]);
                }
                pathBuilder.append("\n").append(moveCount++).append(". Done!");
                break;
            }

            for (int i = 0; i < directions.length; i++) {
                int[] dir = directions[i];
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (isValidMove(newX, newY, visited)) {
                    visited[newX][newY] = true;
                    String newPath = current.path + "\nMove " + directionNames[i] + " to (" + (newY + 1) + "," + (newX + 1) + ")";
                    queue.add(new PathNode(newX, newY, current.steps + 1, newPath));
                }
            }
        }

        return pathFound ? pathBuilder.toString() : "No path found.";
    }

    private boolean isValidMove(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= gameMap.getHeight() || y < 0 || y >= gameMap.getWidth() || y >= gameMap.getMap().get(x).length || gameMap.getMap().get(x)[y] == '0' || visited[x][y]) {
            return false;
        }
        return true;
    }
}