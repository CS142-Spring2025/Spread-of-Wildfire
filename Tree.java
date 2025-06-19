package finalProject;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents a tree with Tree Cell, that can catch fire.
 * 
 * Inheriting from Cell, Tree Cell's have specific burning behaviors.
 */

public class Tree extends FlammableCell {
    public Tree(double burnRate) {
        super("Tree", burnRate);
    }
    
    @Override
    public boolean ignite(double probability) {
        if (!isBurnt && !isBurning) {
            double effectiveProbability = probability * flammability;
            isBurning = (Math.random() < effectiveProbability);
            return isBurning;
        }
        return false;
    }
}
