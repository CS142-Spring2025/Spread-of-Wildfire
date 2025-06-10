package finalProject;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents a tree with Tree Cell, that can catch fire.
 * 
 * Inheriting from Cell, Tree Cell's have specific burning behaviors.
 */

public class Tree extends Cell {
    private final double burnRate;  // Base probability of burning (0.0 to 1.0).
    
    /**
     * Creates new Tree Cell.
     * @param burnRate: Base burning probability multiplier. 
     */
    public Tree(double burnRate) {
        super("Tree");
        // Place burnRate into valid range.
        this.burnRate = Math.max(0, Math.min(1, burnRate));
    }
    
    /**
     * Tree Cell has a chance to ignite.
     * @param probability: Base ignition probability (is modified by burnRate).
     * @return: Returns true if an ignition was successful.
     */
    @Override
    public boolean ignite(double probability) {
        if (!isBurnt && !isBurning) {
            // Only unburnt trees are able to have a chance to ignite.
            double effectiveProbability = probability * burnRate;
            isBurning = (Math.random() < effectiveProbability);
            return isBurning;
        }
        return false;
    }
    
    /**
     * Fire spread probability is calculated for neighboring Cell's.
     * @return: Returns the current probability of spreading.
     */
    public double spreadFire() {
        return isBurning ? burnRate : 0.0;
    }
}