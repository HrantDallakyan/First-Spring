package am.smartcode.first_spring.model.dto.product;

import am.smartcode.first_spring.model.dto.category.CategoryDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto extends BaseProductDto {

    private Integer id;

    private CategoryDto category;

}
