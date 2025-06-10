package finalProject;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents a space of water in the forest, this space cannot
 * catch fire.
 * 
 * The Water Cell serves as a barrier to work against fire.
 */

public class Water extends Cell {
    /**
     * Creates a Water Cell.
     */
    public Water() {
        super("Water");
    }
    
    /**
     * Water Cells are immune to ignition.
     * @param probability: Ignores the ignition.
     * @return: Returns false always.
     */
    @Override
    public boolean ignite(double probability) {
        return false; // Water never ends up burning.
    }
}