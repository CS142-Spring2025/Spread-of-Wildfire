package finalProject;

import java.io.Serializable;

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

public abstract class Cell implements Serializable {
    protected boolean isBurning;
    protected boolean isBurnt;
    protected final String type;
    
    public Cell(String type) {
        this.type = type;
        this.isBurning = false;
        this.isBurnt = false;
    }
    
    public abstract boolean ignite(double probability);
    
    public void update() {
        if (isBurning) {
            isBurning = false;
            isBurnt = true;
        }
    }
    
    public String getType() { return type; }
    public boolean isBurning() { return isBurning; }
    public boolean isBurnt() { return isBurnt; }
}