import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            long startTime = System.nanoTime();
            GameMap map = new GameMap("puzzle_160.txt");
            PathFinder finder = new PathFinder(map);
            String path = finder.findPath();
            System.out.println(path);
            long endTime = System.nanoTime();
            // Calculate the elapsed time
            long elapsedTime = endTime - startTime;
            // Convert nanoseconds to milliseconds for readability
            double milliseconds = (double) elapsedTime / 1_000_000;

            // Print the elapsed time
            System.out.println("Elapsed Time: " + milliseconds + " milliseconds");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (CustomException.MapLoadingException e) {
            ExceptionHandler.handleMapLoadingException();
        } catch (CustomException.MissingStartPointException e) {
            ExceptionHandler.handleMissingStartingPointException();
        } catch (CustomException.MissingFinishPointException e) {
            ExceptionHandler.handleMissingFinishPointException();
        } catch (CustomException.MultipleStartPointsException e) {
            ExceptionHandler.handleMultipleStartPointsException();
        } catch (CustomException.MultipleFinishPointsException e) {
            ExceptionHandler.handleMultipleFinishPointsException();
        }
    }
}