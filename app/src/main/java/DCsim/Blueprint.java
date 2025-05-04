package DCsim;

import java.awt.*;
import javax.swing.*;

import DCsim.components.Pose;

public class Blueprint extends JPanel {
    // Ratios relative to the container's size.
    private static final double WIDTH_RATIO = 0.8;
    private static final double HEIGHT_RATIO = 0.8;
    private static final int ARC_RADIUS = 15;
    
    // Variables to store the computed blueprint size.
    private int blueprintWidth;
    private int blueprintHeight;
    
    public Blueprint() {
        setBackground(new Color(245, 245, 245));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Use Graphics2D for improved rendering.
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Calculate the blueprint dimensions based on the panel's current size.
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        blueprintWidth = (int) (panelWidth * WIDTH_RATIO);
        blueprintHeight = (int) (panelHeight * HEIGHT_RATIO);
        
        // Center the blueprint rectangle in the panel.
        int x = (panelWidth - blueprintWidth) / 2;
        int y = (panelHeight - blueprintHeight) / 2;
        
        // Draw a subtle drop shadow.
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillRoundRect(x + 4, y + 4, blueprintWidth, blueprintHeight, ARC_RADIUS, ARC_RADIUS);
        
        // Draw the blueprint area as a rounded rectangle.
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x, y, blueprintWidth, blueprintHeight, ARC_RADIUS, ARC_RADIUS);
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawRoundRect(x, y, blueprintWidth, blueprintHeight, ARC_RADIUS, ARC_RADIUS);
        
        g2d.dispose();
    }
    
    // Getter for blueprint width.
    public int getBlueprintWidth() {
        return blueprintWidth;
    }
    
    // Getter for blueprint height.
    public int getBlueprintHeight() {
        return blueprintHeight;
    }
    
    // Getter for blueprint size as a Dimension.
    public Dimension getBlueprintSize() {
        return new Dimension(blueprintWidth, blueprintHeight);
    }

    /* Tests if a Module is inside or outside of the blueprint */
    public boolean isInsideBlueprint(Pose pose) {
        if (((pose.getX() + pose.getWidth()) < getBlueprintWidth()) && (pose.getY() + pose.getHeight()) < getBlueprintHeight()) {
            return true;
        } else {
            return false;
        }
    }

}
