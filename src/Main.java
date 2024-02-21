import java.io.IOException;

public class Main {
    public static void main(String[] args) {

       try{
           GameMap map = new GameMap("map.txt");
           PathFinder finder = new PathFinder(map);
           String path = finder.findPath();
           System.out.println(path);
       }catch (IOException | CustomException.MapLoadingException | CustomException.MissingStartPointException |
               CustomException.MissingFinishPointException e){
           System.out.println("Failed to read the map file: " + e.getMessage());
       }
    }
}