package ra.security.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.domain.*;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.request.ProductUpdateRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.ICategoryRepository;
import ra.security.service.IGenericMapper;
import ra.security.service.upload_aws.StorageService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductMapper implements IGenericMapper<Product, ProductRequest, ProductResponse> {

    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private StorageService storageService;

    @Override
    public Product toEntity(ProductRequest productRequest) {
        // Lấy danh sách danh mục từ danh sách ID
        List<Category> categories = categoryRepository.findAllByIdIn(productRequest.getCategory());

        List<String> list = new ArrayList<>();
        for (MultipartFile file : productRequest.getFile()) {
            String imgUrl = storageService.uploadFile(file);
            list.add(imgUrl);
        }
        List<ImageProduct> imageProducts = list.stream()
                .map(url -> ImageProduct.builder().image(url).product(new Product()).build())
                .collect(Collectors.toList());

        // Xây dựng đối tượng Products bằng cách sử dụng thông tin từ ProductRequest và các đối tượng đã lấy
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                // Sử dụng orElse để tránh lỗi nếu không tìm thấy thương hiệu
                .category(categories)
                .stock(productRequest.getStock())
                .images(imageProducts)
                .created_at(new Date())
                .status(true)
                .build();

        return product;
    }
    public Product toEntity(ProductUpdateRequest productRequest) {
        // Lấy danh sách danh mục từ danh sách ID
        List<Category> categories = categoryRepository.findAllByIdIn(productRequest.getCategory());

        // Xây dựng đối tượng Products bằng cách sử dụng thông tin từ ProductRequest và các đối tượng đã lấy
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
               // Sử dụng orElse để tránh lỗi nếu không tìm thấy thương hiệu
                .category(categories)
                .stock(productRequest.getStock())
                .created_at(new Date())
                .status(true)
                .build();

        return product;
    }
    @Override
    public ProductResponse toResponse(Product products) {
        List<String> categories = products.getCategory().stream()
                .map(Category::getName).collect(Collectors.toList());


        List<String> images = products.getImages().stream()
                .map(ImageProduct::getImage).collect(Collectors.toList());

        return ProductResponse.builder()
                .id(products.getId())
                .name(products.getName())
                .price(products.getPrice())
                .description(products.getDescription())
                .stock(products.getStock())
                .main_image(products.getMain_image())
                .images(images)
                .category(categories)
                .coupon_id(products.getCoupon_id())
                .created_at(products.getCreated_at())
                .status(products.isStatus())
                .build();
    }

}
