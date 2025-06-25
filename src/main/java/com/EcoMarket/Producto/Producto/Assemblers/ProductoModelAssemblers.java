package com.EcoMarket.Producto.Producto.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.EcoMarket.Producto.Producto.controller.ProductoControllerV2;
import com.EcoMarket.Producto.Producto.model.Producto;

@Component

public class ProductoModelAssemblers implements RepresentationModelAssembler<Producto, EntityModel<Producto>>{
@Override
    @NonNull
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).getProductoBySku(producto.getSku())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos"));
    }
}
