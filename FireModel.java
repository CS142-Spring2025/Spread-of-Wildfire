package finalProject;

import java.io.*;
import java.util.Random;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program models a forest going through a wildfire on a
 * simulation grid, as FireModel.
 * 
 * The FireModel displays the logic, algorithms, and states of 
 * the simulation, including fire spread, wind effects, and environmental factors.
 */
public class FireModel {
    private Cell[][] grid;      // 2D array representing the simulation grid
    private int width, height;  // Dimensions of grid in cells
    private WindDirection windDirection = WindDirection.NONE; // Current wind direction
    private double humidity = 0.3; // Global humidity level (0.0 to 1.0)
    private double ignitionChance = 0.5; // Base probability of fire spread (0.0 to 1.0)
    
    /**
     * Creates FireModel according to grid size.
     * @param width Grid width in cells
     * @param height Grid height in cells
     */
    public FireModel(int width, int height) {
        this.width = width;
        this.height = height;
        initializeGrid();
    }
    
    /**
    * Initializes grid with random cells and starts a fire at the center.
    * Guarantees a spark to happen at the center cell.
    * The surrounding 3x3 area has 90% chance of flammable cells.
    * The rest of the grid is filled with random cells (60% Trees, 30% Grass, 10% Water).
    */
    private void initializeGrid() {
        grid = new Cell[height][width];
        Random rand = new Random();
    
        // Center 3x3 area: 90% chance of flammable cells
        for (int y = height/2-1; y <= height/2+1; y++) {
            for (int x = width/2-1; x <= width/2+1; x++) {
                if (y == height/2 && x == width/2) {
                    // Force center cell to be a flammable cell
                    grid[y][x] = rand.nextBoolean() ? new Tree(0.9) : new Grass(0.1);
                    grid[y][x].ignite(1.0); // 100% chance to ignite center cell
                } else if (rand.nextDouble() > 0.1) {  // 90% chance for surrounding cells
                    grid[y][x] = rand.nextBoolean() ? 
                        new Tree(0.9) : new Grass(0.1);
                }
            }
        }
    
        // Fill remaining cells randomly
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == null) {
                    double r = rand.nextDouble();
                    grid[y][x] = r < 0.6 ? new Tree(0.9) :  // 60% Trees
                                r < 0.9 ? new Grass(0.1) :   // 30% Grass
                                new Water();                 // 10% Water
                }
            }
        }
    }
    
    /**
     * Advances simulation by one time step.
     * Updates all cells and spreads fire to neighboring cells.
     */
    public void nextStep() {
        Cell[][] newGrid = new Cell[height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x].isBurning()) {
                    spreadFire(x, y);   // Spread fire to neighbors
                }
                grid[y][x].update();   // Update cell state
                newGrid[y][x] = grid[y][x];
            }
        }
        
        grid = newGrid;
    }
    
    /**
     * Spreads fire from a burning cell to its neighbors.
     * @param x X-coordinate of source cell
     * @param y Y-coordinate of source cell
     */
    private void spreadFire(int x, int y) {
        // Check all 8 neighboring cells
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue; // Skip self
                
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    double windEffect = getWindMultiplier(dx, dy);
                    grid[ny][nx].ignite(ignitionChance * windEffect);
                }
            }
        }
    }
    
    /**
     * Calculates wind effect multiplier for fire spread direction.
     * @param dx X-direction of spread (-1, 0, 1)
     * @param dy Y-direction of spread (-1, 0, 1)
     * @return Multiplier (1.5 for downwind, 0.8 for upwind, 1.0 for no wind)
     */
    private double getWindMultiplier(int dx, int dy) {
        return switch (windDirection) {
            case NORTH -> dy == -1 ? 1.5 : 0.8;  // Favors northward spread
            case SOUTH -> dy == 1 ? 1.5 : 0.8;
            case EAST -> dx == 1 ? 1.5 : 0.8;
            case WEST -> dx == -1 ? 1.5 : 0.8;
            default -> 1.0;  // No wind = neutral
        };
    }
    
    /**
     * Sets the wind direction for the simulation.
     * @param direction Wind direction to set (NORTH, SOUTH, EAST, WEST, NONE)
     */
    public void setWind(WindDirection direction) {
        this.windDirection = direction;
    }
    
    /**
     * Sets the global humidity level and updates all grass cells.
     * @param humidity New humidity level (0.0 to 1.0)
     */
    public void setHumidity(double humidity) {
        this.humidity = Math.max(0, Math.min(1, humidity));
        updateGrassHumidity();
    }
    
    /**
     * Updates humidity for all grass cells in the grid.
     */
    private void updateGrassHumidity() {
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof Grass) {
                    ((Grass)cell).setHumidity(humidity);
                }
            }
        }
    }
    
    /**
     * Sets the base ignition probability for fire spread.
     * @param chance New ignition probability (0.0 to 1.0)
     */
    public void setIgnitionChance(double chance) {
        this.ignitionChance = Math.max(0, Math.min(1, chance));
    }
    
    /**
     * Gets the current simulation grid.
     * @return 2D array of cells representing the grid
     */
    public Cell[][] getGrid() { 
        return grid; 
    }
    
    /**
     * Prints the current grid state to console.
     * 'F' = Burning, 'X' = Burnt, first letter = Cell type
     */
    public void printGrid() {
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                char c = cell.isBurning() ? 'F' : 
                        cell.isBurnt() ? 'X' : 
                        cell.getType().charAt(0);
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Saves the current simulation state to a file.
     * @param filename Name of file to save to
     * @throws IOException If there's an error writing to file
     */
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(grid);
            out.writeObject(windDirection);
            out.writeDouble(humidity);
            out.writeDouble(ignitionChance);
        }
    }
    
    /**
     * Loads a simulation state from a file.
     * @param filename Name of file to load from
     * @throws IOException If there's an error reading the file
     * @throws ClassNotFoundException If the file contains unknown classes
     */
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            grid = (Cell[][]) in.readObject();
            windDirection = (WindDirection) in.readObject();
            humidity = in.readDouble();
            ignitionChance = in.readDouble();
            updateGrassHumidity();
        }
    }
}

/**
 * Enum representing possible wind directions.
 */
enum WindDirection { 
    NORTH, SOUTH, EAST, WEST, NONE 
}