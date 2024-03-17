package am.smartcode.first_spring.service.product.impl;

import am.smartcode.first_spring.exception.EntityNotFoundException;
import am.smartcode.first_spring.mapper.ProductMapper;
import am.smartcode.first_spring.model.dto.product.CreateProductDto;
import am.smartcode.first_spring.model.dto.product.ProductDto;
import am.smartcode.first_spring.model.dto.product.ProductFilter;
import am.smartcode.first_spring.model.entity.CategoryEntity;
import am.smartcode.first_spring.model.entity.ProductEntity;
import am.smartcode.first_spring.repository.CategoryRepository;
import am.smartcode.first_spring.repository.ProductRepository;
import am.smartcode.first_spring.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public ProductDto create(CreateProductDto createProductDto) {
        ProductEntity entity = productMapper.toEntity(createProductDto);
        CategoryEntity categoryEntity = categoryRepository.
                findById(createProductDto.getCategoryId()).
                orElseThrow(() -> new EntityNotFoundException(""));
        entity.setCategory(categoryEntity);
        productRepository.save(entity);
        return productMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAll(ProductFilter productFilter) {

        Specification<ProductEntity> specification = Specification.where((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            Predicate filterPredicate;

            BigDecimal startPrice = productFilter.getStartPrice();
            if (Objects.nonNull(startPrice)) {
                filterPredicate = criteriaBuilder
                        .greaterThanOrEqualTo(root.get("price"), startPrice);
                predicates.add(filterPredicate);
            }

            BigDecimal endPrice = productFilter.getEndPrice();
            if (Objects.nonNull(endPrice)) {
                filterPredicate = criteriaBuilder
                        .lessThanOrEqualTo(root.get("price"), endPrice);
                predicates.add(filterPredicate);
            }

            Integer categoryId = productFilter.getCategoryId();
            if (categoryId != null) {
                filterPredicate = criteriaBuilder.equal(root.get("category"), categoryId);
                predicates.add(filterPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });


        return productRepository.findAll(specification)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Integer id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: %d not found.", id)));
        return productMapper.toDto(productEntity);
    }
}
