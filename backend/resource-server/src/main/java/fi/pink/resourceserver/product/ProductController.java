package fi.pink.resourceserver.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    
    @GetMapping("/products/{requestedId}")
    private ResponseEntity<Product> getProduct(@PathVariable Long requestedId) {
        Optional<Product> product = productRepository.findById(requestedId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/shop/products/{requestedId}")
    private ResponseEntity<ShopProductDto> getShopProduct(@PathVariable Long requestedId) {
        Optional<Product> product = productRepository.findById(requestedId);
        return product.map(p -> ResponseEntity.ok(new ShopProductDto(p.getId(), p.getProductName())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
