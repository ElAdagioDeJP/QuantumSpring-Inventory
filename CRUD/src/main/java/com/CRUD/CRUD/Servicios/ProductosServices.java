/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.CRUD.CRUD.Servicios;

import com.CRUD.CRUD.model.Productos;
import com.CRUD.CRUD.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosServices {

    @Autowired
    private ProductRepository productRepository;

    public List<Productos> obtenerTodosLosProductos() {
        return productRepository.findAll();
    }

    public Optional<Productos> obtenerProductoPorId(Long id) {
        return productRepository.findById(id);
    }

    public Productos crearProducto(Productos producto) {
        return productRepository.save(producto);
    }

    public Productos actualizarProducto(Productos producto) {
        return productRepository.save(producto);
    }

    public void eliminarProducto(Productos producto) {
        productRepository.delete(producto);
    }
}
