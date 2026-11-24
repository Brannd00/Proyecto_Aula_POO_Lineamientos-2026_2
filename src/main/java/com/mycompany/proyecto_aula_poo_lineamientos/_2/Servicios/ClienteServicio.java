/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Cita;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Cliente;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.EstadoCita;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author brann
 */
public class ClienteServicio implements IBuscable <Cliente>{
 private final List<Cliente> clientes = new ArrayList<>();
    private final CitaServicio citaServicio;
    private int siguienteId = 1;

    public ClienteServicio(CitaServicio citaServicio) {
        this.citaServicio = citaServicio;
    }

    public Cliente registrarCliente(String nombre, String telefono, String correo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacio.");
        }
        for (Cliente c : clientes) {
            if (c.getTelefono().equals(telefono)) {
                throw new IllegalArgumentException("Ya existe un cliente con ese telefono.");
            }
        }
        Cliente nuevo = new Cliente(siguienteId++, nombre, telefono, correo);
        clientes.add(nuevo);
        return nuevo;
    }

    public void actualizarDatos(Cliente cliente, String nombre, String telefono, String correo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El telenfono no puede estar vacio.");
        }
        for (Cliente c : clientes) {
            if (c != cliente && c.getTelefono().equals(telefono)) {
                throw new IllegalArgumentException("Ya existe un cliente con ese telefono.");
            }
        }
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
    }

   public void eliminarCliente(Cliente cliente) {

    if (!citaServicio.listarPorCliente(cliente).isEmpty()) {

        throw new IllegalStateException(
                "El cliente tiene citas asociadas.");
    }

    clientes.remove(cliente);
}

    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    public Cliente buscarPorTelefono(String telefono) {
        for (Cliente c : clientes) {
            if (c.getTelefono().equals(telefono)) {
                return c;
            }
        }
        return null;
    }
    @Override
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : clientes) {
            if (c.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

   public List<Cita> consultarHistorialServicios(Cliente cliente) {

    List<Cita> historial = new ArrayList<>();

    for (Cita c : citaServicio.listarPorCliente(cliente)) {

        if (c.getEstado() == EstadoCita.ATENDIDA) {
            historial.add(c);
        }
    }

    return historial;
}

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }
}
