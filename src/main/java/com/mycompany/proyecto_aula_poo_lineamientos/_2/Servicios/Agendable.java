/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;

/**
 *
 * @author brann
 */
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Barbero;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Cita;
import com.mycompany.proyecto_aula_poo_lineamientos._2.Modelo.Cliente;
import java.time.LocalDate;
import java.time.LocalTime;

public interface Agendable {
    boolean verificarDisponibilidad(Barbero barbero, LocalDate fecha, LocalTime hora);
    Cita agendar(Cliente cliente, Barbero barbero, LocalDate fecha, LocalTime hora);

}
