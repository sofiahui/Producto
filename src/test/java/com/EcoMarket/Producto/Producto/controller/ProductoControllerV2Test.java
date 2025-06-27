package com.EcoMarket.Producto.Producto.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.EcoMarket.Producto.Producto.Assemblers.ProductoModelAssemblers;
import com.EcoMarket.Producto.Producto.model.Producto;
import com.EcoMarket.Producto.Producto.service.ProductoService;

@WebMvcTest(controllers = ProductoControllerV2.class)
public class ProductoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private ProductoModelAssemblers assembler;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto(3, "Cereal", 2500, 30, 2);
    }

    @Test
    void testCreateProducto() throws Exception {
        when(productoService.save(any(Producto.class))).thenReturn(producto);
        when(assembler.toModel(any(Producto.class))).thenReturn(EntityModel.of(producto));

        String jsonBody = """
            {
                "sku": 3,
                "nombreProducto": "Cereal",
                "precio": 2500,
                "stock": 30,
                "idPedido": 2
            }
        """;

        mockMvc.perform(post("/api/v2/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateProducto() throws Exception {
        producto.setNombreProducto("Cereal Integral");

        when(productoService.save(any(Producto.class))).thenReturn(producto);
        when(assembler.toModel(any(Producto.class))).thenReturn(EntityModel.of(producto));

        String jsonBody = """
            {
                "sku": 3,
                "nombreProducto": "Cereal Integral",
                "precio": 2700,
                "stock": 25,
                "idPedido": 2
            }
        """;

        mockMvc.perform(put("/api/v2/productos/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("Cereal Integral"));
    }

    @Test
    void testDeleteProducto() throws Exception {
        doNothing().when(productoService).delete(4);

        mockMvc.perform(delete("/api/v2/productos/4")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNoContent());
    }
}
