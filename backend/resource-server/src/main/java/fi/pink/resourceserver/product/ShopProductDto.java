package fi.pink.resourceserver.product;

import org.springframework.data.annotation.Id;

public record ShopProductDto(@Id Long id, String productName ) {
}
