package com.avilatek.ecommerce.ecommerce_api.controller;

import com.avilatek.ecommerce.ecommerce_api.dto.ProductDto;
import com.avilatek.ecommerce.ecommerce_api.dto.ProductResponse;
import com.avilatek.ecommerce.ecommerce_api.model.Product;
import com.avilatek.ecommerce.ecommerce_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Obtener todos los productos",
            description = "Obtiene una lista paginada de productos, con la opción de filtrar por disponibilidad.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @GetMapping
    public ResponseEntity<ProductResponse<Product>> getAllProducts(
            Pageable pageable,
            @RequestParam(value = "available", required = false) Boolean available) {

        Page<Product> page;
        if (Boolean.TRUE.equals(available)) {
            page = productService.getAvailableProducts(pageable);
        } else {
            page = productService.getAllProducts(pageable);
        }

        ProductResponse<Product> response = new ProductResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un producto por ID",
            description = "Obtiene los detalles de un producto específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del producto obtenidos exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "ID del producto a obtener", required = true) @PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto basado en los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody @Schema(description = "Datos del producto para crear uno nuevo") ProductDto productDto) {

        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @Operation(summary = "Actualizar un producto",
            description = "Actualiza un producto existente mediante su ID y los nuevos datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID del producto a actualizar", required = true) @PathVariable Long id,
            @RequestBody @Schema(description = "Datos actualizados del producto") ProductDto productDto) {

        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @Operation(summary = "Eliminar un producto",
            description = "Elimina un producto específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto a eliminar", required = true) @PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
