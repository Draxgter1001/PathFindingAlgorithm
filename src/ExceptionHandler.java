
public class ExceptionHandler {
    public static void handleMapLoadingException(CustomException.MapLoadingException e){
        System.err.println("Failed to load the map: " + e.getMessage());
    }

    public static void handleMissingStartingPointException(CustomException.MissingStartPointException e){
        System.err.println("The starting point 'S' is missing in the map" + e.getMessage());
    }

    public static void handleMissingFinishPointException(CustomException.MissingFinishPointException e){
        System.err.println("The finishing point 'F' is missing in the map" + e.getMessage());
    }

}
