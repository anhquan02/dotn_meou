package com.datn.meou.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Product;
import com.datn.meou.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        List<Product> products = productRepository.findByNameContaining(name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;
        if (products.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            list = products.subList(startItem, toIndex);
        }
        Page<Product> productPage = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
        return productPage;
    }

    public Product findById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public Product findByName(String name) {
        return this.productRepository.findByName(name);
    }

    public Product updateStatus(Long id, Integer status) {
        Product product = this.findById(id);
        if (product != null) {
            product.setStatus(status);
            return this.productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        Product product = this.findById(id);
        if (product != null) {
            product.setDeleted(!product.getDeleted());
            this.productRepository.save(product);
        }
    }
    
}
