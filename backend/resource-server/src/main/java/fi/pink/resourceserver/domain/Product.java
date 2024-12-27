package fi.pink.resourceserver.domain;

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
