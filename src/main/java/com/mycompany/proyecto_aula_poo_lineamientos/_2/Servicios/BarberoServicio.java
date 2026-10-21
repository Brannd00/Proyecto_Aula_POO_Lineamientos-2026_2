/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;

import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BarberoServicio implements IBuscable<Barbero> {

    private static final double PORCENTAJE_COMISION = 0.40;

    private final List<Barbero> barberos = new ArrayList<>();
    private final CitaServicio citaServicio;
    private int siguienteId = 1;

    public BarberoServicio(CitaServicio citaServicio) {
        this.citaServicio = citaServicio;
    }

    public Barbero registrarBarbero(String nombre, String telefono, String especialidad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar al menos una especialidad.");
        }
        Barbero nuevo = new Barbero(siguienteId++, nombre, telefono, especialidad);
        barberos.add(nuevo);
        return nuevo;
    }

    public void cambiarEstado(Barbero barbero, String nuevoEstado) {
        if (!"activo".equalsIgnoreCase(nuevoEstado) && !"inactivo".equalsIgnoreCase(nuevoEstado)) {
            throw new IllegalArgumentException("El estado debe ser 'activo' o 'inactivo'.");
        }
        barbero.setEstado(nuevoEstado.toLowerCase());
    }

    public List<Cita> consultarAgenda(Barbero barbero, LocalDate fecha) {
        List<Cita> agenda = new ArrayList<>();
        for (Cita c : citaServicio.listarPorBarbero(barbero)) {
            if (c.getFecha().equals(fecha) && c.getEstado() != EstadoCita.CANCELADA) {
                agenda.add(c);
            }
        }
        return agenda;
    }

    public double calcularComision(Barbero barbero, LocalDate inicio, LocalDate fin) {
        double total = 0;
        for (Cita c : citaServicio.listarPorBarbero(barbero)) {
            boolean enRango = !c.getFecha().isBefore(inicio) && !c.getFecha().isAfter(fin);
            if (c.getEstado() == EstadoCita.ATENDIDA && enRango) {
                total += c.calcularTotal();
            }
        }
        return total * PORCENTAJE_COMISION;
    }

    @Override
    public List<Barbero> buscarPorNombre(String nombre) {
        List<Barbero> resultado = new ArrayList<>();
        for (Barbero b : barberos) {
            if (b.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(b);
            }
        }
        return resultado;
    }

    public List<Barbero> listarActivos() {
        List<Barbero> activos = new ArrayList<>();
        for (Barbero b : barberos) {
            if ("activo".equalsIgnoreCase(b.getEstado())) {
                activos.add(b);
            }
        }
        return activos;
    }

    public List<Barbero> listarBarberos() {
        return new ArrayList<>(barberos);
    }
}
