package com.crud.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.springboot.models.Products;

public interface ProductRepository extends JpaRepository<Products, Integer> {

}
