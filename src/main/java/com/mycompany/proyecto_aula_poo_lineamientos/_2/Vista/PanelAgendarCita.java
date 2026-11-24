package com.mycompany.proyecto_aula_poo_lineamientos._2.Vista;

import com.mycompany.proyecto_aula_poo_lineamientos._2.Controlador.BarberiaControlador;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Barbero;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Cita;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.DetalleCita;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Servicio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 *
 * @author masdp
 */
public class PanelAgendarCita extends JPanel {

    private static final Locale LOCALE_CO = Locale.forLanguageTag("es-CO");
    private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", LOCALE_CO);
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("h:mm a", LOCALE_CO);
    private static final int DIAS_VISIBLES = 14;

    private static final Color MORADO = new Color(0x5B3FA6);
    private static final Color MORADO_APAGADO = new Color(0xDDD5EE);
    private static final Color FONDO_RESUMEN = new Color(0xF6F3FC);
    private static final Color GRIS_AYUDA = new Color(0x666666);
    private static final Color VERDE = new Color(0x2E7D32);
    private static final Color NARANJA = new Color(0xB26A00);

    private final BarberiaControlador controlador;
    private final List<Servicio> servicios;

    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtTelefono = new JTextField(20);
    private final JTextField txtCorreo = new JTextField(20);
    private final List<JCheckBox> checksServicios = new ArrayList<>();
    private final JComboBox<Barbero> comboBarbero = new JComboBox<>();
    private final JComboBox<LocalDate> comboDia = new JComboBox<>();
    private final JComboBox<LocalTime> comboHora = new JComboBox<>();
    private final JLabel lblDisponibilidad = new JLabel(" ");
    private final JLabel lblResumen = new JLabel();
    private final JLabel lblFaltan = new JLabel();
    private final JButton btnAgendar = new JButton("Agendar cita");
    private final JButton btnLimpiar = new JButton("Empezar de nuevo");

    private boolean actualizandoHoras = false;

    public PanelAgendarCita(BarberiaControlador controlador) {
        super(new BorderLayout());
        this.controlador = controlador;
        this.servicios = controlador.listarServicios();

        add(crearPasos(), BorderLayout.CENTER);
        add(crearPanelResumen(), BorderLayout.EAST);

        cargarCombos();
        conectarEventos();
        actualizarHoras();
    }

    public void refrescar() {
        actualizarHoras();
    }


    private JScrollPane crearPasos() {
        JPanel pasos = new JPanel();
        pasos.setLayout(new BoxLayout(pasos, BoxLayout.Y_AXIS));
        pasos.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        pasos.add(crearPasoDatos());
        pasos.add(Box.createVerticalStrut(10));
        pasos.add(crearPasoServicios());
        pasos.add(Box.createVerticalStrut(10));
        pasos.add(crearPasoTurno());

        JScrollPane scroll = new JScrollPane(pasos,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearPasoDatos() {
        JPanel p = crearPaso("· Datos del cliente", "Escribe tus datos para poder reservar tu cita.");
        txtNombre.setToolTipText("Escribe tu nombre y apellido");
        txtTelefono.setToolTipText("Solo números, entre 7 y 10 dígitos");
        txtCorreo.setToolTipText("Opcional");
        agregarFila(p, 1, "Nombre completo *", txtNombre);
        agregarFila(p, 2, "Teléfono * (solo números)", txtTelefono);
        agregarFila(p, 3, "Correo (opcional)", txtCorreo);
        return fijarAlto(p);
    }

    private JPanel crearPasoServicios() {
        JPanel p = crearPaso("· Servicios",
                "Marca uno o más servicios. Cada cita dura máximo "
                + BarberiaControlador.TURNO_MINUTOS + " minutos en total.");
        int fila = 1;
        for (Servicio s : servicios) {
            JCheckBox check = new JCheckBox("<html><b>" + esc(s.getNombre()) + "</b> &nbsp; "
                    + dinero(s.getPrecio()) + " · " + s.getDuracionMinutos() + " min<br>"
                    + "<span style='color:#666666'>" + esc(s.getDescripcion()) + "</span></html>");
            check.setOpaque(false);
            checksServicios.add(check);

            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = fila++;
            g.gridwidth = 2;
            g.anchor = GridBagConstraints.WEST;
            g.fill = GridBagConstraints.HORIZONTAL;
            g.weightx = 1;
            g.insets = new Insets(3, 0, 3, 0);
            p.add(check, g);
        }
        return fijarAlto(p);
    }

    private JPanel crearPasoTurno() {
        JPanel p = crearPaso("·Turno del cliente",
                "Escoge con quién quieres atenderte, el día y la hora. Solo verás horas libres.");
        agregarFila(p, 1, "Barbero", comboBarbero);
        agregarFila(p, 2, "Día", comboDia);
        agregarFila(p, 3, "Hora", comboHora);

        lblDisponibilidad.setForeground(GRIS_AYUDA);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 1;
        g.gridy = 4;
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(2, 6, 0, 0);
        p.add(lblDisponibilidad, g);
        return fijarAlto(p);
    }

    private JPanel crearPanelResumen() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(FONDO_RESUMEN);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(0xD9D0EE)),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        p.setPreferredSize(new Dimension(310, 100));

        JLabel titulo = new JLabel("Resumen de tu cita");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 17f));
        titulo.setForeground(MORADO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblResumen.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblFaltan.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnAgendar.setBackground(MORADO);
        btnAgendar.setForeground(Color.WHITE);
        
        btnAgendar.setContentAreaFilled(false);
        btnAgendar.setOpaque(true);
        btnAgendar.setBorderPainted(false);
        btnAgendar.setFont(btnAgendar.getFont().deriveFont(Font.BOLD, 16f));
        btnAgendar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAgendar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnAgendar.setToolTipText("Se activa cuando completes todos los pasos");

        btnLimpiar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLimpiar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        p.add(titulo);
        p.add(Box.createVerticalStrut(10));
        p.add(lblResumen);
        p.add(Box.createVerticalGlue());
        p.add(lblFaltan);
        p.add(Box.createVerticalStrut(10));
        p.add(btnAgendar);
        p.add(Box.createVerticalStrut(8));
        p.add(btnLimpiar);
        return p;
    }

    private JPanel crearPaso(String titulo, String ayuda) {
        JPanel p = new JPanel(new GridBagLayout());
        Font fuente = UIManager.getFont("Label.font").deriveFont(Font.BOLD, 15f);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0xCCCCCC)),
                        titulo, TitledBorder.LEFT, TitledBorder.TOP, fuente, MORADO),
                BorderFactory.createEmptyBorder(2, 10, 10, 10)));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblAyuda = new JLabel("<html><body style='width:340px'>" + esc(ayuda) + "</body></html>");
        lblAyuda.setForeground(GRIS_AYUDA);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(0, 0, 6, 0);
        p.add(lblAyuda, g);
        return p;
    }

    private void agregarFila(JPanel p, int fila, String etiqueta, JComponent campo) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = fila;
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(4, 0, 4, 8);
        p.add(new JLabel(etiqueta), g);

        g = new GridBagConstraints();
        g.gridx = 1;
        g.gridy = fila;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 0, 4, 0);
        p.add(campo, g);
    }

    private JPanel fijarAlto(JPanel p) {
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, p.getPreferredSize().height));
        return p;
    }


    private void cargarCombos() {
        for (Barbero b : controlador.listarBarberosActivos()) {
            comboBarbero.addItem(b);
        }
        LocalDate hoy = LocalDate.now();
        for (int i = 0; i < DIAS_VISIBLES; i++) {
            comboDia.addItem(hoy.plusDays(i));
        }

        comboDia.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                    boolean seleccionado, boolean conFoco) {
                super.getListCellRendererComponent(lista, valor, indice, seleccionado, conFoco);
                if (valor instanceof LocalDate) {
                    setText(textoDia((LocalDate) valor));
                }
                return this;
            }
        });
        comboHora.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                    boolean seleccionado, boolean conFoco) {
                super.getListCellRendererComponent(lista, valor, indice, seleccionado, conFoco);
                if (valor instanceof LocalTime) {
                    setText(((LocalTime) valor).format(FORMATO_HORA));
                }
                return this;
            }
        });
    }

    private void conectarEventos() {
        escuchar(txtNombre);
        escuchar(txtTelefono);
        escuchar(txtCorreo);
        for (JCheckBox c : checksServicios) {
            c.addItemListener(e -> actualizarResumen());
        }
        comboBarbero.addActionListener(e -> actualizarHoras());
        comboDia.addActionListener(e -> actualizarHoras());
        comboHora.addActionListener(e -> {
            if (!actualizandoHoras) {
                actualizarResumen();
            }
        });
        btnAgendar.addActionListener(e -> agendar());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void escuchar(JTextField campo) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarResumen();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarResumen();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarResumen();
            }
        });
    }

    private void actualizarHoras() {
        actualizandoHoras = true;
        comboHora.removeAllItems();
        Barbero barbero = (Barbero) comboBarbero.getSelectedItem();
        LocalDate dia = (LocalDate) comboDia.getSelectedItem();
        int libres = 0;
        if (barbero != null && dia != null) {
            for (LocalTime hora : controlador.listarHorariosDisponibles(barbero.getId(), dia)) {
                comboHora.addItem(hora);
                libres++;
            }
        }
        actualizandoHoras = false;

        if (libres == 0) {
            lblDisponibilidad.setText("No hay horas libres ese día con este barbero. Prueba con otro día u otro barbero.");
            lblDisponibilidad.setForeground(NARANJA);
        } else {
            lblDisponibilidad.setText(libres + (libres == 1 ? " hora libre" : " horas libres"));
            lblDisponibilidad.setForeground(GRIS_AYUDA);
        }
        actualizarResumen();
    }

    private void actualizarResumen() {
        String nombre = txtNombre.getText().trim();
        Barbero barbero = (Barbero) comboBarbero.getSelectedItem();
        LocalDate dia = (LocalDate) comboDia.getSelectedItem();
        LocalTime hora = (LocalTime) comboHora.getSelectedItem();

        StringBuilder lista = new StringBuilder();
        double total = 0;
        int minutos = 0;
        for (int i = 0; i < servicios.size(); i++) {
            if (checksServicios.get(i).isSelected()) {
                Servicio s = servicios.get(i);
                total += s.getPrecio();
                minutos += s.getDuracionMinutos();
                lista.append("&nbsp;• ").append(esc(s.getNombre())).append(" — ")
                        .append(dinero(s.getPrecio())).append("<br>");
            }
        }
        boolean excede = minutos > BarberiaControlador.TURNO_MINUTOS;
        String vacio = "<i style='color:#888888'>(pendiente)</i>";

        lblResumen.setText("<html><body style='width:205px'>"
                + "<b>Cliente:</b> " + (nombre.isEmpty() ? vacio : esc(nombre)) + "<br>"
                + "<b>Barbero:</b> " + (barbero == null ? vacio : esc(barbero.getNombre())) + "<br>"
                + "<b>Día:</b> " + (dia == null ? vacio : textoDia(dia)) + "<br>"
                + "<b>Hora:</b> " + (hora == null ? vacio : hora.format(FORMATO_HORA)) + "<br><br>"
                + "<b>Servicios:</b><br>" + (lista.length() == 0 ? "&nbsp;" + vacio + "<br>" : lista.toString()) + "<br>"
                + "<b>Total:</b> " + dinero(total) + "<br>"
                + "<b>Duración:</b> "
                + (excede ? "<font color='#C62828'>" : "") + minutos + " min"
                + (excede ? "</font>" : "") + " de " + BarberiaControlador.TURNO_MINUTOS
                + "</body></html>");

        List<String> faltan = new ArrayList<>();
        if (nombre.isEmpty()) {
            faltan.add("tu nombre");
        }
        if (!telefonoValido()) {
            faltan.add("un teléfono válido (solo números, 7 a 10 dígitos)");
        }
        if (!correoValido()) {
            faltan.add("un correo válido (o déjalo vacío)");
        }
        if (minutos == 0) {
            faltan.add("al menos un servicio");
        } else if (excede) {
            faltan.add("quitar algún servicio (máximo " + BarberiaControlador.TURNO_MINUTOS + " min)");
        }
        if (hora == null) {
            faltan.add("una hora libre");
        }

        boolean listo = faltan.isEmpty();
        btnAgendar.setEnabled(listo);
        btnAgendar.setBackground(listo ? MORADO : MORADO_APAGADO);
        if (listo) {
            lblFaltan.setText("<html><body style='width:205px'><b>Todo listo.</b> Pulsa «Agendar cita».</body></html>");
            lblFaltan.setForeground(VERDE);
        } else {
            lblFaltan.setText("<html><body style='width:205px'><b>Aún falta:</b> "
                    + esc(String.join("; ", faltan)) + ".</body></html>");
            lblFaltan.setForeground(NARANJA);
        }
    }

    private void agendar() {
        Barbero barbero = (Barbero) comboBarbero.getSelectedItem();
        LocalDate dia = (LocalDate) comboDia.getSelectedItem();
        LocalTime hora = (LocalTime) comboHora.getSelectedItem();
        if (barbero == null || dia == null || hora == null) {
            return;
        }
        List<Integer> idServicios = new ArrayList<>();
        for (int i = 0; i < servicios.size(); i++) {
            if (checksServicios.get(i).isSelected()) {
                idServicios.add(servicios.get(i).getId());
            }
        }
        try {
            Cita cita = controlador.agendarCitaConServicios(txtNombre.getText().trim(),
                    txtTelefono.getText().trim(), txtCorreo.getText().trim(),
                    barbero.getId(), dia, hora, idServicios);
            mostrarConfirmacion(cita);
            limpiar();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo agendar la cita",
                    JOptionPane.WARNING_MESSAGE);
            actualizarHoras();
        }
    }

    private void mostrarConfirmacion(Cita cita) {
        StringBuilder detalle = new StringBuilder();
        for (DetalleCita d : cita.getDetalles()) {
            detalle.append("&nbsp;• ").append(esc(d.getServicio().getNombre())).append(" — ")
                    .append(dinero(d.calcularSubtotal())).append("<br>");
        }
        String mensaje = "<html><body style='width:300px'>"
                + "<b>Tu cita #" + cita.getId() + " quedó confirmada.</b><br><br>"
                + "<b>Cliente:</b> " + esc(cita.getCliente().getNombre()) + "<br>"
                + "<b>Barbero:</b> " + esc(cita.getBarbero().getNombre()) + "<br>"
                + "<b>Cuándo:</b> " + textoDia(cita.getFecha()) + " a las "
                + cita.getHora().format(FORMATO_HORA) + "<br><br>"
                + "<b>Servicios:</b><br>" + detalle + "<br>"
                + "<b>Total a pagar:</b> " + dinero(cita.calcularTotal()) + "<br>"
                + "<b>Duración:</b> " + cita.calcularDuracionTotal() + " min<br><br>"
                + "Te esperamos. Recuerda llegar unos minutos antes."
                + "</body></html>";
        JOptionPane.showMessageDialog(this, mensaje, "¡Cita agendada!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiar() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        for (JCheckBox c : checksServicios) {
            c.setSelected(false);
        }
        if (comboBarbero.getItemCount() > 0) {
            comboBarbero.setSelectedIndex(0);
        }
        if (comboDia.getItemCount() > 0) {
            comboDia.setSelectedIndex(0);
        }
        actualizarHoras();
        txtNombre.requestFocusInWindow();
    }


    private boolean telefonoValido() {
        return txtTelefono.getText().trim().matches("\\d{7,10}");
    }

    private boolean correoValido() {
        String correo = txtCorreo.getText().trim();
        return correo.isEmpty() || correo.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    }

    private static String textoDia(LocalDate dia) {
        String texto = dia.format(FORMATO_DIA);
        if (dia.equals(LocalDate.now())) {
            return "Hoy · " + texto;
        }
        if (dia.equals(LocalDate.now().plusDays(1))) {
            return "Mañana · " + texto;
        }
        return texto.substring(0, 1).toUpperCase(LOCALE_CO) + texto.substring(1);
    }

    private static String dinero(double valor) {
        return String.format(LOCALE_CO, "$%,.0f", valor);
    }

    private static String esc(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}