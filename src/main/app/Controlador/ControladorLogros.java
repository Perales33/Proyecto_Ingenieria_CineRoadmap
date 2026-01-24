package main.app.Controlador;

import main.app.Modelo.Logro;
import main.app.Vista.PanelLogros;

import java.util.List;

public class ControladorLogros {

    private final PanelLogros view;
    private List<Logro> logros;

    public ControladorLogros(PanelLogros view, List<Logro> logros) {
        this.view = view;
        this.logros = logros;
        init();
    }

    private void init() {
        view.setLogros(logros);

        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            int row = view.getTable().getSelectedRow();
            if (row >= 0 && row < logros.size()) {
                view.renderDetail(logros.get(row));
            } else {
                view.renderDetail(null);
            }
        });

        if (!logros.isEmpty()) {
            view.getTable().setRowSelectionInterval(0, 0);
        }
    }

    public void refresh() {
        view.setLogros(logros);
        view.getTable().repaint();
    }

    public void setLogros(List<Logro> nuevos) {
        this.logros = nuevos;
        refresh();
    }
}
