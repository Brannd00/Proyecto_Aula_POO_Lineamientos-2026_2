/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;

import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioCatalogoServicio {

    private final List<Servicio> catalogo = new ArrayList<>();
    private int siguienteId = 1;

    public Servicio registrarServicio(String nombre, double precio, int duracionMinutos, String descripcion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        validarPrecioYDuracion(precio, duracionMinutos);
        Servicio nuevo = new Servicio(siguienteId++, nombre, precio, duracionMinutos, descripcion);
        catalogo.add(nuevo);
        return nuevo;
    }

    public void actualizarPrecio(Servicio servicio, double nuevoPrecio) {
        validarPrecioYDuracion(nuevoPrecio, servicio.getDuracionMinutos());
        servicio.setPrecio(nuevoPrecio);
    }

    public void eliminarServicio(Servicio servicio) {
        catalogo.remove(servicio);
    }

    public Servicio buscarPorId(int id) {
        for (Servicio s : catalogo) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public List<Servicio> listarServicios() {
        return new ArrayList<>(catalogo);
    }

    private void validarPrecioYDuracion(double precio, int duracion) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a cero.");
        }
    }
}
