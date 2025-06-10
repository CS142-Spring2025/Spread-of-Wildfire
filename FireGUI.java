package finalProject;

import javax.swing.*;
import java.awt.*;

/**
 * 06/01/2025
 * Ivan Corchado
 * 
 * This program is a GUI.
 */

public class FireGUI extends JFrame {
    private FireModel model;
    private JPanel gridPanel;

    public FireGUI(FireModel model) {
        this.model = model;
        setTitle("Wildfire Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        gridPanel = new JPanel(new GridLayout(model.getGrid().length, model.getGrid()[0].length));
        add(gridPanel);
        
        drawGrid();
        
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
                else cellPanel.setBackground(Color.BLUE);
                
                gridPanel.add(cellPanel);
            }
        }
        
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}