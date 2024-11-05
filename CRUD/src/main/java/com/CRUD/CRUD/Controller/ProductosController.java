/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.CRUD.CRUD.Controller;

/**
 *
 * @author jearm
 */
import com.CRUD.CRUD.Excepciones.ResourceNotFoundException;
import com.CRUD.CRUD.model.Productos;
import com.CRUD.CRUD.Servicios.ProductosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosServices productosServices;

    // Obtener todos los productos
    @GetMapping
    public List<Productos> obtenerTodosLosProductos() {
        return productosServices.obtenerTodosLosProductos();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Productos> obtenerProductoPorId(@PathVariable Long id) {
        Productos producto = productosServices.obtenerProductoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + id));
        return ResponseEntity.ok().body(producto);
    }

    // Crear un nuevo producto
    @PostMapping
    public Productos crearProducto(@RequestBody Productos producto) {
        return productosServices.crearProducto(producto);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Productos> actualizarProducto(@PathVariable Long id, @RequestBody Productos detallesProducto) {
        Productos productoActualizado = productosServices.actualizarProducto(id, detallesProducto);
        return ResponseEntity.ok(productoActualizado);
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarProducto(@PathVariable Long id) {
        productosServices.eliminarProducto(id);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return respuesta;
    }

    // Redirigir a index.html para servir la aplicación Vue.js
   
}