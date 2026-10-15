/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Barberia {
    
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Barbero> barberos;
    private List<Servicio> servicios;
    
    
     public Barberia(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.barberos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }
     public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public List<Barbero> getBarberos() { return barberos; }
    public List<Servicio> getServicios() { return servicios; }
    
     public void registrarBarbero(Barbero barbero) {
        this.barberos.add(barbero);
     }
     
 public void registrarServicio(Servicio servicio) {
        this.servicios.add(servicio);
    }
  
}
