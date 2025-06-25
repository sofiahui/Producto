package com.EcoMarket.Producto.Producto.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcoMarket.Producto.Producto.Assemblers.ProductoModelAssemblers;
import com.EcoMarket.Producto.Producto.model.Producto;
import com.EcoMarket.Producto.Producto.service.ProductoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v2/productos")
@Tag(name="Productos",description="Operaciones relacionadas con los Productos")

public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssemblers assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }

    @GetMapping(value = "/{sku}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> getProductoBySku(@PathVariable Integer sku) {
        Producto producto = productoService.findById(sku);
        return assembler.toModel(producto);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoBySku(newProducto.getSku())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{sku}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Integer sku, @RequestBody Producto producto) {
      producto.setSku(sku);
     Producto updatedProducto = productoService.save(producto);
    return ResponseEntity.ok(assembler.toModel(updatedProducto));
}

    @DeleteMapping(value = "/{sku}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteProducto(@PathVariable Integer sku) {
    productoService.deleteBySku(sku);
    return ResponseEntity.noContent().build();
    }


}
