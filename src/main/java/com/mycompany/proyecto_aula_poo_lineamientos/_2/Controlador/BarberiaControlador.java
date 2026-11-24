/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Controlador;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.*;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brann
 */
public class BarberiaControlador {
        private final Barberia barberia;
    private final CitaServicio citaServicio;
    private final ClienteServicio clienteServicio;
    private final BarberoServicio barberoServicio;
    private final ServicioCatalogoServicio catalogoServicio;
    private final PagoServicio pagoServicio;
    public static final LocalTime HORA_APERTURA =
        CitaServicio.HORA_APERTURA;

public static final LocalTime HORA_CIERRE =
        CitaServicio.HORA_CIERRE;

public static final int TURNO_MINUTOS =
        CitaServicio.TURNO_MINUTOS;
    
   public BarberiaControlador(
        String nombre, String direccion, String telefono) {

    this.barberia = new Barberia(
            nombre, direccion, telefono);

    this.citaServicio = new CitaServicio();

    this.pagoServicio = new PagoServicio();

    this.clienteServicio =
            new ClienteServicio(citaServicio);

    this.barberoServicio =
            new BarberoServicio(citaServicio, pagoServicio);

    this.catalogoServicio =
            new ServicioCatalogoServicio();
}


    public String getNombreBarberia() {
        return barberia.getNombre();
    }


    public Cliente registrarCliente(String nombre, String telefono, String correo) {
        return clienteServicio.registrarCliente(nombre, telefono, correo);
    }

    public void actualizarCliente(int idCliente, String nombre, String telefono, String correo) {
        clienteServicio.actualizarDatos(obtenerCliente(idCliente), nombre, telefono, correo);
    }

    public void eliminarCliente(int idCliente) {
        clienteServicio.eliminarCliente(obtenerCliente(idCliente));
    }

    public List<Cliente> listarClientes() {
        return clienteServicio.listarClientes();
    }

    public List<Cliente> buscarClientesPorNombre(String nombre) {
        return clienteServicio.buscarPorNombre(nombre);
    }

    public List<Cita> consultarHistorialCliente(int idCliente) {
        return clienteServicio.consultarHistorialServicios(obtenerCliente(idCliente));
    }


    public Barbero registrarBarbero(String nombre, String telefono, String especialidad) {
        Barbero barbero = barberoServicio.registrarBarbero(nombre, telefono, especialidad);
        barberia.registrarBarbero(barbero);
        return barbero;
    }

   public void cambiarEstadoBarbero(int idBarbero, EstadoBarbero nuevoEstado) {
        barberoServicio.cambiarEstado(obtenerBarbero(idBarbero), nuevoEstado);
    }

    public List<Barbero> listarBarberos() {
        return barberoServicio.listarBarberos();
    }

    public List<Barbero> listarBarberosActivos() {
        return barberoServicio.listarActivos();
    }

    public List<Barbero> buscarBarberosPorNombre(String nombre) {
        return barberoServicio.buscarPorNombre(nombre);
    }

    public List<Cita> consultarAgendaBarbero(int idBarbero, LocalDate fecha) {
        return barberoServicio.consultarAgenda(obtenerBarbero(idBarbero), fecha);
    }

    public double calcularComisionBarbero(int idBarbero, LocalDate inicio, LocalDate fin) {
        return barberoServicio.calcularComision(obtenerBarbero(idBarbero), inicio, fin);
    }


    public Servicio registrarServicio(String nombre, double precio, int duracionMinutos, String descripcion) {
        Servicio servicio = catalogoServicio.registrarServicio(nombre, precio, duracionMinutos, descripcion);
        barberia.registrarServicio(servicio);
        return servicio;
    }

    public void actualizarPrecioServicio(int idServicio, double nuevoPrecio) {
        catalogoServicio.actualizarPrecio(obtenerServicio(idServicio), nuevoPrecio);
    }

    public List<Servicio> listarServicios() {
        return catalogoServicio.listarServicios();
    }


    public boolean verificarDisponibilidad(int idBarbero, LocalDate fecha, LocalTime hora) {
        return citaServicio.verificarDisponibilidad(obtenerBarbero(idBarbero), fecha, hora);
    }

    public Cita agendarCita(int idCliente, int idBarbero, LocalDate fecha, LocalTime hora) {
        return citaServicio.agendar(obtenerCliente(idCliente), obtenerBarbero(idBarbero), fecha, hora);
    }

    public void agregarServicioACita(int idCita, int idServicio, int cantidad) {
        citaServicio.agregarServicioACita(obtenerCita(idCita), obtenerServicio(idServicio), cantidad);
    }

    public void cancelarCita(int idCita) {
        citaServicio.cancelarCita(obtenerCita(idCita));
    }

    public void reprogramarCita(int idCita, LocalDate nuevaFecha, LocalTime nuevaHora) {
        citaServicio.reprogramarCita(obtenerCita(idCita), nuevaFecha, nuevaHora);
    }

    public void finalizarCita(int idCita) {
        citaServicio.finalizarCita(obtenerCita(idCita));
    }

    public int calcularDuracionCita(int idCita) {
        return citaServicio.calcularDuracionTotal(obtenerCita(idCita));
    }

    public List<Cita> listarCitas() {
        return citaServicio.listarCitas();
    }


    public Pago registrarPago(int idCita, String metodoPago) {
        return pagoServicio.registrarPago(obtenerCita(idCita), metodoPago);
    }

    public double calcularTotalCita(int idCita) {
        return pagoServicio.calcularTotal(obtenerCita(idCita));
    }

    public String generarComprobante(int idPago) {
        for (Pago p : pagoServicio.listarPagos()) {
            if (p.getId() == idPago) {
                return pagoServicio.generarComprobante(p);
            }
        }
        throw new IllegalArgumentException("No existe un pago con id " + idPago + ".");
    }

    public double consultarIngresosPorPeriodo(LocalDate inicio, LocalDate fin) {
        return pagoServicio.consultarIngresosPorPeriodo(inicio, fin);
    }

    public List<Pago> listarPagos() {
        return pagoServicio.listarPagos();
    }
    public List<LocalTime> listarHorariosDisponibles(int idBarbero, LocalDate fecha) {
        Barbero barbero = obtenerBarbero(idBarbero);
        List<LocalTime> libres = new ArrayList<>();
         if (!barbero.getEstado().puedeAtender()) {
            return libres;
        }
        LocalTime hora = HORA_APERTURA;
        while (hora.isBefore(HORA_CIERRE)) {
            boolean esFutura = !LocalDateTime.of(fecha, hora).isBefore(LocalDateTime.now());
            if (esFutura && citaServicio.verificarDisponibilidad(barbero, fecha, hora)) {
                libres.add(hora);
            }
            hora = hora.plusMinutes(TURNO_MINUTOS);
        }
        return libres;
    }
     public Cliente registrarOBuscarCliente(String nombre, String telefono, String correo) {
        if (telefono != null) {
            Cliente existente = clienteServicio.buscarPorTelefono(telefono.trim());
            if (existente != null) {
                return existente;
            }
        }
        return clienteServicio.registrarCliente(nombre, telefono, correo);
    }
     public Cita agendarCitaConServicios(String nombre, String telefono, String correo,
            int idBarbero, LocalDate fecha, LocalTime hora, List<Integer> idServicios) {
        if (idServicios == null || idServicios.isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos un servicio.");
        }
        List<Servicio> elegidos = new ArrayList<>();
        int duracion = 0;
        for (int idServicio : idServicios) {
            Servicio s = obtenerServicio(idServicio);
            elegidos.add(s);
            duracion += s.getDuracionMinutos();
        }
        if (duracion > TURNO_MINUTOS) {
            throw new IllegalArgumentException("Los servicios elegidos suman " + duracion
                    + " min y cada turno dura " + TURNO_MINUTOS + " min. Quite algun servicio.");
        }
        if (!listarHorariosDisponibles(idBarbero, fecha).contains(hora)) {
            throw new IllegalStateException("Ese horario no esta dispnible, elija otro.");
        }
        Cliente cliente = registrarOBuscarCliente(nombre, telefono, correo);
        Cita cita = citaServicio.agendar(cliente, obtenerBarbero(idBarbero), fecha, hora);
        for (Servicio s : elegidos) {
            citaServicio.agregarServicioACita(cita, s, 1);
        }
        return cita;
    }
    private Cliente obtenerCliente(int id) {
        Cliente cliente = clienteServicio.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliete con id " + id + ".");
        }
        return cliente;
    }

    private Barbero obtenerBarbero(int id) {
        for (Barbero b : barberoServicio.listarBarberos()) {
            if (b.getId() == id) {
                return b;
            }
        }
        throw new IllegalArgumentException("No existe un barbero con ied " + id + ".");
    }

    private Servicio obtenerServicio(int id) {
        Servicio servicio = catalogoServicio.buscarPorId(id);
        if (servicio == null) {
            throw new IllegalArgumentException("No exite un servicio con id " + id + ".");
        }
        return servicio;
    }

    private Cita obtenerCita(int id) {
        for (Cita c : citaServicio.listarCitas()) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new IllegalArgumentException("No existe una cita con id " + id + ".");
    }

}
