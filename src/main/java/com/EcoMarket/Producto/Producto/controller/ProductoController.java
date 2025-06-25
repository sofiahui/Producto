package com.EcoMarket.Producto.Producto.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.EcoMarket.Producto.Producto.model.Producto;
import com.EcoMarket.Producto.Producto.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los pruductos")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los prodcuto", description = "Obtiene una lista de todos los prodcutos")
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID", description = "Retorna un producto según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "Producto no encontrado"));
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Registra un nuevo producto en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza un producto existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto existente = productoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        producto.setSku(id); // Asegúrate de que tu entidad usa 'sku' como identificador
        return ResponseEntity.ok(productoService.save(producto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        Producto existente = productoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}