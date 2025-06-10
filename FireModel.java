package finalProject;

import java.util.Random;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program models a forest going through a wildfire on a
 * simulation gird, as FireModel.
 * 
 * The FireModel displays the logic, algorithms, and states of 
 * the simulation.
 */

public class FireModel {
    private Cell[][] grid;      // Create 2D array for Cells.
    private int width, height;  // Dimensions of grid.
    private WindDirection windDirection = WindDirection.NONE; // Wind.
    
    /**
     * Created FireModel according to grid size.
     * @param width: Grid width in Cells
     * @param height: Gird height in Cells
     */
    public FireModel(int width, int height) {
        this.width = width;
        this.height = height;
        initializeGrid();
    }
    
    /**
     * Set grid with random Cells and start a fire at the center 
     * of FireModel.
     * Guarantees a spark to happen at the center.
     */
    private void initializeGrid() {
        grid = new Cell[height][width];
        Random rand = new Random();
        
        // Makes sure a spark happens at the center in a 3 by 3 area.
        for (int y = height/2-1; y <= height/2+1; y++) {
            for (int x = width/2-1; x <= width/2+1; x++) {
                // 70% chance of spark happenning (0.7).
                if (rand.nextDouble() > 0.3) {
                    grid[y][x] = rand.nextBoolean() ? 
                        new Tree(0.7) : new Grass(0.3);
                }
            }
        }
        
        // Fill remaining cells randomly on FireModel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == null) {
                    double r = rand.nextDouble();
                    grid[y][x] = r < 0.6 ? new Tree(0.7) :
                                r < 0.9 ? new Grass(0.3) :
                                new Water();
                }
            }
        }
        
        // Starts spark at the center of FireModel.
        grid[height/2][width/2].ignite(1.0);
    }
    
    /**
     * Advances simulation by one step.
     * Updating all Cells and the spreading of fire.
     */
    public void nextStep() {
        Cell[][] newGrid = new Cell[height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x].isBurning()) {
                    spreadFire(x, y);   // Spreads to neighboring Cells.
                }
                grid[y][x].update();
                newGrid[y][x] = grid[y][x]; // Update Cell's state.
            }
        }
        
        grid = newGrid;
    }
    
    /**
     * Spreads fire from a burning Cell to a neighboring Cell.
     * @param x: X-Coordinate of Cell.
     * @param y: Y-Coordinate of Cell.
     */
    private void spreadFire(int x, int y) {
        // Check surrounding 8 neighboring Cells.
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue; // Skip self
                
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    // Apply wind effect to spread probability.
                    double baseProb = 0.5;
                    double windEffect = getWindMultiplier(dx, dy);
                    grid[ny][nx].ignite(baseProb * windEffect);
                }
            }
        }
    }
    
    /**
     * Calculates the wind effect multiplier for fire spread.
     * @param dx: X-direction of spread (-1, 0, 1).
     * @param dy: Y-direction of spread (-1, 0, 1).
     * @return: Returns multiplier (1.5 for downwind & 0.8 for upwind).
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
     * Creates wind direction.
     * @param direction: Wind will effect the spread of fire.
     */
    public void setWind(WindDirection direction) {
        this.windDirection = direction;
    }

    public Cell[][] getGrid() { return grid; }

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
}

enum WindDirection { NORTH, SOUTH, EAST, WEST, NONE }