import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private final List<char[]> map = new ArrayList<>(); // 2D list to represent the game map

    private int startX = -1, startY = -1; //Starting Position in the Map
    private int finishX = -1, finishY = -1; //Finish Position in the Map

    // Constructor for GameMap
    // Loads the map from the specified file path
    // Throws IOException if an I/O error occurs while reading the file
    // Throws CustomException if the map is invalid (missing starting or finish point)
    public GameMap(String filePath) throws IOException, CustomException {
        loadMap(filePath);
        if (startX == -1 || startY == -1) throw new CustomException("Map does not contain a starting point 'S'");
        if (finishX == -1 || finishY == -1) throw new CustomException("Map does not contain a finish point 'F'");
    }

    // Load the map from a file
    private void loadMap(String filePath) throws IOException, CustomException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read the map file line by line
            while ((line = reader.readLine()) != null) {
                char[] row = line.toCharArray();
                map.add(row);
                int rowIndex = map.size() - 1;
                // Iterate over each character in the row
                for (int i = 0; i < row.length; i++) {
                    // Check if the character is the starting point 'S'
                    if (row[i] == 'S') {
                        if (startX != -1 || startY != -1) {
                            throw new CustomException("Map contains multiple starting points 'S'");
                        }
                        // Set the starting position coordinates
                        startX = rowIndex;
                        startY = i;
                    } else if (row[i] == 'F') {
                        if (finishX != -1 || finishY != -1) {
                            throw new CustomException("Map contains multiple finish points 'F'");
                        }
                        finishX = rowIndex;
                        finishY = i;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // If the map file is not found, throw a custom exception
            throw new CustomException("Map file not found: " + filePath);
        }
    }

    // Returns the 2D list representing the game map
    public List<char[]> getMap() {
        return map;
    }

    // Getter for the starting X position
    public int getStartX() { return startX; }

    // Getter for the starting Y position
    public int getStartY() {
        return startY;
    }

    // Getter for the finish X position
    public int getFinishX() {
        return finishX;
    }

    // Getter for the finish Y position
    public int getFinishY() {
        return finishY;
    }

    // Returns the number of rows in the map
    public int getHeight() {
        return map.size();
    }

    // Returns the number of columns in the map (assuming all rows have the same length)
    // Returns 0 if the map is empty
    public int getWidth() {
        return map.isEmpty() ? 0 : map.get(0).length;
    }
}