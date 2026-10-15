/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author brann
 */
import java.time.LocalDate;

public class Cliente extends Persona{
    private String correo;
    private LocalDate fechaRegistro;

    public Cliente(int id, String nombre, String telefono, String correo) {
        super(id, nombre, telefono);
        this.correo = correo;
        this.fechaRegistro = LocalDate.now();
    }

    public String getCorreo() { 
        return correo; 
    }
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    public LocalDate getFechaRegistro() { 
        return fechaRegistro; 
    }
    public void setFechaRegistro(LocalDate fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }
    
    @Override
    public String mostrarInformacion() {
        return "Cliente: " + nombre + " - Tel: " + telefono + " - Correo: " + correo;
    }

    @Override
    public String toString() {
        return nombre + " (" + telefono + ")";
    }
   
}
