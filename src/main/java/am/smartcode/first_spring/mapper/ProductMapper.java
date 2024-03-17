package am.smartcode.first_spring.mapper;

import am.smartcode.first_spring.model.dto.product.CreateProductDto;
import am.smartcode.first_spring.model.dto.product.ProductDto;
import am.smartcode.first_spring.model.dto.product.UpdateProductDto;
import am.smartcode.first_spring.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(CreateProductDto createProductDto);

    ProductDto toDto(ProductEntity product);

    void updateEntity(@MappingTarget ProductEntity product, UpdateProductDto updateProductDto);

}
