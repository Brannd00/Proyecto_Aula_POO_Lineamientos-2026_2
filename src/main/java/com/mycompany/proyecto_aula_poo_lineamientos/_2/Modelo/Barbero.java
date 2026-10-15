/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author brann
 */
public class Barbero extends Persona{
    private String especialidad;
    private String estado; 

    public Barbero(int id, String nombre, String telefono, String especialidad) {
        super(id, nombre, telefono);
        this.especialidad = especialidad;
        this.estado = "activo";
    }

    public String getEspecialidad() { 
        return especialidad; 
    }
    public void setEspecialidad(String especialidad) { 
        this.especialidad = especialidad; 
    }
    public String getEstado() { 
        return estado; 
    }
    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    @Override
    public String mostrarInformacion() {
        return "Barbero: " + nombre + " - Especialidad: " + especialidad + " - Estado: " + estado;
    }

    @Override
    public String toString() {
        return nombre + " - " + especialidad;
    }
}
