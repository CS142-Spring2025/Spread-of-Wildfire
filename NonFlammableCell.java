package finalProject;

import java.io.Serializable;

/**
 * Abstract base class for all non-flammable cells in the simulation.
 * These cells cannot catch fire under any circumstances.
 */
public abstract class NonFlammableCell extends Cell implements Serializable {
    public NonFlammableCell(String type) {
        super(type);
    }

    @Override
    public boolean ignite(double probability) {
        return false; // Never burns
    }
}