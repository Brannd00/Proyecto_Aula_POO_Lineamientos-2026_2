/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;

import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoServicio {

    private final List<Pago> pagos = new ArrayList<>();
    private int siguienteId = 1;

    public Pago registrarPago(Cita cita, String metodoPago) {
        if (cita.getEstado() != EstadoCita.ATENDIDA) {
            throw new IllegalStateException("Solo se puede registrar el pago de una cita finalizada.");
        }
        for (Pago p : pagos) {
            if (p.getCita().getId() == cita.getId()) {
                throw new IllegalStateException("Esta cita ya tiene un pago registrado.");
            }
        }
        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar el método de pago.");
        }
        Pago nuevo = new Pago(siguienteId++, cita, metodoPago);
        pagos.add(nuevo);
        return nuevo;
    }

    public double calcularTotal(Cita cita) {
        return cita.calcularTotal();
    }

    public String generarComprobante(Pago pago) {
        return pago.generarComprobante();
    }

    public double consultarIngresosPorPeriodo(LocalDate inicio, LocalDate fin) {
        double total = 0;
        for (Pago p : pagos) {
            if (!p.getFechaPago().isBefore(inicio) && !p.getFechaPago().isAfter(fin)) {
                total += p.getMontoTotal();
            }
        }
        return total;
    }

    public List<Pago> listarPagos() {
        return new ArrayList<>(pagos);
    }
}
