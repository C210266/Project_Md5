package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.CustomException;
import ra.security.model.dto.request.CategoryRequest;
import ra.security.model.dto.response.CategoryResponse;
import ra.security.service.impl.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v4/admin/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryResponse>> getCateAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> addCate(@RequestBody CategoryRequest categoryRequest) throws CustomException {
        return new ResponseEntity<>(categoryService.save(categoryRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCate(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponse> updateCate(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) throws CustomException {
        return new ResponseEntity<>(categoryService.update(categoryRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCate(@PathVariable Long id) throws  CustomException {
        return new ResponseEntity<>(categoryService.delete(id), HttpStatus.OK);

    }
}
