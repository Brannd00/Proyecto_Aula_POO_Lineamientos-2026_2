/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_aula_poo_lineamientos._2.Servicios;

/**
 *
 * @author brann
 */
import java.util.List;

public interface IBuscable <T> {
  
    List<T> buscarPorNombre(String nombre);
}
