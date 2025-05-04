package DCsim.ui;

import DCsim.components.Module;
import DCsim.handler.ModuleHandler;
import DCsim.handler.ModuleHandler.ModuleType;
import DCsim.handler.ModuleHandler.ModuleVariant;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.*;

public class GuiDisplay {
    private JFrame frame;
    private DisplayPanel displayPanel;
    private LegendPanel legendPanel;
    private Supplier<ResourceData> resourceSupplier;

    public void setGlobalData(Supplier<ResourceData> supplier) {
        this.resourceSupplier = supplier;
    }

    public void setup() {
        frame = new JFrame("Designer Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());
        
        // Create a center container with an OverlayLayout to layer components.
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new OverlayLayout(centerPanel));
        
        // Create the two layers:
        // 1. The blueprint area (drawn first, i.e. in the background).
        Blueprint blueprintPanel = new Blueprint();
        // 2. The module rectangle display (drawn on top).
        displayPanel = new DisplayPanel();
        displayPanel.setOpaque(false);  // Make this panel transparent so the blueprint remains visible.
        
        // When using OverlayLayout, the components are painted in reverse order.
        // Adding displayPanel first and then blueprintPanel yields:
        //   - blueprintPanel (last added) is painted first (background)
        //   - displayPanel (first added) is painted afterwards (foreground)
        centerPanel.add(displayPanel);
        centerPanel.add(blueprintPanel);
        
        legendPanel = new LegendPanel(resourceSupplier);
        
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(legendPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }
    
    public void updateView() {
        displayPanel.repaint();
        legendPanel.repaint();
    }
    
    // -----------------------------
    // Panel for displaying modules (drawn on top of the blueprint).
    // -----------------------------
    private static class DisplayPanel extends JPanel {
        private final int moduleWidth = 80;
        private final int moduleHeight = 40;
        private final int arcRadius = 10; // Rounded corners.
        private final int shadowOffset = 4;
        
        public DisplayPanel() {
            setBackground(Color.WHITE); // This background won’t be painted since opacity is off.
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            List<Module> modules = ModuleHandler.getInstance().getListModules();
            for (Module module : modules) {
                int x = module.getPose().getX();
                int y = module.getPose().getY();

                ModuleType type = ModuleHandler.getInstance().getModuleType(module.getBlockID());
                ModuleVariant variant = ModuleHandler.getInstance().getModuleVariant(module.getBlockID());
                Color moduleColor = getColorForType(type);
                
                // Draw a subtle drop shadow.
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(x + shadowOffset, y + shadowOffset, moduleWidth, moduleHeight, arcRadius, arcRadius);
                
                // Draw the module as a card with rounded corners.
                g2d.setColor(moduleColor);
                g2d.fillRoundRect(x, y, moduleWidth, moduleHeight, arcRadius, arcRadius);
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(x, y, moduleWidth, moduleHeight, arcRadius, arcRadius);
                
                // Draw an internal icon in white.
                g2d.setColor(Color.WHITE);
                int centerX = x + moduleWidth / 2;
                int centerY = y + moduleHeight / 2;
                switch (variant) {
                    case one -> {
                        int halfBase = 10;
                        int triHeight = 20;
                        int[] xPoints = {centerX, centerX - halfBase, centerX + halfBase};
                        int[] yPoints = {centerY - triHeight / 2, centerY + triHeight / 2, centerY + triHeight / 2};
                        g2d.fillPolygon(xPoints, yPoints, 3);
                    }
                    case two -> {
                        int circleDiameter = 20;
                        int circleX = centerX - circleDiameter / 2;
                        int circleY = centerY - circleDiameter / 2;
                        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);
                    }
                    case three -> {
                        int diamondSize = 20;
                        int[] dxPoints = {centerX, centerX - diamondSize / 2, centerX, centerX + diamondSize / 2};
                        int[] dyPoints = {centerY - diamondSize / 2, centerY, centerY + diamondSize / 2, centerY};
                        g2d.fillPolygon(dxPoints, dyPoints, 4);
                    }
                }
                
                // Draw module type text below the module.
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawString(type != null ? type.name() : "Unknown", x + 5, y + moduleHeight + 15);
            }
            g2d.dispose();
        }
        
        private Color getColorForType(ModuleType type) {
            if (type == null) return new Color(158, 158, 158);
            return switch (type) {
                case COMPUTING -> new Color(3, 169, 244);
                case TRANSFORMER -> new Color(255, 152, 0);
                case COOLING -> new Color(33, 150, 243);
                case STORAGE -> new Color(76, 175, 80);
                default -> new Color(158, 158, 158);
            };
        }
    }
    
    // -----------------------------
    // Blueprint panel (drawn underneath the module rectangles).
    // This panel calculates its size proportionally to its container and draws a centered,
    // rounded rectangle that represents the blueprint area.
    // -----------------------------
    public static class Blueprint extends JPanel {
        // These ratios determine the size of the blueprint relative to the available space.
        private static final double WIDTH_RATIO = 0.8;
        private static final double HEIGHT_RATIO = 0.8;
        private static final int ARC_RADIUS = 15;
        
        public Blueprint() {
            setBackground(new Color(245, 245, 245));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int blueprintWidth = (int) (panelWidth * WIDTH_RATIO);
            int blueprintHeight = (int) (panelHeight * HEIGHT_RATIO);
            
            // Center the blueprint area within this panel.
            int x = (panelWidth - blueprintWidth) / 2;
            int y = (panelHeight - blueprintHeight) / 2;
            
            // Draw a subtle drop shadow.
            g2d.setColor(new Color(0, 0, 0, 40));
            g2d.fillRoundRect(x + 4, y + 4, blueprintWidth, blueprintHeight, ARC_RADIUS, ARC_RADIUS);
            
            // Draw the blueprint rectangle.
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(x, y, blueprintWidth, blueprintHeight, ARC_RADIUS, ARC_RADIUS);
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.drawRoundRect(x, y, blueprintWidth, blueprintHeight, ARC_RADIUS, ARC_RADIUS);
            
            g2d.dispose();
        }
    }
    
    // -----------------------------
    // Data class used to supply resource values to the legend panel.
    // -----------------------------
    public static class ResourceData {
        public final int usedCost, totalCost;
        public final int usedElectricity, totalElectricity;
        public final int usedCooling, totalCooling;
        public final int usedComputing, totalComputing;

        public ResourceData(int usedCost, int totalCost, int usedElectricity, int totalElectricity,
                            int usedCooling, int totalCooling, int usedComputing, int totalComputing) {
            this.usedCost = usedCost;
            this.totalCost = totalCost;
            this.usedElectricity = usedElectricity;
            this.totalElectricity = totalElectricity;
            this.usedCooling = usedCooling;
            this.totalCooling = totalCooling;
            this.usedComputing = usedComputing;
            this.totalComputing = totalComputing;
        }
    }
    
    // -----------------------------
    // Panel that displays resource usage and the module list.
    // -----------------------------
    private static class LegendPanel extends JPanel {
        private final Supplier<ResourceData> resourceSupplier;
        
        public LegendPanel(Supplier<ResourceData> resourceSupplier) {
            this.resourceSupplier = resourceSupplier;
            setPreferredSize(new Dimension(250, 0));
            setBackground(Color.WHITE);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // A subtle shadow on the left edge for depth.
            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fillRect(0, 0, 4, getHeight());
            
            // Material style title.
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("STATUS", 20, 30);
            
            ResourceData data = resourceSupplier.get();
            int y = 60;
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 13));
            y = drawResourceBar(g2d, "Cost", data.usedCost, data.totalCost, y);
            y = drawResourceBar(g2d, "Electricity", data.usedElectricity, data.totalElectricity, y);
            y = drawResourceBar(g2d, "Cooling", data.usedCooling, data.totalCooling, y);
            y = drawResourceBar(g2d, "Computing", data.usedComputing, data.totalComputing, y);
            
            y += 40;
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("MODULES", 20, y);
            y += 30;
            
            // Draw the module list.
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 13));
            List<Module> modules = ModuleHandler.getInstance().getListModules();
            for (Module module : modules) {
                ModuleType type = ModuleHandler.getInstance().getModuleType(module.getBlockID());
                ModuleVariant variant = ModuleHandler.getInstance().getModuleVariant(module.getBlockID());
                
                int squareSize = 15;
                int squareX = 20;
                int squareY = y - squareSize + 3;
                Color moduleColor = getColorForType(type);
                g2d.setColor(moduleColor);
                g2d.fillRoundRect(squareX, squareY, squareSize, squareSize, 4, 4);
                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.drawRoundRect(squareX, squareY, squareSize, squareSize, 4, 4);
                
                String label = (type != null ? type.name() : "Unknown") +
                               " Variant: " + (variant != null ? variant.name() : "None");
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawString(label, squareX + squareSize + 5, y);
                y += 25;
            }
            g2d.dispose();
        }

        private int drawResourceBar(Graphics2D g2d, String label, int used, int total, int y) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString(label + " (" + used + "/" + total + ")", 20, y);
            y += 5;
            int barWidth = 180;
            int barHeight = 15;
            int filled = (int) ((used / (double) total) * barWidth);
            // Draw background bar.
            g2d.setColor(new Color(224, 224, 224));
            g2d.fillRoundRect(20, y, barWidth, barHeight, 8, 8);
            // Draw filled portion.
            g2d.setColor(new Color(100, 150, 255));
            g2d.fillRoundRect(20, y, filled, barHeight, 8, 8);
            // Outline.
            g2d.setColor(new Color(0, 0, 0, 80));
            g2d.drawRoundRect(20, y, barWidth, barHeight, 8, 8);
            return y + 30;
        }

        private Color getColorForType(ModuleType type) {
            if (type == null) return new Color(158, 158, 158);
            return switch (type) {
                case COMPUTING -> new Color(3, 169, 244);
                case TRANSFORMER -> new Color(255, 152, 0);
                case COOLING -> new Color(33, 150, 243);
                case STORAGE -> new Color(76, 175, 80);
                default -> new Color(158, 158, 158);
            };
        }
    }
}
