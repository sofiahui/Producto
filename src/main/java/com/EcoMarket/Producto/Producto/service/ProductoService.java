package com.EcoMarket.Producto.Producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarket.Producto.Producto.model.Producto;
import com.EcoMarket.Producto.Producto.repository.ProductoRepository;

import jakarta.transaction.Transactional;
//  FIX IMPORT
@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Integer id){
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Integer id){
        productoRepository.deleteById(id);
    }

}
