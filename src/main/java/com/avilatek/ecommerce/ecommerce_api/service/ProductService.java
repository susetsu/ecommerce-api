package com.avilatek.ecommerce.ecommerce_api.service;


import com.avilatek.ecommerce.ecommerce_api.dto.ProductDto;
import com.avilatek.ecommerce.ecommerce_api.model.Product;
import com.avilatek.ecommerce.ecommerce_api.respository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product no encontrado"));
    }

    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        Product product = getProductById(id);

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public Page<Product> getAvailableProducts(Pageable pageable) {
        return productRepository.findByStockQuantityGreaterThan(0, pageable);
    }
}