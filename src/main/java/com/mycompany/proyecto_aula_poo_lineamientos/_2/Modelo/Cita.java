/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cita {
    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoCita estado;
    private Cliente cliente;
    private Barbero barbero;
    private List<DetalleCita> detalles;
    
    public Cita(int id, LocalDate fecha, LocalTime hora, Cliente cliente, Barbero barbero){
        
        this.id=id;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.barbero = barbero;
        this.estado = EstadoCita.PENDIENTE;
        this.detalles = new ArrayList<>();
    }
         public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Barbero getBarbero() { return barbero; }
    public void setBarbero(Barbero barbero) { this.barbero = barbero; }

    public List<DetalleCita> getDetalles() { return detalles; }

    public void agregarDetalle(DetalleCita detalle) {
    this.detalles.add(detalle);
    }
     public int calcularDuracionTotal() {
        int total = 0;
        for (DetalleCita d : detalles) {
            total += d.getServicio().getDuracionMinutos() * d.getCantidad();
        }
        return total;
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleCita d : detalles) {
            total += d.calcularSubtotal();
        }
        return total;
    }
}
