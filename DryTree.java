package finalProject;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents a dry tree cell with high flammability.
 * Dry trees have a 90% base chance to catch fire.
 */
public class DryTree extends Tree {
    /**
     * Creates a new DryTree with high flammability (90%).
     */
    public DryTree() {
        super(0.9); // High burn rate
    }
}