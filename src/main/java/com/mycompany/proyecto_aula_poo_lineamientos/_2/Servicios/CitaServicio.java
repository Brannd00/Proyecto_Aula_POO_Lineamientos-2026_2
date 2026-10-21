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

    @Override
    public boolean verificarDisponibilidad(Barbero barbero, LocalDate fecha, LocalTime hora) {
        for (Cita c : citas) {
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
    public Cita agendar(Cliente cliente, Barbero barbero, LocalDate fecha, LocalTime hora) {
        if (cliente == null || barbero == null) {
            throw new IllegalArgumentException("Cliente y barbero son obligatorios.");
        }
        if (!"activo".equalsIgnoreCase(barbero.getEstado())) {
            throw new IllegalStateException("El barbero no está activo.");
        }
        if (LocalDateTime.of(fecha, hora).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha y hora deben ser futuras.");
        }
        if (!verificarDisponibilidad(barbero, fecha, hora)) {
            throw new IllegalStateException("El barbero ya tiene una cita en ese horario.");
        }
        Cita cita = new Cita(siguienteId++, fecha, hora, cliente, barbero);
        citas.add(cita);
        return cita;
    }

    public void agregarServicioACita(Cita cita, Servicio servicio, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden agregar servicios a citas pendientes.");
        }
        cita.agregarDetalle(new DetalleCita(servicio, cantidad));
    }

    public void cancelarCita(Cita cita) {
        if (cita.getEstado() == EstadoCita.ATENDIDA) {
            throw new IllegalStateException("No se puede cancelar una cita ya atendida.");
        }
        cita.setEstado(EstadoCita.CANCELADA);
    }

    public void reprogramarCita(Cita cita, LocalDate nuevaFecha, LocalTime nuevaHora) {
        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden reprogramar citas pendientes.");
        }
        if (LocalDateTime.of(nuevaFecha, nuevaHora).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La nueva fecha y hora deben ser futuras.");
        }
        if (!verificarDisponibilidad(cita.getBarbero(), nuevaFecha, nuevaHora)) {
            throw new IllegalStateException("El barbero ya tiene una cita en ese horario.");
        }
        cita.setFecha(nuevaFecha);
        cita.setHora(nuevaHora);
    }

    public void finalizarCita(Cita cita) {
        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden finalizar citas pendientes.");
        }
        if (cita.getDetalles().isEmpty()) {
            throw new IllegalStateException("La cita debe tener al menos un servicio.");
        }
        cita.setEstado(EstadoCita.ATENDIDA);
    }

    public int calcularDuracionTotal(Cita cita) {
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
