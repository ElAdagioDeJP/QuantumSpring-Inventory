/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.CRUD.CRUD.Servicios;

/**
 *
 * @author jearm
 */
import com.CRUD.CRUD.model.Productos;
import com.CRUD.CRUD.repository.ProductRepository;
import com.CRUD.CRUD.Excepciones.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosServices {

    @Autowired
    private ProductRepository productoRepository;

    // Obtener todos los productos
    public List<Productos> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Productos> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Crear un nuevo producto
    public Productos crearProducto(Productos producto) {
        return productoRepository.save(producto);
    }

    // Actualizar un producto existente
    public Productos actualizarProducto(Long id, Productos detallesProducto) {
        Productos producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + id));

        producto.setTitulo(detallesProducto.getTitulo());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setCategoria(detallesProducto.getCategoria());
        producto.setPrecio(detallesProducto.getPrecio());
        producto.setPorcentajeDescuento(detallesProducto.getPorcentajeDescuento());
        producto.setCalificacion(detallesProducto.getCalificacion());
        producto.setStock(detallesProducto.getStock());
        producto.setMarca(detallesProducto.getMarca());
        producto.setSku(detallesProducto.getSku());
        producto.setPeso(detallesProducto.getPeso());
        producto.setDimensiones(detallesProducto.getDimensiones());
        producto.setInformacionGarantia(detallesProducto.getInformacionGarantia());
        producto.setInformacionEnvio(detallesProducto.getInformacionEnvio());
        producto.setEstadoDisponibilidad(detallesProducto.getEstadoDisponibilidad());
        producto.setPoliticaDevolucion(detallesProducto.getPoliticaDevolucion());
        producto.setCantidadMinimaPedido(detallesProducto.getCantidadMinimaPedido());
        producto.setCodigoBarras(detallesProducto.getCodigoBarras());
        producto.setCodigoQR(detallesProducto.getCodigoQR());
        producto.setMiniatura(detallesProducto.getMiniatura());
        producto.setImagenes(detallesProducto.getImagenes());

        return productoRepository.save(producto);
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        Productos producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + id));

        productoRepository.delete(producto);
    }
}
