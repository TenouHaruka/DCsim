import components.Module;
import handler.ModuleHandler;
import handler.ModuleHandler.ModuleType;
import handler.ModuleHandler.ModuleVariant;
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

        displayPanel = new DisplayPanel();
        legendPanel = new LegendPanel(resourceSupplier);

        frame.add(displayPanel, BorderLayout.CENTER);
        frame.add(legendPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void updateView() {
        displayPanel.repaint();
        legendPanel.repaint();
    }

    // Panel for displaying modules visually.
    private static class DisplayPanel extends JPanel {
        public DisplayPanel() {
            setBackground(Color.WHITE);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            List<Module> modules = ModuleHandler.getInstance().getListModules();
            for (Module module : modules) {
                int x = module.getPose().getX();
                int y = module.getPose().getY();

                ModuleType type = ModuleHandler.getInstance().getModuleType(module.getId());
                ModuleVariant variant = ModuleHandler.getInstance().getModuleVariant(module.getId());

                Color color = getColorForType(type);

                // Draw the module rectangle.
                g.setColor(color);
                g.fillRect(x, y, 80, 40);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, 80, 40);

                // Draw a shape based on the module variant.
                // The shape will be centered within the rectangle.
                g.setColor(Color.BLACK);
                if (variant != null) {
                    switch (variant) {
                        case one -> {
                            // Draw a triangle.
                            int centerX = x + 40;
                            int centerY = y + 20;
                            int halfBase = 10;
                            int triHeight = 20;
                            int[] xPoints = {centerX, centerX - halfBase, centerX + halfBase};
                            int[] yPoints = {centerY - triHeight / 2, centerY + triHeight / 2, centerY + triHeight / 2};
                            g.fillPolygon(xPoints, yPoints, 3);
                        }
                        case two -> {
                            // Draw a circle.
                            int circleDiameter = 20;
                            int circleX = x + 40 - circleDiameter / 2;
                            int circleY = y + 20 - circleDiameter / 2;
                            g.fillOval(circleX, circleY, circleDiameter, circleDiameter);
                        }
                        case three -> {
                            // Draw a diamond.
                            int diamondWidth = 20;
                            int diamondHeight = 20;
                            int diamondCenterX = x + 40;
                            int diamondCenterY = y + 20;
                            int[] dxPoints = {
                                diamondCenterX,
                                diamondCenterX - diamondWidth / 2,
                                diamondCenterX,
                                diamondCenterX + diamondWidth / 2
                            };
                            int[] dyPoints = {
                                diamondCenterY - diamondHeight / 2,
                                diamondCenterY,
                                diamondCenterY + diamondHeight / 2,
                                diamondCenterY
                            };
                            g.fillPolygon(dxPoints, dyPoints, 4);
                        }
                    }
                }
                
                // Below the rectangle, display the module type.
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                g.drawString((type != null ? type.name() : "Unknown"), x + 5, y + 55);
            }
        }
        
        private Color getColorForType(ModuleType type) {
            if (type == null) return Color.GRAY;
            return switch (type) {
                case COMPUTING -> Color.CYAN;
                case CONTROL -> Color.ORANGE;
                case COOLING -> Color.BLUE;
                case STORAGE -> Color.GREEN;
                default -> Color.GRAY;
            };
        }
    }
    
    // Data class used to supply resource values to the legend panel.
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
    
    // Panel that displays resource usage and the module list.
    private static class LegendPanel extends JPanel {
        private final Supplier<ResourceData> resourceSupplier;

        public LegendPanel(Supplier<ResourceData> resourceSupplier) {
            this.resourceSupplier = resourceSupplier;
            setPreferredSize(new Dimension(250, 0));
            setBackground(new Color(240, 240, 240));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            g.drawLine(0, 0, 0, getHeight());

            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.BLACK);
            g.drawString("STATUS", 20, 30);

            ResourceData data = resourceSupplier.get();

            int y = 60;
            g.setFont(new Font("Arial", Font.PLAIN, 13));
            y = drawResourceBar(g, "Cost", data.usedCost, data.totalCost, y);
            y = drawResourceBar(g, "Electricity", data.usedElectricity, data.totalElectricity, y);
            y = drawResourceBar(g, "Cooling", data.usedCooling, data.totalCooling, y);
            y = drawResourceBar(g, "Computing", data.usedComputing, data.totalComputing, y);

            y += 40;
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("MODULES", 20, y);
            y += 30;

            // Move module list down a bit.
            y += 10;

            g.setFont(new Font("Arial", Font.PLAIN, 13));
            List<Module> modules = ModuleHandler.getInstance().getListModules();
            for (Module module : modules) {
                ModuleType type = ModuleHandler.getInstance().getModuleType(module.getId());
                ModuleVariant variant = ModuleHandler.getInstance().getModuleVariant(module.getId());

                // Draw a small square (15x15) with the same color as on the main display.
                int squareSize = 15;
                Color squareColor = getColorForType(type);
                g.setColor(squareColor);
                g.fillRect(20, y - squareSize + 3, squareSize, squareSize);
                g.setColor(Color.BLACK);
                g.drawRect(20, y - squareSize + 3, squareSize, squareSize);

                // Build the module label.
                String label = (type != null ? type.name() : "x") +
                               " Variant: " + (variant != null ? variant.name() : "x");
                // Draw the label to the right of the square.
                g.drawString(label, 20 + squareSize + 5, y);
                y += 25;
            }
        }

        private int drawResourceBar(Graphics g, String label, int used, int total, int y) {
            g.setColor(Color.BLACK);
            g.drawString(label + " (" + used + "/" + total + ")", 20, y);
            y += 5;
            int barWidth = 180;
            int barHeight = 15;
            int filled = (int) ((used / (double) total) * barWidth);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(20, y, barWidth, barHeight);
            g.setColor(new Color(100, 150, 255));
            g.fillRect(20, y, filled, barHeight);
            g.setColor(Color.BLACK);
            g.drawRect(20, y, barWidth, barHeight);
            return y + 30;
        }

        private Color getColorForType(ModuleType type) {
            if (type == null) return Color.GRAY;
            return switch (type) {
                case COMPUTING -> Color.CYAN;
                case CONTROL -> Color.ORANGE;
                case COOLING -> Color.BLUE;
                case STORAGE -> Color.GREEN;
                default -> Color.GRAY;
            };
        }
    }
}
