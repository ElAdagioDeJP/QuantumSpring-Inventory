/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.CRUD.CRUD.repository;

import com.CRUD.CRUD.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jearm
 */
public interface ProductRepository extends JpaRepository<Productos, Long> {
}
