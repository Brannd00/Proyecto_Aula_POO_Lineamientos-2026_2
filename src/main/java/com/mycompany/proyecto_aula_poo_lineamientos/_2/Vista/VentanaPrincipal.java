package com.mycompany.proyecto_aula_poo_lineamientos._2.Vista;

import com.mycompany.proyecto_aula_poo_lineamientos._2.Controlador.BarberiaControlador;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * 
 * 
 *
 * @author brann
 */
public class VentanaPrincipal extends JFrame {

    private static final String MENU = "menu";
    private static final String CLIENTES = "clientes";
    private static final String BARBEROS = "barberos";
    private static final String AGENDAR = "agendar";
    private static final String SERVICIOS = "servicios";
    private static final String AGENDA = "agenda";
    private static final String PAGOS = "pagos";
    private static final String REPORTES = "reportes";

    private static final Color MORADO = new Color(0x5B3FA6);

    private final CardLayout cartas = new CardLayout();
    private final JPanel contenedor = new JPanel(cartas);
    private final Map<String, String> subtitulos = new HashMap<>();
    private final JLabel lblSubtitulo = new JLabel(" ");
    private final JButton btnVolver = new JButton("← Menú principal");
    private final PanelAgendarCita panelAgendar;

    public VentanaPrincipal(BarberiaControlador controlador) {
        super(controlador.getNombreBarberia());
        this.panelAgendar = new PanelAgendarCita(controlador);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(crearEncabezado(controlador.getNombreBarberia()), BorderLayout.NORTH);
        add(contenedor, BorderLayout.CENTER);

        agregarPantalla(MENU, "Menú principal", crearMenu());
        agregarPantalla(CLIENTES, "Clientes", crearPantallaPendiente("Clientes"));
        agregarPantalla(BARBEROS, "Barberos", crearPantallaPendiente("Barberos"));
        agregarPantalla(AGENDAR, "Agenda tu cita en 3 pasos: tus datos, tus servicios y tu turno.", panelAgendar);
        agregarPantalla(SERVICIOS, "Servicios", crearPantallaPendiente("Servicios"));
        agregarPantalla(AGENDA, "Consultar agenda", crearPantallaPendiente("Consultar agenda"));
        agregarPantalla(PAGOS, "Pagos", crearPantallaPendiente("Pagos"));
        agregarPantalla(REPORTES, "Reportes", crearPantallaPendiente("Reportes"));

        btnVolver.addActionListener(e -> mostrar(MENU));
        mostrar(MENU);

        pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(Math.min(getWidth(), pantalla.width - 40), Math.min(getHeight(), pantalla.height - 80));
        setLocationRelativeTo(null);
    }

    public static void aplicarEstiloGlobal() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignorada) {
        }
        for (Object clave : Collections.list(UIManager.getDefaults().keys())) {
            Object valor = UIManager.get(clave);
            if (valor instanceof Font) {
                Font f = (Font) valor;
                UIManager.put(clave, new FontUIResource(f.deriveFont(Math.max(f.getSize2D(), 14f))));
            }
        }
    }


    private JPanel crearEncabezado(String nombreBarberia) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(MORADO);
        p.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel titulo = new JLabel(nombreBarberia);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));

        lblSubtitulo.setForeground(new Color(0xE6DDF7));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(lblSubtitulo);

        JPanel derecha = new JPanel(new GridBagLayout());
        derecha.setOpaque(false);
        derecha.add(btnVolver);

        p.add(textos, BorderLayout.CENTER);
        p.add(derecha, BorderLayout.EAST);
        return p;
    }

    private JPanel crearMenu() {
        JPanel p = new JPanel(new GridLayout(4, 2, 14, 14));
        p.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        p.add(crearBotonMenu("Clientes", () -> mostrar(CLIENTES)));
        p.add(crearBotonMenu("Barberos", () -> mostrar(BARBEROS)));
        p.add(crearBotonMenu("Agendar cita", () -> mostrar(AGENDAR)));
        p.add(crearBotonMenu("Servicios", () -> mostrar(SERVICIOS)));
        p.add(crearBotonMenu("Consultar agenda", () -> mostrar(AGENDA)));
        p.add(crearBotonMenu("Pagos", () -> mostrar(PAGOS)));
        p.add(crearBotonMenu("Reportes", () -> mostrar(REPORTES)));
        p.add(crearBotonMenu("Salir", this::salir));
        return p;
    }

    private JButton crearBotonMenu(String texto, Runnable accion) {
        JButton b = new JButton(texto);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 17f));
        b.setFocusPainted(false);
        b.addActionListener(e -> accion.run());
        return b;
    }

    private JPanel crearPantallaPendiente(String nombre) {
        JPanel p = new JPanel(new GridBagLayout());
        JLabel mensaje = new JLabel("La pantalla «" + nombre + "» está en construcción.");
        mensaje.setForeground(Color.GRAY);
        mensaje.setFont(mensaje.getFont().deriveFont(16f));
        p.add(mensaje);
        return p;
    }


    private void agregarPantalla(String clave, String subtitulo, JComponent panel) {
        contenedor.add(panel, clave);
        subtitulos.put(clave, subtitulo);
    }

    private void mostrar(String clave) {
        if (AGENDAR.equals(clave)) {
            panelAgendar.refrescar();
        }
        cartas.show(contenedor, clave);
        lblSubtitulo.setText(subtitulos.get(clave));
        btnVolver.setVisible(!MENU.equals(clave));
    }

    private void salir() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
