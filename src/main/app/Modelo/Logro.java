package main.app.Vista;

import main.app.Modelo.Logro;
import main.app.Modelo.Insignia;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ==========================================================
 * PANEL DE LOGROS (Swing) - CineRoadMap
 * ==========================================================
 * - Tabla con: Insignia | Logro | Descripción | Progreso | Estado
 * - Progreso: barra + actual/objetivo
 * - Insignia: ImageIcon (gris si bloqueada)
 *
 * Uso:
 *   PanelLogros p = new PanelLogros();
 *   p.setLogros(CatalogoLogros.getLogros());  // o tu lista real
 *   ...
 *   // cuando el modelo cambie (evaluar peli/género):
 *   p.refresh();
 */
public class PanelLogros extends JPanel {

    private final JTable table;
    private final LogrosTableModel tableModel;

    private final JLabel detailTitle = new JLabel("Detalle del logro");
    private final JLabel detailText = new JLabel("Selecciona un logro...");
    private final JLabel bigIcon = new JLabel();

    public PanelLogros() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("Logros e Insignias");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);

        // Tabla
        tableModel = new LogrosTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(54);
        table.setFillsViewportHeight(true);

        // Renderers
        table.getColumnModel().getColumn(0).setCellRenderer(new InsigniaCellRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(new ProgresoCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRenderer());

        // Tamaños de columnas (aprox)
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(240);
        table.getColumnModel().getColumn(4).setMaxWidth(140);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel detalle derecha
        add(buildDetailPanel(), BorderLayout.EAST);

        // Selección -> detalle
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int r = table.getSelectedRow();
            if (r < 0) renderDetail(null);
            else renderDetail(tableModel.getAt(r));
        });
    }

    private JComponent buildDetailPanel() {
        JPanel right = new JPanel(new BorderLayout(10, 10));
        right.setPreferredSize(new Dimension(340, 420));

        detailTitle.setFont(detailTitle.getFont().deriveFont(Font.BOLD, 14f));

        bigIcon.setHorizontalAlignment(SwingConstants.CENTER);
        bigIcon.setPreferredSize(new Dimension(140, 140));

        detailText.setVerticalAlignment(SwingConstants.TOP);
        detailText.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(detailTitle);
        header.add(Box.createVerticalStrut(8));
        header.add(bigIcon);

        right.add(header, BorderLayout.NORTH);
        right.add(new JScrollPane(detailText), BorderLayout.CENTER);

        return right;
    }

    // ---------------- API para integrarlo en la app ----------------

    public void setLogros(List<Logro> logros) {
        tableModel.setLogros(logros);
        if (tableModel.getRowCount() > 0) {
            table.getSelectionModel().setSelectionInterval(0, 0);
        } else {
            renderDetail(null);
        }
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
        int r = table.getSelectedRow();
        if (r >= 0 && r < tableModel.getRowCount()) {
            renderDetail(tableModel.getAt(r));
        }
    }

    // ---------------- Detalle ----------------

    private void renderDetail(Logro l) {
        if (l == null) {
            bigIcon.setIcon(null);
            detailText.setText("Selecciona un logro...");
            return;
        }

        String nombre = safe(callString(l, "getNombreLogro"), l.getnombreReto());
        String desc = safe(l.getDescripcion(), "—");
        boolean done = l.getCompleto();

        Progreso p = Progreso.from(l);
        String progTxt = (p.objetivo > 0)
                ? (p.actual + "/" + p.objetivo + " (" + p.percent() + "%)")
                : (done ? "Completado" : "Sin progreso");

        Icon iconBig = buildIcon(l.getInsignia(), done, 96);
        bigIcon.setIcon(iconBig);

        String html =
                "<html style='width: 300px; font-family: sans-serif;'>" +
                        "<b>" + esc(nombre) + "</b><br/><br/>" +
                        "<b>Descripción:</b> " + esc(desc) + "<br/><br/>" +
                        "<b>Estado:</b> " + (done ? "✅ Completado" : "Pendiente") + "<br/>" +
                        "<b>Progreso:</b> " + esc(progTxt) + "<br/>" +
                        "</html>";

        detailText.setText(html);
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String safe(String a, String b) {
        if (a != null && !a.trim().isEmpty()) return a;
        return (b == null) ? "" : b;
    }

    private static String callString(Object obj, String method) {
        try {
            Method m = obj.getClass().getMethod(method);
            Object out = m.invoke(obj);
            return (out instanceof String) ? (String) out : null;
        } catch (Exception ignored) {
            return null;
        }
    }

    // ============================
    // Table Model
    // ============================

    private static class LogrosTableModel extends AbstractTableModel {

        private final String[] cols = {"Insignia", "Logro", "Descripción", "Progreso", "Estado"};
        private List<Logro> logros = new ArrayList<>();

        public void setLogros(List<Logro> list) {
            logros = (list == null) ? new ArrayList<>() : new ArrayList<>(list);
            fireTableDataChanged();
        }

        public Logro getAt(int row) {
            return logros.get(row);
        }

        @Override public int getRowCount() { return logros.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int col) { return cols[col]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Logro l = logros.get(rowIndex);
            switch (columnIndex) {
                case 0: return l.getInsignia();
                case 1: return l.getnombreReto();      // tu getter real
                case 2: return l.getDescripcion();     // tu getter real
                case 3: return Progreso.from(l);       // progreso adaptable
                case 4: return l.getCompleto();        // tu getter real
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return Insignia.class;
                case 3: return Progreso.class;
                case 4: return Boolean.class;
                default: return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    // ============================
    // Renderers
    // ============================

    private static class InsigniaCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);

            Insignia ins = (value instanceof Insignia) ? (Insignia) value : null;
            boolean done = (Boolean) table.getModel().getValueAt(row, 4);

            lbl.setIcon(buildIcon(ins, done, 40));
            return lbl;
        }
    }

    private static class ProgresoCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            Progreso p = (value instanceof Progreso) ? (Progreso) value : new Progreso(0, 0);

            JPanel panel = new JPanel(new BorderLayout(8, 0));
            panel.setOpaque(true);

            JProgressBar bar = new JProgressBar();
            int max = Math.max(1, p.objetivo);
            bar.setMinimum(0);
            bar.setMaximum(max);
            bar.setValue(Math.min(p.actual, max));
            bar.setStringPainted(false);

            JLabel txt = new JLabel(p.text());
            txt.setFont(txt.getFont().deriveFont(12f));

            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
                txt.setForeground(table.getSelectionForeground());
            } else {
                panel.setBackground(Color.WHITE);
                txt.setForeground(Color.DARK_GRAY);
            }

            panel.add(bar, BorderLayout.CENTER);
            panel.add(txt, BorderLayout.EAST);
            return panel;
        }
    }

    private static class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            boolean done = (value instanceof Boolean) && (Boolean) value;

            JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                    table, done ? "Completado" : "Pendiente", isSelected, hasFocus, row, column);

            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 12f));
            return lbl;
        }
    }

    // ============================
    // Progreso adaptable (sin romper compilación)
    // ============================

    private static class Progreso {
        final int actual;
        final int objetivo;

        Progreso(int actual, int objetivo) {
            this.actual = Math.max(0, actual);
            this.objetivo = Math.max(0, objetivo);
        }

        static Progreso from(Logro l) {
            // Intentos típicos (según cómo lo hayáis implementado en el repo):
            Integer a = callInt(l, "getProgresoActual");
            Integer o = callInt(l, "getProgresoObjetivo");
            if (a != null && o != null) return new Progreso(a, o);

            Integer a2 = callInt(l, "getActual");
            Integer o2 = callInt(l, "getObjetivo");
            if (a2 != null && o2 != null) return new Progreso(a2, o2);

            Integer a3 = callInt(l, "getProgreso");
            Integer o3 = callInt(l, "getObjetivo");
            if (a3 != null && o3 != null) return new Progreso(a3, o3);

            // Si no hay progreso en el modelo: 0 o 1 si está completado
            return new Progreso(l.getCompleto() ? 1 : 0, l.getCompleto() ? 1 : 0);
        }

        static Integer callInt(Object obj, String method) {
            try {
                Method m = obj.getClass().getMethod(method);
                Object out = m.invoke(obj);
                return (out instanceof Integer) ? (Integer) out : null;
            } catch (Exception ignored) {
                return null;
            }
        }

        int percent() {
            if (objetivo <= 0) return 0;
            double p = (actual * 100.0) / objetivo;
            p = Math.max(0, Math.min(100, p));
            return (int) Math.round(p);
        }

        String text() {
            if (objetivo <= 0) return "—";
            return actual + "/" + objetivo;
        }
    }

    // ============================
    // Icono de Insignia (gris si bloqueada)
    // ============================

    private static Icon buildIcon(Insignia ins, boolean done, int sizePx) {
        if (ins == null) return null;

        ImageIcon base;
        try {
            base = ins.getInsignia();
        } catch (Exception e) {
            return null;
        }
        if (base == null || base.getImage() == null) return null;

        boolean unlocked = false;
        try {
            unlocked = ins.getBloqueo();
        } catch (Exception ignored) {}

        Image img = base.getImage();

        // Si está bloqueada y no completado -> gris
        if (!unlocked && !done) {
            img = toGray(img);
        }

        Image scaled = img.getScaledInstance(sizePx, sizePx, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private static Image toGray(Image img) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        if (w <= 0 || h <= 0) return img;

        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgba = bi.getRGB(x, y);
                Color c = new Color(rgba, true);
                int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                Color cg = new Color(gray, gray, gray, c.getAlpha());
                bi.setRGB(x, y, cg.getRGB());
            }
        }
        return bi;
    }
}




















PanelLogros panelLogros = new PanelLogros();
panelLogros.setLogros(CatalogoLogros.getLogros()); // o vuestra lista real

// meterlo en un contenedor Swing:
tuPanelContenedor.add(panelLogros);
