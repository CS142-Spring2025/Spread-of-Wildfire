package finalProject;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents vegetation with a Grass Cell.
 * 
 * The Grass Cell has a possibility of resistance to burning
 * because of humidity.
 */
public class Grass extends FlammableCell {
    public Grass(double humidity) {
        super("Grass", 1 - humidity); // flammability = 1 - humidity
    }
    
    /**
     * Sets the humidity level for this grass cell.
     * @param humidity The new humidity level (0.0 to 1.0)
     */
    public void setHumidity(double humidity) {
        this.flammability = 1 - humidity;
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