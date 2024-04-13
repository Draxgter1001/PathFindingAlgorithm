import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            GameMap map = new GameMap("puzzle_40.txt");
            PathFinder finder = new PathFinder(map);
            String path = finder.findPath();
            System.out.println(path);

        } catch (CustomException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}