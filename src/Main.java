import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            GameMap map = new GameMap("map.txt");
            PathFinder finder = new PathFinder(map);
            String path = finder.findPath();
            System.out.println(path);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (CustomException.MapLoadingException e) {
            ExceptionHandler.handleMapLoadingException();
        } catch (CustomException.MissingStartPointException e) {
            ExceptionHandler.handleMissingStartingPointException();
        } catch (CustomException.MissingFinishPointException e) {
            ExceptionHandler.handleMissingFinishPointException();
        }
    }
}