import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private final List<char[]> map = new ArrayList<>();
    private int startX = -1, startY = -1; //Starting Position in the Map
    private int finishX = -1, finishY = -1; //Finish Position in the Map

    public GameMap(String filePath) throws IOException, CustomException.MapLoadingException, CustomException.MissingStartPointException, CustomException.MissingFinishPointException, CustomException.MultipleStartPointsException, CustomException.MultipleFinishPointsException {
        loadMap(filePath);
        if (startX == -1 || startY == -1) throw new CustomException.MissingStartPointException("Map does not contain a starting point 'S'");
        if (finishX == -1 || finishY == -1) throw new CustomException.MissingFinishPointException("Map does not contain a finish point 'F'");
    }

    private void loadMap(String filePath) throws IOException, CustomException.MapLoadingException, CustomException.MultipleStartPointsException, CustomException.MultipleFinishPointsException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                char[] row = line.toCharArray();
                map.add(row);
                int rowIndex = map.size() - 1;
                for (int i = 0; i < row.length; i++) {
                    if (row[i] == 'S') {
                        if (startX != -1 || startY != -1) {
                            throw new CustomException.MultipleStartPointsException("Map contains multiple starting points 'S'");
                        }
                        startX = rowIndex;
                        startY = i;
                    } else if (row[i] == 'F') {
                        if (finishX != -1 || finishY != -1) {
                            throw new CustomException.MultipleFinishPointsException("Map contains multiple finish points 'F'");
                        }
                        finishX = rowIndex;
                        finishY = i;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new CustomException.MapLoadingException("Map file not found: " + filePath);
        }
    }

    public List<char[]> getMap() {
        return map;
    }

    public int getStartX() { return startX; }

    public int getStartY() {
        return startY;
    }

    public int getFinishX() {
        return finishX;
    }

    public int getFinishY() {
        return finishY;
    }

    public int getHeight() {
        return map.size();
    }

    public int getWidth() {
        return map.isEmpty() ? 0 : map.get(0).length;
    }

}
