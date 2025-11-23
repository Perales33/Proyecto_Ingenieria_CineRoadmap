import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

class interfaz extends JFrame
{
    public static void main(String[] args) {
        new interfaz();
    }

    static CardLayout colocacion = new CardLayout();
    
    public static CardLayout getColocacion()
    {
        return colocacion;
    }
    
    JPanel mainPanel = new JPanel(colocacion);

    JPanel loginPanel;
    JPanel registrarPanel;
    JPanel cambioContrasenaPanel;
    JPanel peliculasPanel;
    JPanel perfilPanel;
    JPanel appPanel;

    public interfaz()
    {
        loginPanel = crearloginPanel();
        registrarPanel = crearPanelRegistro();
        cambioContrasenaPanel = crearPanelContrasena();
        appPanel = crearPanelInicio();
        peliculasPanel = crearPanelPeliculas();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrarPanel, "Registro");
        mainPanel.add(cambioContrasenaPanel, "CambioContrasena");
        mainPanel.add(appPanel, "Inicio");
        mainPanel.add(peliculasPanel, "Peliculas");

        add(mainPanel);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        colocacion.show(mainPanel, "Login");
        setVisible(true);
    }

    private JPanel crearloginPanel()
    {
        JPanel panelCental = new JPanel(new GridBagLayout());
        panelCental.setBackground(new Color(200, 200, 200));
        panelCental.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        JLabel titulo = new JLabel("¡Bienvenido a CineRoadmap!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(70,80,90));
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 2;
        panelCental.add(titulo, bag);
        
        JLabel labelNombre = new JLabel("Usuario o Email:");
        bag.gridwidth = 1; bag.gridy = 1; bag.gridx = 0;
        panelCental.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        panelCental.add(campoNombre, bag);
        
        bag.gridwidth = 2; bag.gridy = 3; bag.gridx = 0; 
        JLabel labelContrasena = new JLabel("Contraseña:");
        panelCental.add(labelContrasena, bag);
        
        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        panelCental.add(campoContrasena, bag);

        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        botonVer.setBorderPainted(false);
        botonVer.setContentAreaFilled(false);
        botonVer.addActionListener(e -> Eventos.verContrasena(campoContrasena));
        panelCental.add(botonVer, bag);
        
        bag.gridwidth = 2; bag.gridy = 4; bag.gridx = 0;
        JButton botonInicio = new JButton("Iniciar Sesión");
        estiloBotones(botonInicio);
        botonInicio.addActionListener(e -> colocacion.show(mainPanel, "Inicio"));
        panelCental.add(botonInicio, bag);

        bag.gridy = 5;
        JButton botonRegistro = new JButton("Registrarse");
        estiloBotones(botonRegistro);
        botonRegistro.addActionListener(e -> colocacion.show(mainPanel, "Registro"));
        panelCental.add(botonRegistro, bag);
        
        bag.gridy = 6;
        JButton botonCambioContrasena = new JButton("Cambio de contraseña");
        estiloBotones(botonCambioContrasena);
        botonCambioContrasena.addActionListener(e -> colocacion.show(mainPanel, "CambioContrasena"));
        panelCental.add(botonCambioContrasena, bag);

        return panelCental;
    }

    private JPanel crearPanelRegistro()
    {
        JPanel panelCental = new JPanel(new GridBagLayout());
        panelCental.setBackground(new Color(200, 200, 200));
        panelCental.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        JLabel titulo = new JLabel("Formulario de Registro", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(70,80,90));
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 2;
        panelCental.add(titulo, bag);
        
        JLabel labelNombre = new JLabel("Nombre de usuario:");
        bag.gridwidth = 1; bag.gridy = 1; bag.gridx = 0;
        panelCental.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        panelCental.add(campoNombre, bag);
        
        JLabel labelEmail = new JLabel("Email:");
        bag.gridwidth = 1; bag.gridy = 2; bag.gridx = 0;
        panelCental.add(labelEmail, bag);

        bag.gridx = 1;
        JTextField campoEmail = new JTextField(20);
        panelCental.add(campoEmail, bag);
        
        bag.gridwidth = 2; bag.gridy = 3; bag.gridx = 0; 
        JLabel labelContrasena = new JLabel("Contraseña:");
        panelCental.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        panelCental.add(campoContrasena, bag);

        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        botonVer.setBorderPainted(false);
        botonVer.setContentAreaFilled(false);
        botonVer.addActionListener(e -> Eventos.verContrasena(campoContrasena));
        panelCental.add(botonVer, bag);

        bag.gridwidth = 2; bag.gridy = 4; bag.gridx = 0;
        JButton botonRegistro = new JButton("Registrarse");
        estiloBotones(botonRegistro);
        botonRegistro.addActionListener(e -> colocacion.show(mainPanel, "Registro"));
        panelCental.add(botonRegistro, bag);

        bag.gridy = 5;
        JButton botonInicio = new JButton("Volver al Login");
        estiloBotones(botonInicio);
        botonInicio.addActionListener(e -> colocacion.show(mainPanel, "Login"));
        panelCental.add(botonInicio, bag);

        return panelCental;
    }

    private JPanel crearPanelContrasena()
    {
        JPanel panelCental = new JPanel(new GridBagLayout());
        panelCental.setBackground(new Color(200, 200, 200));
        panelCental.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        JLabel titulo = new JLabel("Formulario de cambio de contraseña", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(70,80,90));
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 2;
        panelCental.add(titulo, bag);
        
        JLabel labelNombre = new JLabel("Nombre de usuario:");
        bag.gridwidth = 1; bag.gridy = 1; bag.gridx = 0;
        panelCental.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        panelCental.add(campoNombre, bag);
        
        JLabel labelContrasena = new JLabel("Contraseña:");
        bag.gridwidth = 1; bag.gridy = 2; bag.gridx = 0;
        panelCental.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        panelCental.add(campoContrasena, bag);
        
        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        botonVer.setBorderPainted(false);
        botonVer.setContentAreaFilled(false);
        botonVer.addActionListener(e -> Eventos.verContrasena(campoContrasena));
        panelCental.add(botonVer, bag);

        bag.gridwidth = 1; bag.gridy = 3; bag.gridx = 0; 
        JLabel labelConfirmacion = new JLabel("Confirmar contraseña:");
        panelCental.add(labelConfirmacion, bag);

        bag.gridx = 1;
        JPasswordField campoConfirmacion = new JPasswordField(20);
        panelCental.add(campoConfirmacion, bag);

        bag.gridx = 2;
        JButton botonVerConfirmacion = new JButton("👁");
        botonVerConfirmacion.setBorderPainted(false);
        botonVerConfirmacion.setContentAreaFilled(false);
        botonVerConfirmacion.addActionListener(e -> Eventos.verContrasena(campoConfirmacion));
        panelCental.add(botonVerConfirmacion, bag);

        bag.gridwidth = 2; bag.gridy = 4; bag.gridx = 0;
        JButton botonRegistro = new JButton("Cambio de Contraseña");
        estiloBotones(botonRegistro);
        botonRegistro.addActionListener(e -> colocacion.show(mainPanel, "CambioContrasena"));
        panelCental.add(botonRegistro, bag);

        bag.gridy = 5;
        JButton botonInicio = new JButton("Volver al Login");
        estiloBotones(botonInicio);
        botonInicio.addActionListener(e -> colocacion.show(mainPanel, "Login"));
        panelCental.add(botonInicio, bag);

        return panelCental;
    }

    private JPanel crearBanner()
    {
        JPanel panelBanner = new JPanel(new GridLayout(1, 5, 10, 0));
        panelBanner.setBackground(new Color(180,180,180));

        JLabel labelNombre = new JLabel("Inicio", SwingConstants.CENTER);
        JLabel labelPeliculas = new JLabel("Peliculas", SwingConstants.CENTER);
        JLabel labelLogrInsig = new JLabel("Logros e Insignias", SwingConstants.CENTER);
        JLabel labelRetos = new JLabel("Retos", SwingConstants.CENTER);
        JLabel labelPerfil = new JLabel("Perfil", SwingConstants.CENTER);
        JLabel labelCerrarSesion = new JLabel("Cerrar Sesión", SwingConstants.CENTER);

        JLabel labels[] = {labelNombre, labelPeliculas, labelLogrInsig, labelRetos, labelPerfil, labelCerrarSesion};

        String pantallas[] = {"Inicio", "Peliculas", "LogrosInsignias", "Retos", "Perfil", "Login"};

        for(int i = 0; i < labels.length; i++)
        {
            JLabel label = labels[i];
            String pantalla = pantallas[i];

            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) 
                {
                    if(pantalla.equals("LogrosInsignias") || pantalla.equals("Retos"))
                    {
                        JOptionPane.showMessageDialog(null, "Sección todavía no implementada","Información" ,JOptionPane.INFORMATION_MESSAGE);
                    }
                    colocacion.show(mainPanel, pantalla);
                }
            });
            panelBanner.add(label);
        }

        return panelBanner;
    }


    private JPanel crearPanelInicio()
    {
        JPanel panelCental = new JPanel(new BorderLayout());

        JPanel banner = crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(new Color(200, 200, 200));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        bag.gridwidth = 2; bag.gridy = 3; bag.gridx = 0;
        JButton botonInicio = new JButton("CerrarSesion");
        estiloBotones(botonInicio);
        botonInicio.addActionListener(e -> colocacion.show(mainPanel, "Login"));
        panelContenido.add(botonInicio, bag);

        panelCental.add(panelContenido, BorderLayout.CENTER);

        return panelCental;
    }

    private JPanel crearPanelPeliculas()
    {
        JPanel panelCental = new JPanel(new BorderLayout());

        JPanel banner = crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(new Color(200, 200, 200));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        bag.gridwidth = 2; bag.gridy = 1; bag.gridx = 0;
        JLabel peliculasLabel = new JLabel("Seccion de Películas");
        panelContenido.add(peliculasLabel, bag);

        bag.gridwidth = 2; bag.gridy = 2; bag.gridx = 0;
        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.7);

        JPanel panelCatalogo = new JPanel(new BorderLayout());
        panelCatalogo.setBorder(BorderFactory.createTitledBorder("Catálogo de Películas"));

        JTextArea areaPeliculas = new JTextArea(10, 30);
        areaPeliculas.setEditable(false);
        areaPeliculas.setLineWrap(true);
        areaPeliculas.setWrapStyleWord(true);

        JScrollPane scrollCatalogo = new JScrollPane(areaPeliculas);
        panelCatalogo.add(scrollCatalogo, BorderLayout.CENTER);
        panelCatalogo.putClientProperty("areaPeliculas", areaPeliculas);

        JPanel panelCalificaciones = new JPanel(new GridBagLayout());
        panelCalificaciones.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0), 
                BorderFactory.createTitledBorder("Valoración de Películas")
            )
        );
        GridBagConstraints colocacionCalificiacion = new GridBagConstraints();
        colocacionCalificiacion.insets = new Insets(8, 8, 8,8);
        colocacionCalificiacion.fill = GridBagConstraints.HORIZONTAL;
        colocacionCalificiacion.gridwidth = 2;

        JLabel labelCP = new JLabel("Tus calificaciones");
        colocacionCalificiacion.gridy = 0; colocacionCalificiacion.gridx = 0;
        panelCalificaciones.add(labelCP, colocacionCalificiacion);

        JTextArea areaCP = new JTextArea(5,1);
        colocacionCalificiacion.gridy = 1; colocacionCalificiacion.weighty = 0.5; colocacionCalificiacion.fill = GridBagConstraints.BOTH; 
        areaCP.setEditable(false);
        panelCalificaciones.add(areaCP, colocacionCalificiacion);

        colocacionCalificiacion.gridy = 2; 
        colocacionCalificiacion.fill = GridBagConstraints.HORIZONTAL;
        JLabel sPelicula = new JLabel("Selecciona una película:");
        panelCalificaciones.add(sPelicula, colocacionCalificiacion);
        
        colocacionCalificiacion.gridy = 3;
        JTextField lPeliculas = new JTextField();
        panelCalificaciones.add(lPeliculas, colocacionCalificiacion);

        colocacionCalificiacion.gridwidth = 1;
        colocacionCalificiacion.gridy = 4; colocacionCalificiacion.gridx = 0; 
        colocacionCalificiacion.fill = GridBagConstraints.HORIZONTAL;
        JLabel sValoracion = new JLabel("Valore de 1 a 5:");
        panelCalificaciones.add(sValoracion, colocacionCalificiacion);
        
        colocacionCalificiacion.gridy = 4; colocacionCalificiacion.gridx = 1;
        JTextField lValoracion = new JTextField();
        panelCalificaciones.add(lValoracion, colocacionCalificiacion);
        
        colocacionCalificiacion.gridy = 5; colocacionCalificiacion.gridx = 0;
        colocacionCalificiacion.gridwidth = 2;
        JButton botonCalificiacion = new JButton("Guardar Calificación");
        panelCalificaciones.add(botonCalificiacion, colocacionCalificiacion);

        panelDividido.setLeftComponent(panelCatalogo);
        panelDividido.setRightComponent(panelCalificaciones);
        

        panelContenido.add(panelDividido, bag);


        panelCental.add(panelContenido, BorderLayout.CENTER);

        return panelCental;
    }

    

    private void estiloBotones(JButton boton)
    {
        boton.setBackground(new Color(200, 0, 0));
        boton.setForeground(Color.WHITE);
    }
}

