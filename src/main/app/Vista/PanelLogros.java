package main.app.Vista;

import main.app.Controlador.PanelLogrosController;
import main.app.Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelLogros {

    public static JPanel crearPanelLogros() {

        // Panel principal
        JPanel panelCentral = new JPanel(new BorderLayout());

        // =========================
        // BANNER
        // =========================
        JPanel banner = PanelBanner.crearBanner();
        panelCentral.add(banner, BorderLayout.NORTH);

        // =========================
        // CONTENIDO
        // =========================
        JPanel contenido = new JPanel(new BorderLayout(12,12));
        contenido.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        contenido.setBackground(Color.BLACK);

        // ===== TABLA LOGROS =====
        PanelLogrosView vista = new PanelLogrosView();

        List<Logro> logros = CatalogoLogros.getLogros();
        PanelLogrosController controller =
                new PanelLogrosController(vista, logros);

        contenido.add(vista, BorderLayout.CENTER);
        panelCentral.add(contenido, BorderLayout.CENTER);

        return panelCentral;
    }
}
