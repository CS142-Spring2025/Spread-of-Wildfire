package finalProject;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents a space in the forest with a Cell, that is in a 
 * wildfire simulation grid with a abstract class(Cell).
 * 
 * The program defines the Cell's properties and behaviors for all types
 * of Cells.
 */

public abstract class Cell {
    protected boolean isBurning;    // Tracks if Cell is on fire.
    protected boolean isBurnt;      // Tracks if Cell has been burnt.
    protected final String type;    // Type of Cell (Tree, Grass, or Water).
    
    /**
     * Constructs a new Cell
     * @param type: Empty Cell will be assigned a type.
     */
    public Cell(String type) {
        this.type = type;
        this.isBurning = false;
        this.isBurnt = false;
    }
    
    /**
     * Cell has a chance to ignite.
     * @param probability: Probability of a successful ignition is set to go from 0% to 100%
     * (0.0 to 1.0).
     * @return: Returning true if successful and false if no ignition.
     */
    public abstract boolean ignite(double probability);
    
    /**
     * Updates Cell's state (burning Cells becoming burnt).
     * Returning true if burnt and false if burning.
     */
    public void update() {
        if (isBurning) {
            isBurning = false;
            isBurnt = true;
        }
    }
    
    // Accessor method.
    /** @return: Returns the Cell's type. */
    public String getType() { return type; }
    
    /** @return: Returns true if a Cell is burning. */
    public boolean isBurning() { return isBurning; }
    
    /** @return: Returns true if Cell was burnt. */
    public boolean isBurnt() { return isBurnt; }
}