package com.EcoMarket.Producto.Producto.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    private Integer sku;
    private String nombreProducto;
    private Integer precio;
    private Integer stock;
    private Integer idPedido;  // ID del pedido desde otro microservicio

    // @ManyToOne
    // @JoinColumn(name = "id_pedido", nullable = false, unique = true)
 
   // private Pedido id_pedido;  // PREGUNTAR SI CREARON LA CLASE ITEM_PEDIDO, PREGUNTAR EL NOMBRE COLUMNA ID DEL PEDIDO
}
