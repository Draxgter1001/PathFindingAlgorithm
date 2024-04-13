import java.util.PriorityQueue;

class PathNode implements Comparable<PathNode> {
    int x, y, stepCount;
    double distanceToGoal;
    String path;

    public PathNode(int x, int y, int stepCount, String path, GameMap gameMap) {
        this.x = x;
        this.y = y;
        this.stepCount = stepCount;
        this.path = path;
        this.distanceToGoal = calculateDistanceToGoal(gameMap);
    }

    // Calculates the Manhattan distance to the goal
    private double calculateDistanceToGoal(GameMap gameMap) {
        return Math.abs(x - gameMap.getFinishX()) + Math.abs(y - gameMap.getFinishY());
    }

    // Compares nodes by their estimated distance to the goal (heuristic)
    @Override
    public int compareTo(PathNode other) {
        return Double.compare(stepCount + distanceToGoal, other.stepCount + other.distanceToGoal);
    }
}

public class PathFinder {
    private final GameMap GAMEMAP;

    public PathFinder(GameMap gameMap) {
        this.GAMEMAP = gameMap;
    }

    public String findPath() {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        String[] directionNames = {"Right", "Down", "Left", "Up"};
        boolean[][] visited = new boolean[GAMEMAP.getHeight()][GAMEMAP.getWidth()];
        PriorityQueue<PathNode> queue = new PriorityQueue<>();

        StringBuilder pathBuilder = new StringBuilder();
        int stepNumber = 1;
        pathBuilder.append(stepNumber++).append(". Start at (").append(GAMEMAP.getStartY() + 1).append(",").append(GAMEMAP.getStartX() + 1).append(")");

        queue.add(new PathNode(GAMEMAP.getStartX(), GAMEMAP.getStartY(), 0, "Start at (" + (GAMEMAP.getStartY() + 1) + "," + (GAMEMAP.getStartX() + 1) + ")", GAMEMAP));
        visited[GAMEMAP.getStartX()][GAMEMAP.getStartY()] = true;

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            if (current.x == GAMEMAP.getFinishX() && current.y == GAMEMAP.getFinishY()) {
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

                // Slide in the current direction until hitting a wall or rock
                while (isValidMove(newX + directions[i][0], newY + directions[i][1], visited)) {
                    newX += directions[i][0];
                    newY += directions[i][1];
                }

                if (!visited[newX][newY]) {
                    visited[newX][newY] = true;
                    String newPath = current.path + "\nMove " + directionNames[i] + " to (" + (newY + 1) + "," + (newX + 1) + ")";
                    queue.add(new PathNode(newX, newY, current.stepCount + 1, newPath, GAMEMAP));
                }
            }
        }

        return "No path found.";
    }

    // Check if the next move is within the map, not visited, and not blocked
    private boolean isValidMove(int x, int y, boolean[][] visited) {
        return x >= 0 && x < GAMEMAP.getHeight() && y >= 0 && y < GAMEMAP.getWidth() && !visited[x][y] && GAMEMAP.getMap().get(x)[y] != '0';
    }
}
