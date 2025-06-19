package finalProject;

import java.io.Serializable;

/**
 * Abstract base class for all flammable cells in the simulation.
 * Provides common flammability properties and behavior.
 */
public abstract class FlammableCell extends Cell implements Serializable {
    protected double flammability;

    public FlammableCell(String type, double flammability) {
        super(type);
        this.flammability = flammability;
    }

    @Override
    public abstract boolean ignite(double probability);
}