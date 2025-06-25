package com.EcoMarket.Producto.Producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// FIX INTERFACE
import com.EcoMarket.Producto.Producto.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByNombreProducto(String nombreProducto); // cambiar atributo en Model Producto
    
    Producto findBySku(Integer sku);

    List<Producto> findByNombreProductoAndSku(String nombreProducto, Integer sku);

    @Query("SELECT p FROM Producto p WHERE p.nombreProducto= :np")
    List<Producto> buscarPorNombreProducto(@Param("np") String np);

    @Query(value="SELECT * FROM Producto WHERE sku = :sku", nativeQuery= true)
    Producto buscarPorSku(@Param("sku") Integer sku);
  
    void deleteBySku(Integer sku);

}
