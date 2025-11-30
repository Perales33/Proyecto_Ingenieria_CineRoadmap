package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelPeliculas 
{
    protected static JPanel crearPanelPeliculas()
    {
        ArrayList<Pelicula> catalogo = Pelicula.getCatalogo();

        JPanel panelCental = new JPanel(new BorderLayout());

        JPanel banner = PanelBanner.crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        bag.gridwidth = 2; bag.gridy = 1;
        JLabel peliculasLabel = new JLabel("Seccion de Películas");
        Estilos.estilosTitulosLRC(peliculasLabel);
        peliculasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenido.add(peliculasLabel, bag);
        
        bag.gridwidth = 2; bag.gridy = 2; bag.gridx = 0;
        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.7);

        JPanel panelCatalogo = new JPanel();
        panelCatalogo.setLayout(new BoxLayout(panelCatalogo, BoxLayout.Y_AXIS));
        panelCatalogo.setBorder(BorderFactory.createTitledBorder("Catálogo de Películas"));

        for(Pelicula p : catalogo)
        {
            panelCatalogo.add(crearPeliculas(p));
            panelCatalogo.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollCatalogo = new JScrollPane(panelCatalogo);
        scrollCatalogo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        SwingUtilities.invokeLater(() -> 
        {
            scrollCatalogo.getVerticalScrollBar().setValue(0);
            scrollCatalogo.getHorizontalScrollBar().setValue(0);
        });


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

        colocacionCalificacion.gridy = 0;
        colocacionCalificacion.gridx = 0;
        colocacionCalificacion.fill = GridBagConstraints.BOTH;
        colocacionCalificacion.weighty = 1.0;

        JTextArea areaValoraciones = new JTextArea();
        areaValoraciones.setEditable(false);
        Estilos.estilosTextArea(areaValoraciones);
        areaValoraciones.setBorder(BorderFactory.createTitledBorder("Tus valoraciones"));

        areaValoraciones.setText(crearValoraciones(Controlador.getUsuarioActivo()));


        JScrollPane scrollValoraciones = new JScrollPane(areaValoraciones);
        scrollValoraciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollValoraciones.getVerticalScrollBar().setValue(0);
        
        int alturaLinea = areaValoraciones.getFontMetrics(areaValoraciones.getFont()).getHeight();
        scrollValoraciones.setPreferredSize(new Dimension(300, alturaLinea * 7));

        panelCalificaciones.add(scrollValoraciones, colocacionCalificacion);

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
                JOptionPane.showMessageDialog(panelGenerador.mainPanel, "Debes seleccionar una película", "Pelicula no seleccionada", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Pelicula pelicula = (Pelicula) peliculaSeleccionada;
                String notaString =  lValoracion.getText().trim();

                if(notaString.isEmpty())
                {
                    JOptionPane.showMessageDialog(panelGenerador.mainPanel, "Debes valorar la película de 1 a 5", "Valoración vacía", JOptionPane.ERROR_MESSAGE);
                }
                int nota = 0;

                try 
                {
                    nota = Integer.parseInt(notaString);
                } 
                catch (Exception a) 
                {
                    JOptionPane.showMessageDialog(panelGenerador.mainPanel, "Debes valorar la película con un número válido de 1 a 5", "Valoración mal hecha", JOptionPane.ERROR_MESSAGE);
                }

                String mensaje = Controlador.guardarCalificacion(pelicula, nota);

                if(mensaje == null)
                {
                    seleccionPeliculas.setSelectedIndex(0);
                    lValoracion.setText("");
                    JOptionPane.showMessageDialog(panelGenerador.mainPanel, "Calificiación registrada correctamente / Película marcada como vista", "Calificación guardada", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(panelGenerador.mainPanel, mensaje, "Calificación no registrada", JOptionPane.ERROR_MESSAGE);
                }

                areaValoraciones.setText(crearValoraciones(Controlador.getUsuarioActivo()));
            }
        });

        panelCalificaciones.add(botonCalificacion, colocacionCalificacion);

        panelDividido.setEnabled(false);
        panelDividido.setDividerSize(0);
        panelDividido.setLeftComponent(scrollCatalogo);
        panelDividido.setRightComponent(panelCalificaciones);
        
        bag.gridx = 0; bag.gridy = 2;
        bag.gridwidth = 2; bag.weightx = 1.0; bag.weighty = 1.0;
        bag.fill = GridBagConstraints.BOTH;
        panelContenido.add(panelDividido, bag);

        panelCental.add(panelContenido, BorderLayout.CENTER);

        return panelCental;
    }

    private static JPanel crearPeliculas(Pelicula p)
    {
        Usuario u = Controlador.getUsuarioActivo();

        if (u == null) { return null; }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(p.getnombrePelicula()));

        ImageIcon icono;

        try
        {
            icono = new ImageIcon(PanelPeliculas.class.getResource("../../static/img" + p.getFoto()));
            if(icono.getImage() == null)
            {
                icono = new ImageIcon();
            }
        }
        catch(Exception e)
        {
            icono = new ImageIcon();
        }
        
        Image imagen = icono.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panel.add(imagenLabel, BorderLayout.WEST);

        JTextArea datosPelicula = new JTextArea();
        datosPelicula.setText
        (
            "\n" +
            "Año: " + p.getAnio() + "\n\n" +
            "Géneros: " + p.getGeneros() + "\n\n" +
            "Directores: " + p.getDirectores() + "\n\n" +
            "Actores: " + p.getActores() + "\n\n" +
            "Descripción: " + p.getDescripcion() + "\n\n"
        );
        Estilos.estilosTextArea(datosPelicula);

        panel.add(datosPelicula, BorderLayout.CENTER);
    
        return panel;
    }

    private static String crearValoraciones(Usuario u)
    {;
        if (u == null || Calificaciones.getListaCalificaciones().isEmpty()) { return "Actualmente no hay calificaciones"; }

        StringBuilder texto = new StringBuilder();

        for(Calificaciones c : Calificaciones.getListaCalificaciones())
        {
            if(c.getUsuario().equals(u))
            {
                texto.append(c.getPelicula().getnombrePelicula()).append(" (Nota: ").append(c.getCalificacion()).append(" / 5)\n");
            }
            
        }

        if(texto.length() == 0)
        {
            return "Actualmente no hay calificaciones";
        }
        return texto.toString();
    }
}
