/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barberia.Servicio;

import java.util.List;

public interface IBuscable<T> {
    List<T> buscarPorNombre(String nombre);
}
