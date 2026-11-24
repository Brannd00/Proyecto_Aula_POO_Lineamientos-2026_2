package com.mycompany.proyecto_aula_poo_lineamientos._2;

import com.mycompany.proyecto_aula_poo_lineamientos._2.Controlador.BarberiaControlador;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 *
 * @author brann
 */
public class Proyecto_Aula_POO_Lineamientos2026_2 {

    private static final String NOMBRE_BARBERIA = "Barbería UDeC";
    private static final String DIRECCION_BARBERIA = "Barrio los Alpes Mz C cll 31-e";
    private static final String TELEFONO_BARBERIA = "3001234567";

    public static void main(String[] args) {
        final BarberiaControlador controlador = new BarberiaControlador(
                NOMBRE_BARBERIA, DIRECCION_BARBERIA, TELEFONO_BARBERIA);
        cargarDatosIniciales(controlador);

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal.aplicarEstiloGlobal();
            new VentanaPrincipal(controlador).setVisible(true);
        });
    }

    private static void cargarDatosIniciales(BarberiaControlador controlador) {
        controlador.registrarBarbero("Andrés Gómez", "3205551111", "Corte clásico y barba");
        controlador.registrarBarbero("Luis Martínez", "3205552222", "Fade y diseños");
        controlador.registrarBarbero("Camilo Ríos", "3205553333", "Barba y tintes");

        controlador.registrarServicio("Corte clásico", 25000, 30, "Corte con tijera y máquina");
        controlador.registrarServicio("Arreglo de barba", 15000, 20, "Perfilado y toalla caliente");
        controlador.registrarServicio("Cejas", 8000, 10, "Perfilado de cejas");
        controlador.registrarServicio("Lavado", 10000, 10, "Lavado y masaje capilar");
        controlador.registrarServicio("Tinte", 40000, 30, "Coloración de cabello o barba");
    }
}