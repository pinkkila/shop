package fi.pink.resourceserver.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShopProductJsonTests {

    @Autowired
    private JacksonTester<ShopProductDto> json;

    @Test
    void shopProductDtoSerializationTest() throws IOException {
        ShopProductDto shopProductDto = new ShopProductDto(1L, "tuote1");
        assertThat(json.write(shopProductDto)).isStrictlyEqualToJson("/json-examples/shopproductdto.json");
        assertThat(json.write(shopProductDto)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(shopProductDto)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
        assertThat(json.write(shopProductDto)).hasJsonPathStringValue("@.productName");
        assertThat(json.write(shopProductDto)).extractingJsonPathStringValue("@.productName").isEqualTo("tuote1");
    }

    @Test
    void shopProductDtoDeserializationTest() throws IOException {
        String expected = """
            {
                "id": 1,
                "productName": "tuote1"
            }
            """;
        assertThat(json.parse(expected)).isEqualTo(new ShopProductDto(1L, "tuote1"));
        assertThat(json.parseObject(expected).id()).isEqualTo(1);
        assertThat(json.parseObject(expected).productName()).isEqualTo("tuote1");
    }

}
