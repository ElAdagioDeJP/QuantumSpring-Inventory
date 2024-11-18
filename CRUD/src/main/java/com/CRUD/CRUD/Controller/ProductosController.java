package com.CRUD.CRUD.Controller;

import com.CRUD.CRUD.Excepciones.ResourceNotFoundException;
import com.CRUD.CRUD.model.Productos;
import com.CRUD.CRUD.Servicios.ProductosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosServices productosServices;

    @GetMapping
    public List<Productos> obtenerTodosLosProductos() {
        return productosServices.obtenerTodosLosProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productos> obtenerProductoPorId(@PathVariable Long id) {
        Productos producto = productosServices.obtenerProductoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + id));
        return ResponseEntity.ok().body(producto);
    }

    @PostMapping
    public Productos crearProducto(@RequestBody Productos producto) {
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setFechaActualizacion(LocalDateTime.now());
        return productosServices.crearProducto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> actualizarProducto(@PathVariable Long id, @RequestBody Productos detallesProducto) {
        Productos producto = productosServices.obtenerProductoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + id));

        producto.setTitulo(detallesProducto.getTitulo());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setCategoria(detallesProducto.getCategoria());
        producto.setPrecio(detallesProducto.getPrecio());
        producto.setDescuento(detallesProducto.getDescuento());
        producto.setStock(detallesProducto.getStock());
        producto.setMarca(detallesProducto.getMarca());
        producto.setSku(detallesProducto.getSku());
        producto.setPeso(detallesProducto.getPeso());
        producto.setInformacionGarantia(detallesProducto.getInformacionGarantia());
        producto.setInformacionEnvio(detallesProducto.getInformacionEnvio());
        producto.setEstadoDisponibilidad(detallesProducto.getEstadoDisponibilidad());
        producto.setPoliticaDevolucion(detallesProducto.getPoliticaDevolucion());
        producto.setCantidadMinimaPedido(detallesProducto.getCantidadMinimaPedido());
        producto.setMiniatura(detallesProducto.getMiniatura());
        producto.setTags(detallesProducto.getTags());
        producto.setImagen(detallesProducto.getImagen());
       
        producto.setBarcode(detallesProducto.getBarcode());
        producto.setQrcode(detallesProducto.getQrcode());
        producto.setFechaActualizacion(LocalDateTime.now());
        producto.setWidth(detallesProducto.getWidth());
        producto.setHeight(detallesProducto.getHeight());
        producto.setDepth(detallesProducto.getDepth());


        
        final Productos productoActualizado = productosServices.actualizarProducto(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarProducto(@PathVariable Long id) {
        Productos producto = productosServices.obtenerProductoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + id));

        productosServices.eliminarProducto(producto);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return respuesta;
    }
}