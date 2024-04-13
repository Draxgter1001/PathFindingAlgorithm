import java.util.LinkedList;
import java.util.Queue;

class PathNode {
    int x, y, stepCount;
    String path;

    public PathNode(int x, int y, int stepCount, String path) {
        this.x = x;
        this.y = y;
        this.stepCount = stepCount;
        this.path = path;
    }
}

public class PathFinder {
    private final GameMap gameMap;

    public PathFinder(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public String findPath() {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        String[] directionNames = {"Right", "Down", "Left", "Up"};
        boolean[][] visited = new boolean[gameMap.getHeight()][gameMap.getWidth()];
        Queue<PathNode> queue = new LinkedList<>();

        StringBuilder pathBuilder = new StringBuilder();
        int stepNumber = 1;
        pathBuilder.append(stepNumber++).append(". Start at (").append(gameMap.getStartY() + 1).append(",").append(gameMap.getStartX() + 1).append(")");

        queue.add(new PathNode(gameMap.getStartX(), gameMap.getStartY(), 0, "Start at (" + (gameMap.getStartY() + 1) + "," + (gameMap.getStartX() + 1) + ")"));
        visited[gameMap.getStartX()][gameMap.getStartY()] = true;

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            if (current.x == gameMap.getFinishX() && current.y == gameMap.getFinishY()) {
                String[] pathSteps = current.path.split("\n");
                for (int i = 1; i < pathSteps.length; i++) {
                    pathBuilder.append("\n").append(stepNumber++).append(". ").append(pathSteps[i]);
                }
                pathBuilder.append("\n").append(stepNumber).append(". Done!");
                return pathBuilder.toString();
            }

            for (int i = 0; i < directions.length; i++) {
                int newX = current.x;
                int newY = current.y;
                boolean reachedFinish = false;

                // Slide in the current direction until hitting a wall or rock
                while (isValidPosition(newX + directions[i][0], newY + directions[i][1])) {
                    newX += directions[i][0];
                    newY += directions[i][1];

                    // If the finish point is reached, stop sliding and return the path
                    if (newX == gameMap.getFinishX() && newY == gameMap.getFinishY()) {
                        reachedFinish = true;
                        break;
                    }
                }

                if (!visited[newX][newY]) {
                    visited[newX][newY] = true;
                    String newPath = current.path + "\nMove " + directionNames[i] + " to (" + (newY + 1) + "," + (newX + 1) + ")";
                    queue.add(new PathNode(newX, newY, current.stepCount + 1, newPath));

                    // If the finish point is reached, return the path immediately
                    if (reachedFinish) {
                        String[] pathSteps = newPath.split("\n");
                        for (int j = 1; j < pathSteps.length; j++) {
                            pathBuilder.append("\n").append(stepNumber++).append(". ").append(pathSteps[j]);
                        }
                        pathBuilder.append("\n").append(stepNumber).append(". Done!");
                        return pathBuilder.toString();
                    }
                }
            }
        }

        return "No path found.";
    }

    // Check if the next position is within the map and not blocked
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < gameMap.getHeight() && y >= 0 && y < gameMap.getWidth() && gameMap.getMap().get(x)[y] != '0';
    }
}