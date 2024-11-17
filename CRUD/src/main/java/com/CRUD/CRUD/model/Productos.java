package com.CRUD.CRUD.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private Double precio;
    private Double descuento;
    private Integer stock;
    private String marca;
    private String sku;
    private Double peso;
    private String informacionGarantia;
    private String informacionEnvio;
    private String estadoDisponibilidad;
    private String politicaDevolucion;
    private Integer cantidadMinimaPedido;
    private String miniatura;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String barcode;
    private String qrcode;

    @ElementCollection
    private List<String> tags;

    @ElementCollection
    private List<String> imagenes;

    @Embedded
    private Dimensions dimensiones;

    @Embedded
    private Meta meta;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Review> reviews;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getInformacionGarantia() {
        return informacionGarantia;
    }

    public void setInformacionGarantia(String informacionGarantia) {
        this.informacionGarantia = informacionGarantia;
    }

    public String getInformacionEnvio() {
        return informacionEnvio;
    }

    public void setInformacionEnvio(String informacionEnvio) {
        this.informacionEnvio = informacionEnvio;
    }

    public String getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(String estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public String getPoliticaDevolucion() {
        return politicaDevolucion;
    }

    public void setPoliticaDevolucion(String politicaDevolucion) {
        this.politicaDevolucion = politicaDevolucion;
    }

    public Integer getCantidadMinimaPedido() {
        return cantidadMinimaPedido;
    }

    public void setCantidadMinimaPedido(Integer cantidadMinimaPedido) {
        this.cantidadMinimaPedido = cantidadMinimaPedido;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public Dimensions getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(Dimensions dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}