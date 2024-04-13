import java.util.LinkedList;
import java.util.Queue;

class PathNode {
    int x, y, stepCount;
    String path;

    // Constructor for PathNode
    public PathNode(int x, int y, int stepCount, String path) {
        this.x = x;
        this.y = y;
        this.stepCount = stepCount;
        this.path = path;
    }
}

public class PathFinder {
    private final GameMap gameMap;

    // Constructor for PathFinder
    public PathFinder(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    // Find the path from the starting position to the finish position using BFS
    public String findPath() {
        // Define the four possible directions: Right, Down, Left, Up
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        String[] directionNames = {"Right", "Down", "Left", "Up"};

        // Create a 2D boolean array to keep track of visited positions
        boolean[][] visited = new boolean[gameMap.getHeight()][gameMap.getWidth()];

        // Create a queue to store the positions to be explored
        Queue<PathNode> queue = new LinkedList<>();

        // Create a StringBuilder to build the final path
        StringBuilder pathBuilder = new StringBuilder();
        int stepNumber = 1;
        pathBuilder.append(stepNumber++).append(". Start at (").append(gameMap.getStartY() + 1).append(",").append(gameMap.getStartX() + 1).append(")");

        // Add the starting position to the queue and mark it as visited
        queue.add(new PathNode(gameMap.getStartX(), gameMap.getStartY(), 0, "Start at (" + (gameMap.getStartY() + 1) + "," + (gameMap.getStartX() + 1) + ")"));
        visited[gameMap.getStartX()][gameMap.getStartY()] = true;

        // Continue the BFS until the queue is empty
        while (!queue.isEmpty()) {
            // Remove the front node from the queue
            PathNode current = queue.poll();

            // If the current position is the finish position, build the final path and return it
            if (current.x == gameMap.getFinishX() && current.y == gameMap.getFinishY()) {
                String[] pathSteps = current.path.split("\n");
                for (int i = 1; i < pathSteps.length; i++) {
                    pathBuilder.append("\n").append(stepNumber++).append(". ").append(pathSteps[i]);
                }
                pathBuilder.append("\n").append(stepNumber).append(". Done!");
                return pathBuilder.toString();
            }

            // Iterate over the four possible directions: Right, Down, Left, Up
            for (int i = 0; i < directions.length; i++) {
                int newX = current.x;
                int newY = current.y;
                boolean reachedFinish = false;

                // Slide in the current direction until hitting a wall or rock
                while (isValidPosition(newX + directions[i][0], newY + directions[i][1])) {
                    newX += directions[i][0];
                    newY += directions[i][1];

                    // If the finish point is reached, stop sliding and mark it as reached
                    if (newX == gameMap.getFinishX() && newY == gameMap.getFinishY()) {
                        reachedFinish = true;
                        break;
                    }
                }

                // If the new position has not been visited before
                if (!visited[newX][newY]) {
                    visited[newX][newY] = true;

                    // Create a new path by appending the current direction and position to the existing path
                    String newPath = current.path + "\nMove " + directionNames[i] + " to (" + (newY + 1) + "," + (newX + 1) + ")";

                    // Add the new position to the queue for further exploration
                    queue.add(new PathNode(newX, newY, current.stepCount + 1, newPath));

                    // If the finish point is reached, build the final path and return it
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

        // If no path is found, return "No path found."
        return "No path found.";
    }

    // Check if the next position is within the map and not blocked
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < gameMap.getHeight() && y >= 0 && y < gameMap.getWidth() && gameMap.getMap().get(x)[y] != '0';
    }
}