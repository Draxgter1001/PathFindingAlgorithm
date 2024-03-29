
public class ExceptionHandler {
    public static void handleMapLoadingException(){
        System.err.println("Failed to load the map");
    }

    public static void handleMissingStartingPointException(){
        System.err.println("The starting point 'S' is missing in the map");
    }

    public static void handleMissingFinishPointException(){
        System.err.println("The finishing point 'F' is missing in the map");
    }
}
