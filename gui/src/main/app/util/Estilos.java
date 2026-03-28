package main.app.util;

import javax.swing.*;
import java.awt.*;

public class Estilos 
{
    // -------------------------
    // ESTILO BOTONES
    // -------------------------
    public static void estiloBotones(JButton boton)
    {
        boton.setBackground(new Color(200, 0, 0));  // color de fondo rojo
        boton.setForeground(Color.WHITE);           // texto blanco
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // cursor tipo mano
    }

    // -------------------------
    // ESTILO LABELS PEQUEÑOS (etiquetas de formulario)
    // -------------------------
    public static void estiloLabelLRC(JLabel label)
    {
        label.setForeground(Color.WHITE);          // texto blanco
        label.setFont(new Font("Arial", Font.BOLD, 12)); // fuente Arial negrita tamaño 12
    }

    // -------------------------
    // ESTILO TITULOS (grandes)
    // -------------------------
    public static void estilosTitulosLRC(JLabel titulo)
    {
        titulo.setFont(new Font("Arial", Font.BOLD, 18)); // Arial negrita tamaño 18
        titulo.setForeground(Color.WHITE);               // texto blanco
    }

    // -------------------------
    // ESTILO CAMPOS DE TEXTO (JTextField)
    // -------------------------
    public static void estilosInputsDatos(JTextField textfield)
    {
        textfield.setBackground(Color.WHITE);        // fondo blanco
        textfield.setForeground(Color.GRAY);         // texto gris
        textfield.setCaretColor(Color.BLACK);        // cursor negro
        textfield.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textfield.setFont(new Font("Arial", Font.PLAIN, 12));
        textfield.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // borde blanco
        textfield.setFocusTraversalKeysEnabled(false); // deshabilita tabulación automática
    }

    // -------------------------
    // ESTILO CAMPOS CONTRASEÑA (JPasswordField)
    // -------------------------
    public static void estilosInputsContrasenas(JPasswordField passwordField)
    {
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.GRAY);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField.setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); // sin borde visible
        passwordField.setEchoChar('*'); // caracter de ocultamiento
        passwordField.setFocusTraversalKeysEnabled(false);
    }

    // -------------------------
    // ESTILO TEXTAREA (área de texto no editable)
    // -------------------------
    public static void estilosTextArea(JTextArea textArea)
    {
        textArea.setEditable(false);
        textArea.setLineWrap(true);        // ajuste de línea automático
        textArea.setFocusable(false);      // no se puede enfocar
        textArea.setHighlighter(null);     // sin resaltado
        textArea.setWrapStyleWord(true);   // ajuste por palabra
        textArea.setFont(new Font("Arial", Font.ITALIC, 12));
        textArea.setCaretColor(textArea.getBackground()); // cursor invisible
    }

    // -------------------------
    // ESTILO COMBOBOX (JComboBox)
    // -------------------------
    public static void estilosCombox(JComboBox comboBox)
    {
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        comboBox.setSelectedIndex(0); // inicializa en el primer elemento
    }
}
