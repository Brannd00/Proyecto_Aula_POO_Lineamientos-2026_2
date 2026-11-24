/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;

import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaServicio implements Agendable {

    private final List<Cita> citas = new ArrayList<>();
    private int siguienteId = 1;
    public static final LocalTime HORA_APERTURA = LocalTime.of(9, 0);
    public static final LocalTime HORA_CIERRE = LocalTime.of(18, 0);
    public static final int TURNO_MINUTOS = 60;

private boolean horarioValido(LocalTime hora) {

    if (hora == null) {
        return false;
    }

    return !hora.isBefore(HORA_APERTURA)
            && hora.isBefore(HORA_CIERRE)
            && hora.getMinute() == 0
            && hora.getSecond() == 0
            && hora.getNano() == 0;
}
   @Override
public boolean verificarDisponibilidad(
        Barbero barbero, LocalDate fecha, LocalTime hora) {

    return verificarDisponibilidad(
            barbero, fecha, hora, null);
}

private boolean verificarDisponibilidad(
        Barbero barbero, LocalDate fecha,
        LocalTime hora, Cita citaIgnorada) {

    if (barbero == null || fecha == null
            || !horarioValido(hora)) {
        return false;
    }

    if (!barbero.getEstado().puedeAtender()) {
        return false;
    }

    if (LocalDateTime.of(fecha, hora)
            .isBefore(LocalDateTime.now())) {
        return false;
    }

    for (Cita c : citas) {

        if (c == citaIgnorada) {
            continue;
        }

        if (c.getBarbero().getId() == barbero.getId()
                && c.getEstado() != EstadoCita.CANCELADA
                && c.getFecha().equals(fecha)
                && c.getHora().equals(hora)) {

            return false;
        }
    }

    return true;
}

   @Override
public Cita agendar(
        Cliente cliente, Barbero barbero,
        LocalDate fecha, LocalTime hora) {

    if (cliente == null || barbero == null
            || fecha == null || hora == null) {

        throw new IllegalArgumentException(
                "Todos los datos son obligatorios.");
    }

    if (!verificarDisponibilidad(barbero, fecha, hora)) {

        throw new IllegalStateException(
                "El barbero o el horario no estan disponibles.");
    }

    if (!clienteDisponible(cliente, fecha, hora, null)) {

        throw new IllegalStateException(
                "El cliente ya tiene una cita en ese horario.");
    }

    Cita cita = new Cita(
            siguienteId++, fecha, hora, cliente, barbero);

    citas.add(cita);

    return cita;
}

    private boolean clienteDisponible(
        Cliente cliente, LocalDate fecha,
        LocalTime hora, Cita citaIgnorada) {

    for (Cita c : citas) {

        if (c == citaIgnorada) {
            continue;
        }

        if (c.getCliente().getId() == cliente.getId()
                && c.getEstado() != EstadoCita.CANCELADA
                && c.getFecha().equals(fecha)
                && c.getHora().equals(hora)) {

            return false;
        }
    }

    return true;
}
    
   public void agregarServicioACita(
        Cita cita, Servicio servicio, int cantidad) {

    if (cita == null || servicio == null) {
        throw new IllegalArgumentException(
                "La cita y el servicio son obligatorios.");
    }

    if (cantidad <= 0) {
        throw new IllegalArgumentException(
                "La cantidad debe ser mayor a cero.");
    }

    if (cita.getEstado() != EstadoCita.PENDIENTE) {
        throw new IllegalStateException(
                "Solo se pueden modificar citas pendientes.");
    }

    long duracionNueva = cita.calcularDuracionTotal()
            + (long) servicio.getDuracionMinutos() * cantidad;

    if (duracionNueva > TURNO_MINUTOS) {
        throw new IllegalArgumentException(
                "La cita no puede superar los 60 minutos.");
    }

    cita.agregarDetalle(
            new DetalleCita(servicio, cantidad));
}

   public void cancelarCita(Cita cita) {

    if (cita == null
            || cita.getEstado() != EstadoCita.PENDIENTE) {

        throw new IllegalStateException(
                "Solo se pueden cancelar citas pendientes.");
    }

    cita.setEstado(EstadoCita.CANCELADA);
}

   public void reprogramarCita(
        Cita cita, LocalDate nuevaFecha,
        LocalTime nuevaHora) {

    if (cita == null) {
        throw new IllegalArgumentException(
                "La cita es obligatoria.");
    }

    if (cita.getEstado() != EstadoCita.PENDIENTE) {
        throw new IllegalStateException(
                "Solo se pueden reprogramar citas pendientes.");
    }

    if (!verificarDisponibilidad(
            cita.getBarbero(),
            nuevaFecha,
            nuevaHora,
            cita)) {

        throw new IllegalStateException(
                "El horario seleccionado no esta disponible.");
    }

    if (!clienteDisponible(
            cita.getCliente(),
            nuevaFecha,
            nuevaHora,
            cita)) {

        throw new IllegalStateException(
                "El cliente ya tiene una cita en ese horario.");
    }

    cita.setFecha(nuevaFecha);
    cita.setHora(nuevaHora);
}

    public void finalizarCita(Cita cita) {

    if (cita == null) {
        throw new IllegalArgumentException(
                "La cita es obligatoria.");
    }

    if (cita.getEstado() != EstadoCita.PENDIENTE) {

        throw new IllegalStateException(
                "Solo se pueden finalizar citas pendientes.");
    }

    if (cita.getDetalles().isEmpty()) {

        throw new IllegalStateException(
                "La cita debe tener al menos un servicio.");
    }

    LocalDateTime fechaCita = LocalDateTime.of(
            cita.getFecha(), cita.getHora());

    if (fechaCita.isAfter(LocalDateTime.now())) {

        throw new IllegalStateException(
                "No se puede finalizar una cita futura.");
    }

    cita.setEstado(EstadoCita.ATENDIDA);
}
  public int calcularDuracionTotal(Cita cita) {

    if (cita == null) {
        throw new IllegalArgumentException(
                "La cita es obligatoria.");
    }

    return cita.calcularDuracionTotal();
}
  
    public List<Cita> listarCitas() {
        return new ArrayList<>(citas);
    }

    public List<Cita> listarPorCliente(Cliente cliente) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getCliente().getId() == cliente.getId()) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Cita> listarPorBarbero(Barbero barbero) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getBarbero().getId() == barbero.getId()) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}
