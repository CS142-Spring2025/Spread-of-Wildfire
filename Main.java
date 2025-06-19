package finalProject;

import javax.swing.*;
import java.awt.*;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program represents the main.
 */

public class Main {
    public static void main(String[] args) {
        // Create and configure parameter dialog
        JDialog setupDialog = new JDialog();
        setupDialog.setTitle("Simulation Parameters");
        setupDialog.setLayout(new GridLayout(0, 2, 5, 5));
        setupDialog.setResizable(false);
        
        // Add components
        JComboBox<WindDirection> windBox = new JComboBox<>(WindDirection.values());
        JSlider humiditySlider = createSlider(30);
        JSlider ignitionSlider = createSlider(50);
        
        setupDialog.add(new JLabel("Wind Direction:"));
        setupDialog.add(windBox);
        setupDialog.add(new JLabel("Humidity (%):"));
        setupDialog.add(humiditySlider);
        setupDialog.add(new JLabel("Ignition Chance (%):"));
        setupDialog.add(ignitionSlider);
        
        JButton startButton = new JButton("Start Simulation");
        setupDialog.add(new JLabel(""));
        setupDialog.add(startButton);
        
        // Start simulation with parameters
        startButton.addActionListener(e -> {
            WindDirection wind = (WindDirection)windBox.getSelectedItem();
            double humidity = humiditySlider.getValue() / 100.0;
            double ignition = ignitionSlider.getValue() / 100.0;
            
            FireModel model = new FireModel(30, 20);
            model.setWind(wind);
            model.setHumidity(humidity);
            model.setIgnitionChance(ignition);
            
            FireGUI gui = new FireGUI(model);
            gui.setVisible(true);
            setupDialog.dispose();
        });
        
        setupDialog.pack();
        setupDialog.setLocationRelativeTo(null);
        setupDialog.setVisible(true);
    }
    
    private static JSlider createSlider(int defaultValue) {
        JSlider slider = new JSlider(0, 100, defaultValue);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }
}