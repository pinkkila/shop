package fi.pink.resourceserver.product;

import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
