package am.smartcode.first_spring.service.product;

import am.smartcode.first_spring.model.dto.product.CreateProductDto;
import am.smartcode.first_spring.model.dto.product.ProductDto;
import am.smartcode.first_spring.model.dto.product.ProductFilter;

import java.util.List;

public interface ProductService {

    ProductDto create(CreateProductDto createProductDto);

    List<ProductDto> getAll(ProductFilter productFilter);

    ProductDto getById(Integer id);
}
