import components.Module;
import handler.ModuleHandler;
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

                ModuleHandler.ModuleType type = ModuleHandler.getInstance().getModuleType(module.getId());
                g.setColor(getColorForType(type));

                g.fillRect(x, y, 40, 40);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, 40, 40);
                g.drawString("ID: " + module.getId(), x + 5, y + 55);
            }
        }

        private Color getColorForType(ModuleHandler.ModuleType type) {
            if (type == null) return Color.GRAY;
            return switch (type) {
                case COMPUTING -> Color.CYAN;
                case CONTROL -> Color.ORANGE;
                case COOLING -> Color.BLUE;
                case STORAGE -> Color.GREEN;
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

            g.setFont(new Font("Arial", Font.PLAIN, 13));
            List<Module> modules = ModuleHandler.getInstance().getListModules();
            for (Module module : modules) {
                ModuleHandler.ModuleType type = ModuleHandler.getInstance().getModuleType(module.getId());
                String label = "Mod" + module.getId() + " type: " + (type != null ? type.name() : "x");
                g.drawString(label, 20, y);
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
    }
}
