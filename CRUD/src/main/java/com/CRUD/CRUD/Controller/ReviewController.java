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
import com.CRUD.CRUD.model.Review;
import com.CRUD.CRUD.Servicios.ProductosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos/{productoId}/reviews")
public class ReviewController {

    @Autowired
    private ProductosServices productosServices;

    @GetMapping
    public List<Review> obtenerReviews(@PathVariable Long productoId) {
        Productos producto = productosServices.obtenerProductoPorId(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + productoId));
        return producto.getReviews();
    }

    @PostMapping
    public Review crearReview(@PathVariable Long productoId, @RequestBody Review review) {
        Productos producto = productosServices.obtenerProductoPorId(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + productoId));
        producto.getReviews().add(review);
        productosServices.actualizarProducto(producto);
        return review;
    }

    @PutMapping("/{reviewId}")
    public Review actualizarReview(@PathVariable Long productoId, @PathVariable Long reviewId, @RequestBody Review detallesReview) {
        Productos producto = productosServices.obtenerProductoPorId(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + productoId));
        Review review = producto.getReviews().stream()
                .filter(r -> r.getId().equals(reviewId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrada para este id :: " + reviewId));
        review.setRating(detallesReview.getRating());
        review.setComment(detallesReview.getComment());
        review.setDate(detallesReview.getDate());
        review.setReviewerName(detallesReview.getReviewerName());
        review.setReviewerEmail(detallesReview.getReviewerEmail());
        productosServices.actualizarProducto(producto);
        return review;
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> eliminarReview(@PathVariable Long productoId, @PathVariable Long reviewId) {
        Productos producto = productosServices.obtenerProductoPorId(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para este id :: " + productoId));
        Review review = producto.getReviews().stream()
                .filter(r -> r.getId().equals(reviewId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrada para este id :: " + reviewId));
        producto.getReviews().remove(review);
        productosServices.actualizarProducto(producto);
        return ResponseEntity.ok().build();
    }
}
