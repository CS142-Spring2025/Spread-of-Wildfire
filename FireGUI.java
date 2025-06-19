package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program is a GUI.
 */

public class FireGUI extends JFrame {
    private FireModel model;
    private JPanel gridPanel, controlPanel;
    private JComboBox<WindDirection> windDirectionBox;
    private JSlider humiditySlider, ignitionSlider;

    public FireGUI(FireModel model) {
        this.model = model;
        setTitle("Wildfire Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Control Panel (top of the window)
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Wind Direction Dropdown
        windDirectionBox = new JComboBox<>(WindDirection.values());
        windDirectionBox.setSelectedItem(WindDirection.NONE); // Default: no wind
        controlPanel.add(new JLabel("Wind:"));
        controlPanel.add(windDirectionBox);

        // Humidity Slider
        humiditySlider = new JSlider(0, 100, 30); // Min: 0%, Max: 100%, Default: 30%
        humiditySlider.setMajorTickSpacing(20);
        humiditySlider.setPaintTicks(true);
        humiditySlider.setPaintLabels(true);
        controlPanel.add(new JLabel("Humidity:"));
        controlPanel.add(humiditySlider);

        // Ignition Probability Slider
        ignitionSlider = new JSlider(0, 100, 50); // Min: 0%, Max: 100%, Default: 50%
        ignitionSlider.setMajorTickSpacing(20);
        ignitionSlider.setPaintTicks(true);
        ignitionSlider.setPaintLabels(true);
        controlPanel.add(new JLabel("Ignition:"));
        controlPanel.add(ignitionSlider);

        // Save/Load Buttons
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);

        // Grid Panel (center of the window)
        gridPanel = new JPanel(new GridLayout(model.getGrid().length, model.getGrid()[0].length));

        // Add panels to the JFrame
        add(controlPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        // Event Listeners
        windDirectionBox.addActionListener(e -> 
            model.setWind((WindDirection) windDirectionBox.getSelectedItem())
        );

        saveButton.addActionListener(e -> {
            try {
                model.saveToFile("simulation.sav");
                JOptionPane.showMessageDialog(this, "Simulation saved!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadButton.addActionListener(e -> {
            try {
                model.loadFromFile("simulation.sav");
                drawGrid(); // Refresh the grid display
                JOptionPane.showMessageDialog(this, "Simulation loaded!");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Timer for animation
        Timer timer = new Timer(500, e -> {
            model.nextStep();
            drawGrid();
        });
        timer.start();
    }

    private void drawGrid() {
        gridPanel.removeAll();
        Cell[][] grid = model.getGrid();
        
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                JPanel cellPanel = new JPanel();
                if (cell.isBurning()) cellPanel.setBackground(Color.RED);
                else if (cell.isBurnt()) cellPanel.setBackground(Color.BLACK);
                else if (cell.getType().equals("Tree")) cellPanel.setBackground(Color.GREEN);
                else if (cell.getType().equals("Grass")) cellPanel.setBackground(new Color(144, 238, 144)); // Light green
                else cellPanel.setBackground(Color.BLUE); // Water/Rock
                gridPanel.add(cellPanel);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}