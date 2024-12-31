package fi.pink.resourceserver.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("product")
public class Product {
    @Id
    private Long id;
    private String productName;
}
