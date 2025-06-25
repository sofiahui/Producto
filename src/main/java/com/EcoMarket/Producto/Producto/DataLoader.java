package com.EcoMarket.Producto.Producto;

// import java.util.List;
// import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.EcoMarket.Producto.Producto.model.Producto;
import com.EcoMarket.Producto.Producto.repository.ProductoRepository;

import net.datafaker.Faker;

@Component

public class DataLoader implements CommandLineRunner{

  @Autowired
    private ProductoRepository productoRepository;


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        //Random random = new Random();

        //limpiar datos antes
        
        productoRepository.deleteAll();
        
        
        // Generar productos
        for (int i = 0; i < 5; i++) {
                Producto producto = new Producto();
                producto.setNombreProducto(faker.commerce().productName());
                producto.setPrecio(faker.number().numberBetween(1000, 10000));
                producto.setStock(faker.number().numberBetween(1, 50));
                producto.setIdPedido(1); // Asegúrate de asignar un ID válido
                productoRepository.save(producto);
}


        // List<Producto> productos = productoRepository.findAll();
        
       
    }  



}
