/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

public class Pago {

    private int id;
    private Cita cita;
    private double montoTotal;
    private String metodoPago;
    private LocalDate fechaPago;

    public Pago(int id, Cita cita, String metodoPago) {
        this.id = id;
        this.cita = cita;
        this.metodoPago = metodoPago;
        this.montoTotal = cita.calcularTotal();
        this.fechaPago = LocalDate.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public String generarComprobante() {
        return "Pago #" + id + " - Cliente: " + cita.getCliente().getNombre()
                + " - Total: $" + montoTotal + " - Método: " + metodoPago;
    }
}