/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo;

public class DetalleCita {

    private final Servicio servicio;
    private final int cantidad;
    private final double precioUnitario;
    private final int duracionUnitaria;

    public DetalleCita(Servicio servicio, int cantidad) {

        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio es obligatorio.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero.");
        }

        this.servicio = servicio;
        this.cantidad = cantidad;

        this.precioUnitario = servicio.getPrecio();
        this.duracionUnitaria = servicio.getDuracionMinutos();
    }

    public Servicio getServicio() {
        return servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getDuracionUnitaria() {
        return duracionUnitaria;
    }

    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    public int calcularDuracion() {
        return duracionUnitaria * cantidad;
    }
}