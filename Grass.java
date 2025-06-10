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

public class Grass extends Cell {
    private final double humidity;  // Moisture level (0.0 to 1.0).
    
    /**
     * Creating Grass Cell.
     * @param humidity: Sets humidity moisture level.
     */
    public Grass(double humidity) {
        super("Grass");
        this.humidity = humidity; // Held to a valid range.
    }
    
    /**
     * Chance for Grass to ignite.
     * @param probability: Base ignition probability (modified by humidity).
     * @return: Returns true if ignition was a success.
     */
    @Override
    public boolean ignite(double probability) {
        if (!isBurnt && !isBurning) {
            // The higher the humidity, the less of chance for ignition.
            double effectiveProbability = probability * (1 - humidity);
            isBurning = (Math.random() < effectiveProbability);
            return isBurning;
        }
        return false;
    }
}