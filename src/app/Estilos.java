package app;

import javax.swing.*;
import java.awt.*;

public class Estilos 
{
    protected static void estiloBotones(JButton boton)
    {
        boton.setBackground(new Color(200, 0, 0));
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    protected static void estiloLabelLRC(JLabel label)
    {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 12));
    }

    protected static void estilosTitulosLRC(JLabel titulo)
    {
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
    }

    protected static void estilosInputsDatos(JTextField textfield)
    {
        textfield.setBackground(Color.WHITE);
        textfield.setForeground(Color.BLACK);
        textfield.setCaretColor(Color.BLACK);
        textfield.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textfield.setFont(new Font("Arial", Font.PLAIN, 12));
        textfield.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
    protected static void estilosInputsContrasenas(JPasswordField passwordField)
    {
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        passwordField.setEchoChar('*');
    }

    protected static void estilosTextArea(JTextArea textArea)
    {
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setFocusable(false);
        textArea.setHighlighter(null);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.ITALIC, 12));
        textArea.setCaretColor(textArea.getBackground());
    }

    protected static void estilosCombox(JComboBox comboBox)
    {
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        comboBox.setSelectedIndex(0);
    }
}
