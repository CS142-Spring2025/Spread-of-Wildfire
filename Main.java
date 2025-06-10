package finalProject;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents the main.
 */

public class Main {
    public static void main(String[] args) {
        // Text-based simulation
        FireModel model = new FireModel(20, 20);
        System.out.println("Initial state:");
        model.printGrid();
        
        for (int i = 0; i < 5; i++) {
            model.nextStep();
            System.out.println("Step " + (i+1) + ":");
            model.printGrid();
        }
        
        // GUI Simulation
        FireModel guiModel = new FireModel(30, 20);
        guiModel.setWind(WindDirection.EAST);
        FireGUI gui = new FireGUI(guiModel);
        gui.setVisible(true);
    }
}