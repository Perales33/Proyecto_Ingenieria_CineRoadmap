// Hecho por IA (Ejemplo como podría quedar)


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Optional;

/**
 * CineRoadmapGUI.java
 * Aplicación de escritorio en Java (Swing) para gestionar usuarios y valorar películas.
 *
 * El programa simula las estructuras de datos (ArrayLists) y las funcionalidades
 * de la versión anterior, pero con una interfaz gráfica de usuario.
 *
 * NOTA: Esta versión utiliza Mocks de datos y no persistencia (no guarda en un archivo o base de datos).
 */
public class CineRoadmapGUI extends JFrame {

    // --- 1. ESTRUCTURAS DE DATOS (Simulación de Clases y Datos) ---

    /** Representa un objeto Pelicula. */
    private static class Pelicula {
        int id;
        String nombrePelicula;
        int anio;
        String generos;
        String directores;
        String descripcion;

        public Pelicula(int id, String nombrePelicula, int anio, String generos, String directores, String descripcion) {
            this.id = id;
            this.nombrePelicula = nombrePelicula;
            this.anio = anio;
            this.generos = generos;
            this.directores = directores;
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return nombrePelicula + " (" + anio + ")";
        }
    }

    /** Representa un objeto Usuario. */
    private static class Usuario {
        int id;
        String contrasena;
        String email;
        String nombreUsuario;
        String foto;
        ArrayList<Pelicula> peliculasVistas;

        public Usuario(int id, String contrasena, String email, String nombreUsuario, String foto) {
            this.id = id;
            this.contrasena = contrasena;
            this.email = email;
            this.nombreUsuario = nombreUsuario;
            this.foto = foto;
            this.peliculasVistas = new ArrayList<>();
        }
    }

    /** Representa una Valoracion de una película por un usuario. */
    private static class Valoracion {
        int usuarioId;
        int peliculaId;
        int calificacion; // 1 a 5

        public Valoracion(int usuarioId, int peliculaId, int calificacion) {
            this.usuarioId = usuarioId;
            this.peliculaId = peliculaId;
            this.calificacion = calificacion;
        }
    }

    // --- 2. DATOS DE LA APLICACIÓN (ArrayLists y Variables de Estado) ---

    // Catálogo de películas (Datos Fijos - Mock)
    private static final ArrayList<Pelicula> catalogoPeliculas = new ArrayList<>();
    static {
        // Inicialización del catálogo (como Pelicula.cargarPeliculas)
        catalogoPeliculas.add(new Pelicula(0, "El Laberinto del Fauno", 2006, "Fantasía, Drama", "Guillermo del Toro", "En la España de 1944, una niña se adentra en un laberinto y descubre un mundo mágico."));
        catalogoPeliculas.add(new Pelicula(1, "Roma", 2018, "Drama", "Alfonso Cuarón", "Retrato de un año turbulento en la vida de una familia de clase media en la Ciudad de México de los 70."));
        catalogoPeliculas.add(new Pelicula(2, "Relatos Salvajes", 2014, "Comedia negra, Thriller", "Damián Szifron", "Seis historias cortas sobre la venganza y la frustración."));
        catalogoPeliculas.add(new Pelicula(3, "Amores Perros", 2000, "Drama", "Alejandro González Iñárritu", "Tres historias interconectadas a través de un accidente automovilístico."));
    }

    // Listas de datos dinámicos
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private ArrayList<Valoracion> listaValoraciones = new ArrayList<>();

    // Estado de la sesión
    private Usuario activeUser = null;
    private int nextUserId = 1;

    // --- 3. COMPONENTES DE LA INTERFAZ DE USUARIO ---

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Paneles de la aplicación (se usarán en el CardLayout)
    private JPanel loginPanel;
    private JPanel mainAppPanel;
    private JPanel registerPanel;
    private JPanel changePasswordPanel;
    private JPanel profilePanel;
    private JPanel moviesPanel;

    // --- 4. CONSTRUCTOR Y MÉTODOS DE INICIALIZACIÓN ---

    public CineRoadmapGUI() {
        // Configuración de la ventana principal
        setTitle("CineRoadmap - Aplicación de Escritorio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Tamaño inicial
        setLocationRelativeTo(null); // Centrar en la pantalla

        // Inicializar los paneles
        loginPanel = createLoginPanel();
        registerPanel = createRegisterPanel();
        changePasswordPanel = createChangePasswordPanel();
        mainAppPanel = createMainAppPanel();
        profilePanel = createProfilePanel();
        moviesPanel = createMoviesPanel();

        // Añadir paneles al CardLayout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(changePasswordPanel, "ChangePassword");
        mainPanel.add(mainAppPanel, "MainApp");
        mainPanel.add(profilePanel, "Profile");
        mainPanel.add(moviesPanel, "Movies");

        // Añadir el panel principal a la ventana
        add(mainPanel);

        // Mostrar la ventana de Login al inicio
        cardLayout.show(mainPanel, "Login");
        setVisible(true);
    }

    /**
     * Punto de entrada de la aplicación.
     */
    public static void main(String[] args) {
        // Asegura que la GUI se ejecute en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new CineRoadmapGUI());
    }

    // --- 5. MÉTODOS DE LÓGICA (Simulación del Controlador) ---

    /**
     * Maneja el intento de inicio de sesión.
     * @param credential Nombre de usuario o email.
     * @param password Contraseña.
     * @return true si el inicio de sesión es exitoso, false en caso contrario.
     */
    private boolean inicioSesion(String credential, String password) {
        if (listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados.", "Error de Inicio", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Optional<Usuario> userFound = listaUsuarios.stream()
                .filter(u -> (u.nombreUsuario.equals(credential) || u.email.equals(credential)) && u.contrasena.equals(password))
                .findFirst();

        if (userFound.isPresent()) {
            activeUser = userFound.get();
            JOptionPane.showMessageDialog(this, "¡Bienvenido, " + activeUser.nombreUsuario + "!", "Inicio Exitoso", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            activeUser = null;
            JOptionPane.showMessageDialog(this, "Usuario o Contraseña incorrectos.", "Error de Inicio", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Maneja el registro de un nuevo usuario.
     * @param nombre Nombre de usuario.
     * @param email Email.
     * @param contrasena Contraseña.
     * @return Mensaje de error o null si fue exitoso.
     */
    private String mostrarRegistro(String nombre, String email, String contrasena) {
        if (nombre.trim().isEmpty() || email.trim().isEmpty() || contrasena.trim().isEmpty()) {
            return "Todos los campos son obligatorios.";
        }
        boolean isDuplicate = listaUsuarios.stream().anyMatch(u -> u.nombreUsuario.equals(nombre) || u.email.equals(email));
        if (isDuplicate) {
            return "Ya se está usando este nombre de usuario o email.";
        }

        Usuario nuevoUsuario = new Usuario(nextUserId++, contrasena, email, nombre, "default.jpg");
        listaUsuarios.add(nuevoUsuario);
        return null; // Éxito
    }

    /**
     * Maneja el cambio de contraseña.
     * @return Mensaje de error o null si fue exitoso.
     */
    private String cambioContrasena(String credential, String currentPassword, String newPassword) {
        if (credential.trim().isEmpty() || currentPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {
            return "Todos los campos son obligatorios.";
        }

        Optional<Usuario> userOpt = listaUsuarios.stream()
                .filter(u -> u.nombreUsuario.equals(credential) || u.email.equals(credential))
                .findFirst();

        if (userOpt.isEmpty()) {
            return "El usuario no se ha podido encontrar.";
        }

        Usuario usuarioEncontrar = userOpt.get();

        if (!usuarioEncontrar.contrasena.equals(currentPassword)) {
            return "La contraseña actual no coincide.";
        }

        if (currentPassword.equals(newPassword)) {
            return "La nueva contraseña no puede ser igual a la actual.";
        }

        // Actualizar la contraseña
        usuarioEncontrar.contrasena = newPassword;
        return null; // Éxito
    }

    /**
     * Maneja la valoración de una película.
     * @param peliculaSeleccionada Pelicula a valorar.
     * @param nota Calificación (1-5).
     * @return Mensaje de error o null si fue exitoso.
     */
    private String valorarPelicula(Pelicula peliculaSeleccionada, int nota) {
        if (activeUser == null) {
            return "Error: No hay un usuario activo.";
        }
        if (nota < 1 || nota > 5) {
            return "Error: La nota debe ser un valor entero entre 1 y 5.";
        }

        // 1. Marcar película como vista (si no lo está)
        if (activeUser.peliculasVistas.stream().noneMatch(p -> p.id == peliculaSeleccionada.id)) {
            activeUser.peliculasVistas.add(peliculaSeleccionada);
        }

        // 2. Actualizar/Crear Calificación
        Optional<Valoracion> existingRatingOpt = listaValoraciones.stream()
                .filter(r -> r.usuarioId == activeUser.id && r.peliculaId == peliculaSeleccionada.id)
                .findFirst();

        if (existingRatingOpt.isPresent()) {
            // Actualizar
            existingRatingOpt.get().calificacion = nota;
        } else {
            // Crear
            listaValoraciones.add(new Valoracion(activeUser.id, peliculaSeleccionada.id, nota));
        }

        return null; // Éxito
    }

    // --- 6. MÉTODOS DE CREACIÓN DE PANELES (UI) ---

    /**
     * Crea el panel de inicio de sesión.
     */
    private JPanel createLoginPanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel title = new JLabel("¡Bienvenido a CineRoadmap!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(79, 70, 229)); // Color Índigo
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Campos de entrada
        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Usuario o Email:"), gbc);
        gbc.gridx = 1; panel.add(userField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; 
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; panel.add(passField, gbc);

        // Botones
        gbc.gridwidth = 2;
        gbc.gridy = 3; gbc.gridx = 0;
        JButton loginButton = new JButton("1. Iniciar Sesión");
        loginButton.setBackground(new Color(79, 70, 229));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        JButton registerButton = new JButton("2. Registrarse");
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        panel.add(registerButton, gbc);

        gbc.gridy = 5;
        JButton changePassButton = new JButton("3. Cambio de contraseña");
        changePassButton.addActionListener(e -> cardLayout.show(mainPanel, "ChangePassword"));
        panel.add(changePassButton, gbc);

        gbc.gridy = 6;
        JButton exitButton = new JButton("4. Salir");
        exitButton.setBackground(new Color(239, 68, 68)); // Rojo
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton, gbc);

        // Acción del botón de login
        loginButton.addActionListener((ActionEvent e) -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (inicioSesion(user, pass)) {
                // Actualizar el panel principal de la aplicación y mostrarlo
                updateMainAppPanel();
                cardLayout.show(mainPanel, "MainApp");
                userField.setText("");
                passField.setText("");
            }
        });

        return panel;
    }

    /**
     * Crea el panel de registro de usuario.
     */
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel title = new JLabel("Formulario de Registro", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(79, 70, 229));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Campos de entrada
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Nombre Usuario:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; panel.add(passField, gbc);

        // Botones
        gbc.gridwidth = 2;
        gbc.gridy = 4; gbc.gridx = 0;
        JButton registerButton = new JButton("Registrarse");
        registerButton.setBackground(new Color(79, 70, 229));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        panel.add(registerButton, gbc);

        gbc.gridy = 5;
        JButton backButton = new JButton("Volver al Inicio");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        panel.add(backButton, gbc);

        // Acción del botón de registro
        registerButton.addActionListener((ActionEvent e) -> {
            String error = mostrarRegistro(nameField.getText(), emailField.getText(), new String(passField.getPassword()));
            if (error == null) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente. Inicie sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                emailField.setText("");
                passField.setText("");
                cardLayout.show(mainPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(this, error, "Error de Registro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Crea el panel de cambio de contraseña.
     */
    private JPanel createChangePasswordPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel title = new JLabel("Cambio de Contraseña", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(79, 70, 229));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Campos de entrada
        JTextField credentialField = new JTextField(20);
        JPasswordField currentPassField = new JPasswordField(20);
        JPasswordField newPassField = new JPasswordField(20);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Usuario o Email:"), gbc);
        gbc.gridx = 1; panel.add(credentialField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Contraseña Actual:"), gbc);
        gbc.gridx = 1; panel.add(currentPassField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Nueva Contraseña:"), gbc);
        gbc.gridx = 1; panel.add(newPassField, gbc);

        // Botones
        gbc.gridwidth = 2;
        gbc.gridy = 4; gbc.gridx = 0;
        JButton changeButton = new JButton("Cambiar Contraseña");
        changeButton.setBackground(new Color(79, 70, 229));
        changeButton.setForeground(Color.WHITE);
        changeButton.setFocusPainted(false);
        panel.add(changeButton, gbc);

        gbc.gridy = 5;
        JButton backButton = new JButton("Volver al Inicio");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        panel.add(backButton, gbc);

        // Acción del botón de cambio
        changeButton.addActionListener((ActionEvent e) -> {
            String error = cambioContrasena(credentialField.getText(), new String(currentPassField.getPassword()), new String(newPassField.getPassword()));
            if (error == null) {
                JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                credentialField.setText("");
                currentPassField.setText("");
                newPassField.setText("");
                cardLayout.show(mainPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(this, error, "Error de Cambio", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Crea el panel del Menú Principal (una vez logueado).
     */
    private JPanel createMainAppPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 250));

        JLabel titleLabel = new JLabel("", SwingConstants.CENTER); // Se actualizará al hacer login
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(79, 70, 229));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        buttonPanel.setBackground(new Color(245, 245, 250));

        // Botones de las opciones
        JButton profileButton = new JButton("1. Perfil Usuario");
        profileButton.addActionListener(e -> { updateProfilePanel(); cardLayout.show(mainPanel, "Profile"); });
        stylePrimaryButton(profileButton);

        JButton moviesButton = new JButton("2. Películas");
        moviesButton.addActionListener(e -> { updateMoviesPanel(); cardLayout.show(mainPanel, "Movies"); });
        stylePrimaryButton(moviesButton);

        JButton achievementsButton = new JButton("3. Logros e Insignias");
        achievementsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE));
        stylePrimaryButton(achievementsButton);

        JButton challengesButton = new JButton("4. Retos");
        challengesButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE));
        stylePrimaryButton(challengesButton);

        JButton logoutButton = new JButton("5. Cerrar Sesión");
        logoutButton.addActionListener(e -> {
            activeUser = null;
            JOptionPane.showMessageDialog(this, "Sesión cerrada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Login");
        });
        styleSecondaryButton(logoutButton);

        JButton exitButton = new JButton("6. Salir");
        exitButton.addActionListener(e -> System.exit(0));
        styleDangerButton(exitButton);

        buttonPanel.add(profileButton);
        buttonPanel.add(moviesButton);
        buttonPanel.add(achievementsButton);
        buttonPanel.add(challengesButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de perfil de usuario.
     */
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 250));

        // Título
        JLabel titleLabel = new JLabel("Perfil de Usuario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(79, 70, 229));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Contenido Central
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBackground(new Color(245, 245, 250));

        // Panel de Información
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Datos Personales"));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setOpaque(true);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        contentPanel.add(infoPanel);

        // Panel de Películas Vistas
        JPanel moviesPanel = new JPanel(new BorderLayout());
        moviesPanel.setBorder(BorderFactory.createTitledBorder("Películas Vistas"));
        moviesPanel.setBackground(Color.WHITE);
        moviesPanel.setOpaque(true);

        JTextArea moviesArea = new JTextArea();
        moviesArea.setEditable(false);
        moviesArea.setLineWrap(true);
        moviesArea.setWrapStyleWord(true);
        moviesPanel.add(new JScrollPane(moviesArea), BorderLayout.CENTER);
        contentPanel.add(moviesPanel);

        panel.add(contentPanel, BorderLayout.CENTER);

        // Botón de Volver
        JButton backButton = new JButton("Volver al Menú Principal");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainApp"));
        styleSecondaryButton(backButton);
        panel.add(backButton, BorderLayout.SOUTH);

        // Almacenar referencias para actualización
        panel.putClientProperty("infoArea", infoArea);
        panel.putClientProperty("moviesArea", moviesArea);

        return panel;
    }

    /**
     * Actualiza el contenido del panel de perfil con la información del usuario activo.
     */
    private void updateProfilePanel() {
        if (activeUser == null) return;

        JTextArea infoArea = (JTextArea) profilePanel.getClientProperty("infoArea");
        JTextArea moviesArea = (JTextArea) profilePanel.getClientProperty("moviesArea");

        // Actualizar información del usuario
        infoArea.setText(String.format(
            "Nombre: %s\nEmail: %s\nID: %d\nFoto: %s",
            activeUser.nombreUsuario, activeUser.email, activeUser.id, activeUser.foto
        ));

        // Actualizar películas vistas
        StringBuilder sb = new StringBuilder();
        if (activeUser.peliculasVistas.isEmpty()) {
            sb.append("No ha visto ninguna película todavía.");
        } else {
            for (Pelicula p : activeUser.peliculasVistas) {
                sb.append("• ").append(p.nombrePelicula).append(" (").append(p.anio).append(")\n");
            }
        }
        moviesArea.setText(sb.toString());
    }

    /**
     * Crea el panel de películas y valoración.
     */
    private JPanel createMoviesPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 250));

        // Título
        JLabel titleLabel = new JLabel("Catálogo y Valoración de Películas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(79, 70, 229));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Contenido central: Catálogo a la izquierda, Valoración a la derecha
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.7); // El catálogo ocupa más espacio

        // --- Panel de Catálogo ---
        JPanel cataloguePanel = new JPanel(new BorderLayout());
        cataloguePanel.setBorder(BorderFactory.createTitledBorder("Catálogo Completo"));
        cataloguePanel.setBackground(Color.WHITE);

        // Crear lista de películas para visualización
        JTextArea catalogueArea = new JTextArea();
        catalogueArea.setEditable(false);
        catalogueArea.setLineWrap(true);
        catalogueArea.setWrapStyleWord(true);
        JScrollPane scrollCatalogue = new JScrollPane(catalogueArea);
        cataloguePanel.add(scrollCatalogue, BorderLayout.CENTER);
        splitPane.setLeftComponent(cataloguePanel);
        panel.putClientProperty("catalogueArea", catalogueArea);


        // --- Panel de Valoración ---
        JPanel ratingPanel = new JPanel(new GridBagLayout());
        ratingPanel.setBorder(BorderFactory.createTitledBorder("Valorar Película"));
        ratingPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        JLabel currentRatingsLabel = new JLabel("Tus Calificaciones Anteriores:");
        gbc.gridy = 0; gbc.gridx = 0; ratingPanel.add(currentRatingsLabel, gbc);

        JTextArea currentRatingsArea = new JTextArea(5, 1);
        currentRatingsArea.setEditable(false);
        currentRatingsArea.setLineWrap(true);
        currentRatingsArea.setWrapStyleWord(true);
        JScrollPane scrollRatings = new JScrollPane(currentRatingsArea);
        gbc.gridy = 1; gbc.weighty = 0.5; gbc.fill = GridBagConstraints.BOTH; ratingPanel.add(scrollRatings, gbc);
        panel.putClientProperty("currentRatingsArea", currentRatingsArea);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.gridy = 2; gbc.gridx = 0; ratingPanel.add(new JLabel("Selecciona Película:"), gbc);

        JComboBox<Pelicula> movieComboBox = new JComboBox<>(catalogoPeliculas.toArray(new Pelicula[0]));
        movieComboBox.insertItemAt(null, 0); // Placeholder
        movieComboBox.setSelectedIndex(0);
        gbc.gridy = 3; ratingPanel.add(movieComboBox, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 4; gbc.gridx = 0; ratingPanel.add(new JLabel("Nota (1 a 5):"), gbc);

        JTextField ratingField = new JTextField(5);
        gbc.gridx = 1; ratingPanel.add(ratingField, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 5; gbc.gridx = 0;
        JButton rateButton = new JButton("Guardar Calificación");
        stylePrimaryButton(rateButton);
        ratingPanel.add(rateButton, gbc);

        splitPane.setRightComponent(ratingPanel);
        panel.add(splitPane, BorderLayout.CENTER);


        // Botón de Volver
        JButton backButton = new JButton("Volver al Menú Principal");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainApp"));
        styleSecondaryButton(backButton);
        panel.add(backButton, BorderLayout.SOUTH);

        // Acción del botón de valoración
        rateButton.addActionListener((ActionEvent e) -> {
            Pelicula selected = (Pelicula) movieComboBox.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una película.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int nota = Integer.parseInt(ratingField.getText().trim());
                String error = valorarPelicula(selected, nota);
                if (error == null) {
                    JOptionPane.showMessageDialog(this, "¡Calificación guardada!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    ratingField.setText("");
                    movieComboBox.setSelectedIndex(0);
                    // Refrescar ambas áreas
                    updateMoviesPanel();
                    updateProfilePanel();
                } else {
                    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La nota debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Actualiza el contenido del panel de películas.
     */
    private void updateMoviesPanel() {
        if (activeUser == null) return;

        JTextArea catalogueArea = (JTextArea) moviesPanel.getClientProperty("catalogueArea");
        JTextArea currentRatingsArea = (JTextArea) moviesPanel.getClientProperty("currentRatingsArea");

        // 1. Actualizar Catálogo (Información de la Película)
        StringBuilder catSb = new StringBuilder();
        for (Pelicula p : catalogoPeliculas) {
            catSb.append(String.format(
                "--- %s (%d) ---\nGéneros: %s\nDirector: %s\nDescripción: %s\n\n",
                p.nombrePelicula, p.anio, p.generos, p.directores, p.descripcion
            ));
        }
        catalogueArea.setText(catSb.toString());

        // 2. Actualizar Calificaciones del Usuario
        StringBuilder ratingsSb = new StringBuilder();
        long ratedCount = listaValoraciones.stream().filter(r -> r.usuarioId == activeUser.id).count();

        if (ratedCount == 0) {
            ratingsSb.append("No has valorado ninguna película todavía.");
        } else {
            for (Valoracion v : listaValoraciones) {
                if (v.usuarioId == activeUser.id) {
                    Pelicula p = catalogoPeliculas.stream().filter(cp -> cp.id == v.peliculaId).findFirst().orElse(null);
                    if (p != null) {
                        ratingsSb.append(String.format("• %s: %d/5\n", p.nombrePelicula, v.calificacion));
                    }
                }
            }
        }
        currentRatingsArea.setText(ratingsSb.toString());
    }

    /**
     * Actualiza el contenido del panel principal de la aplicación.
     */
    private void updateMainAppPanel() {
        JLabel titleLabel = (JLabel) ((BorderLayout) mainAppPanel.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        if (activeUser != null && titleLabel != null) {
            titleLabel.setText("Menú Principal | Hola, " + activeUser.nombreUsuario);
        }
    }

    // --- 7. MÉTODOS DE ESTILO ---

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(79, 70, 229));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(79, 70, 229));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(79, 70, 229), 2));
    }

    private void styleDangerButton(JButton button) {
        button.setBackground(new Color(239, 68, 68)); // Rojo
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
