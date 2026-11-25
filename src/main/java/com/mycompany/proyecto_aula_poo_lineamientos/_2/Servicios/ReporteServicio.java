/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.*;
import java.time.LocalDate;
/**
 *
 * @author brann
 */
public class ReporteServicio {
    
     private final PagoServicio pagoServicio;
    private final BarberoServicio barberoServicio;
    private final CitaServicio citaServicio;

    public ReporteServicio(PagoServicio pagoServicio,
            BarberoServicio barberoServicio,
            CitaServicio citaServicio) {

        this.pagoServicio = pagoServicio;
        this.barberoServicio = barberoServicio;
        this.citaServicio = citaServicio;
    }

    public void generarReportes(LocalDate inicio, LocalDate fin)
            throws InterruptedException {

        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            throw new IllegalArgumentException("Periodo invalido.");
        }

        long tiempoInicio = System.currentTimeMillis();

        Runnable tareaIngresos = () -> {

            System.out.println("Iniciando reporte de ingresos..");

            simularProcesamiento();

            double ingresos = pagoServicio
                    .consultarIngresosPorPeriodo(inicio, fin);

            System.out.println("Ingresos totales: $" + ingresos);
        };

        Runnable tareaComisiones = () -> {

            System.out.println("Iniciando reporte de comisiones...");

            simularProcesamiento();

            for (Barbero b : barberoServicio.listarBarberos()) {

                double comision = barberoServicio
                        .calcularComision(b, inicio, fin);

                System.out.println("Comision de "
                        + b.getNombre() + ": $" + comision);
            }
        };

        Runnable tareaCitas = () -> {

            System.out.println("Iniciando reporte de citas...");

            simularProcesamiento();

            int cantidad = 0;

            for (Cita c : citaServicio.listarCitas()) {

                if (c.getEstado() == EstadoCita.ATENDIDA
                        && !c.getFecha().isBefore(inicio)
                        && !c.getFecha().isAfter(fin)) {

                    cantidad++;
                }
            }

            System.out.println("Citas atendidas: " + cantidad);
        };

        Thread hiloIngresos = new Thread(tareaIngresos, "Ingresdos");

        Thread hiloComisiones = new Thread(tareaComisiones, "Comisiones");

        Thread hiloCitas = new Thread(tareaCitas, "Citaqs");

        hiloIngresos.start();
        hiloComisiones.start();
        hiloCitas.start();

        hiloIngresos.join();
        hiloComisiones.join();
        hiloCitas.join();

        long tiempoFin = System.currentTimeMillis();

        System.out.println("Reportes finalizados.");
        System.out.println("Tiempo total: "
                + (tiempoFin - tiempoInicio) + " ms");
    }

    private void simularProcesamiento() {

        try {

            Thread.sleep(2000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}
