package com.EcoMarket.Producto.Producto.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.EcoMarket.Producto.Producto.model.Producto;
import com.EcoMarket.Producto.Producto.repository.ProductoRepository;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = new Producto(1, "Pan integral", 1500, 20, 5);
    }

    @Test
    void testFindAll() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));

        List<Producto> productos = productoService.findAll();

        assertEquals(1, productos.size());
        verify(productoRepository).findAll();
    }

    @Test
    void testFindById_Existe() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Producto encontrado = productoService.findById(1);

        assertNotNull(encontrado);
        assertEquals("Pan integral", encontrado.getNombreProducto());
    }

    @Test
    void testFindById_NoExiste() {
        when(productoRepository.findById(999)).thenReturn(Optional.empty());

        Producto resultado = productoService.findById(999);

        assertNull(resultado);
    }

    @Test
    void testSave() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto guardado = productoService.save(producto);

        assertNotNull(guardado);
        assertEquals("Pan integral", guardado.getNombreProducto());
        verify(productoRepository).save(producto);
    }

    @Test
    void testDelete() {
        doNothing().when(productoRepository).deleteById(1);

        productoService.delete(1);

        verify(productoRepository).deleteById(1);
    }
}
