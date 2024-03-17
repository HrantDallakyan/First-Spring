package am.smartcode.first_spring.contoller;

import am.smartcode.first_spring.exception.EntityNotFoundException;
import am.smartcode.first_spring.model.dto.product.CreateProductDto;
import am.smartcode.first_spring.model.dto.product.ProductDto;
import am.smartcode.first_spring.model.dto.product.ProductFilter;
import am.smartcode.first_spring.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//@Controller
//@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(createProductDto));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductDto>> getAll(@RequestBody ProductFilter productFilter) {
//        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
        return ResponseEntity.ok(productService.getAll(productFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


}
