package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import main.app.Modelo.*;
import main.app.Controlador.*;
import main.app.util.*;

public class PanelPeliculas 
{
    public static JPanel crearPanelPeliculas()
    {
        ArrayList<Pelicula> catalogo = Pelicula.getCatalogo();

        // -------------------------
        // PANEL PRINCIPAL
        // -------------------------
        JPanel panelCental = new JPanel(new BorderLayout());

        // -------------------------
        // BANNER SUPERIOR
        // -------------------------
        JPanel banner = PanelBanner.crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        // -------------------------
        // PANEL CONTENIDO
        // -------------------------
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        // -------------------------
        // TÍTULO
        // -------------------------
        bag.gridwidth = 2; bag.gridy = 1;
        JLabel peliculasLabel = new JLabel("Seccion de Películas");
        Estilos.estilosTitulosLRC(peliculasLabel);
        peliculasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenido.add(peliculasLabel, bag);
        
        // -------------------------
        // PANEL DIVIDIDO
        // -------------------------
        bag.gridwidth = 2; bag.gridy = 2; bag.gridx = 0;
        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.7);

        // -------------------------
        // PANEL CATÁLOGO
        // -------------------------
        JPanel panelCatalogo = new JPanel();
        panelCatalogo.setLayout(new BoxLayout(panelCatalogo, BoxLayout.Y_AXIS));
        panelCatalogo.setBorder(BorderFactory.createTitledBorder("Catálogo de Películas"));

        for(Pelicula p : catalogo)
        {
            panelCatalogo.add(ControladorPeliculas.crearPeliculas(p));
            panelCatalogo.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollCatalogo = new JScrollPane(panelCatalogo);
        scrollCatalogo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        SwingUtilities.invokeLater(() -> 
        {
            scrollCatalogo.getVerticalScrollBar().setValue(0);
            scrollCatalogo.getHorizontalScrollBar().setValue(0);
        });

        // -------------------------
        // PANEL CALIFICACIONES
        // -------------------------
        JPanel panelCalificaciones = new JPanel(new GridBagLayout());
        panelCalificaciones.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0), 
                BorderFactory.createTitledBorder("Valoración de Películas")
            )
        );

        GridBagConstraints colocacionCalificacion = new GridBagConstraints();
        colocacionCalificacion.insets = new Insets(8, 8, 8, 8);
        colocacionCalificacion.fill = GridBagConstraints.HORIZONTAL;
        colocacionCalificacion.weightx = 1.0;
        colocacionCalificacion.gridwidth = 2;

        // -------------------------
        // ÁREA DE VALORACIONES
        // -------------------------
        colocacionCalificacion.gridy = 0;
        colocacionCalificacion.gridx = 0;
        colocacionCalificacion.fill = GridBagConstraints.BOTH;
        colocacionCalificacion.weighty = 1.0;

        JTextArea areaValoraciones = new JTextArea();
        areaValoraciones.setEditable(false);
        Estilos.estilosTextArea(areaValoraciones);
        areaValoraciones.setBorder(BorderFactory.createTitledBorder("Tus valoraciones"));
        areaValoraciones.setText(ControladorCalificacion.crearValoraciones(ControladorUsuario.getUsuarioActivo()));

        // Panel scroll en la sección de valoraciones
        JScrollPane scrollValoraciones = new JScrollPane(areaValoraciones);
        scrollValoraciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollValoraciones.getVerticalScrollBar().setValue(0);
        
        int alturaLinea = areaValoraciones.getFontMetrics(areaValoraciones.getFont()).getHeight();
        scrollValoraciones.setPreferredSize(new Dimension(300, alturaLinea * 7));

        panelCalificaciones.add(scrollValoraciones, colocacionCalificacion);

        // -------------------------
        // SELECCIÓN DE PELÍCULA
        // -------------------------
        colocacionCalificacion.gridy = 1;
        colocacionCalificacion.gridx = 0;
        colocacionCalificacion.gridwidth = 1;
        colocacionCalificacion.weightx = 0;
        colocacionCalificacion.fill = GridBagConstraints.NONE;

        JLabel sPelicula = new JLabel("Selecciona una película:");
        panelCalificaciones.add(sPelicula, colocacionCalificacion);

        colocacionCalificacion.gridx = 1;
        colocacionCalificacion.fill = GridBagConstraints.HORIZONTAL;
        colocacionCalificacion.weightx = 1.0;

        JComboBox<Object> seleccionPeliculas = new JComboBox<>(catalogo.toArray(new Pelicula[0]));
        seleccionPeliculas.insertItemAt("--- Película ---", 0);
        Estilos.estilosCombox(seleccionPeliculas);
        panelCalificaciones.add(seleccionPeliculas, colocacionCalificacion);

        // -------------------------
        // CAMPO VALORACIÓN
        // -------------------------
        colocacionCalificacion.gridy = 2;
        colocacionCalificacion.gridx = 0;
        colocacionCalificacion.fill = GridBagConstraints.NONE;
        colocacionCalificacion.weightx = 0;

        JLabel sValoracion = new JLabel("Valore de 1 a 5:");
        panelCalificaciones.add(sValoracion, colocacionCalificacion);

        colocacionCalificacion.gridx = 1;
        colocacionCalificacion.fill = GridBagConstraints.HORIZONTAL;
        colocacionCalificacion.weightx = 1.0;

        JTextField lValoracion = new JTextField();
        Estilos.estilosInputsDatos(lValoracion);
        panelCalificaciones.add(lValoracion, colocacionCalificacion);

        // -------------------------
        // BOTÓN GUARDAR
        // -------------------------
        colocacionCalificacion.gridy = 3;
        colocacionCalificacion.gridx = 0;
        colocacionCalificacion.gridwidth = 2;
        colocacionCalificacion.fill = GridBagConstraints.NONE;

        JButton botonCalificacion = new JButton("Guardar Calificación");
        Estilos.estiloBotones(botonCalificacion);

        botonCalificacion.addActionListener((ActionEvent e) -> 
        {
            Object peliculaSeleccionada = seleccionPeliculas.getSelectedItem();

            if(!(peliculaSeleccionada instanceof Pelicula))
            {
                JOptionPane.showMessageDialog(PanelGenerador.getMain(),"Debes seleccionar una película","Pelicula no seleccionada", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Pelicula pelicula = (Pelicula) peliculaSeleccionada;
                String notaString =  lValoracion.getText().trim();

                if(notaString.isEmpty())
                {
                    JOptionPane.showMessageDialog(PanelGenerador.getMain(),"Debes valorar la película de 1 a 5","Valoración vacía", JOptionPane.ERROR_MESSAGE);
                }

                int nota = 0;

                try 
                {
                    nota = Integer.parseInt(notaString);
                } 
                catch (Exception a) 
                {
                    JOptionPane.showMessageDialog(PanelGenerador.getMain(),"Debes valorar la película con un número válido de 1 a 5","Valoración mal hecha", JOptionPane.ERROR_MESSAGE);
                }

                String mensaje = ControladorCalificacion.guardarCalificacion(pelicula, nota);

                if(mensaje == null)
                {
                    seleccionPeliculas.setSelectedIndex(0);
                    lValoracion.setText("");
                    JOptionPane.showMessageDialog(PanelGenerador.getMain(),"Calificiación registrada correctamente / Película marcada como vista","Calificación guardada", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(PanelGenerador.getMain(), mensaje,"Calificación no registrada", JOptionPane.ERROR_MESSAGE);
                }

                areaValoraciones.setText(ControladorCalificacion.crearValoraciones(ControladorUsuario.getUsuarioActivo()));
            }
        });

        panelCalificaciones.add(botonCalificacion, colocacionCalificacion);

        // -------------------------
        // CONFIGURACIÓN DEl PANEL DIVIDIDO
        // -------------------------
        panelDividido.setEnabled(false);
        panelDividido.setDividerSize(0);
        panelDividido.setLeftComponent(scrollCatalogo);
        panelDividido.setRightComponent(panelCalificaciones);
        
        bag.gridx = 0; bag.gridy = 2;
        bag.gridwidth = 2; bag.weightx = 1.0; bag.weighty = 1.0;
        bag.fill = GridBagConstraints.BOTH;
        panelContenido.add(panelDividido, bag);

        panelCental.add(panelContenido, BorderLayout.CENTER);

        // -------------------------
        // FOOTER
        // -------------------------
        JPanel footer = PanelFooter.crearFooter("© CineRoadmap", 1200, 30);
        panelCental.add(footer, BorderLayout.SOUTH);

        return panelCental;
    }
}

