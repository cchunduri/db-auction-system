package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.dtos.ProductDto;
import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.db.codingchallenge.auctionserver.mappers.ProductMapper.toProductEntity;

@Service
@AllArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductDto productDto) {
        Product product = toProductEntity(productDto);
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(UUID id, ProductDto productUpdateDTO) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(productUpdateDTO.name());
                product.setDescription(productUpdateDTO.description());
                product.setPrice(productUpdateDTO.price());
                product.setQuantity(productUpdateDTO.quantity());
                return productRepository.save(product);
            });
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
