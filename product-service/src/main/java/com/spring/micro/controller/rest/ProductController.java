package com.spring.micro.controller.rest;

import com.spring.micro.dto.ProductDto;
import com.spring.micro.model.Product;
import com.spring.micro.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AccessToken accessToken;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) throws VerificationException {

        System.out.println(accessToken.getPreferredUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id,@Valid @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.update(id,productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable String id){
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAll(Pageable pageable){
        return ResponseEntity.ok(productService.getPagination(pageable));
    }
}
