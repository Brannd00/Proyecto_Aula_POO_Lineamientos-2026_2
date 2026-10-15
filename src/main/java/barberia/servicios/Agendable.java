/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barberia.servicios;

import barberia.modelo.Barbero;
import barberia.modelo.Cita;
import barberia.modelo.Cliente;
import java.time.LocalDate;
import java.time.LocalTime;

public interface Agendable {
    boolean verificarDisponibilidad(Barbero barbero, LocalDate fecha, LocalTime hora);
    Cita agendar(Cliente cliente, Barbero barbero, LocalDate fecha, LocalTime hora);
}
