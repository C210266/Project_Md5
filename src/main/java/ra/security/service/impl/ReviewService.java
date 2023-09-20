package ra.security.service.impl;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.CustomException;
import ra.security.model.domain.Category;
import ra.security.model.domain.Product;
import ra.security.model.domain.Review;
import ra.security.model.domain.Users;
import ra.security.model.dto.request.ReviewRequest;
import ra.security.model.dto.response.ReviewResponse;
import ra.security.repository.IProductRepository;
import ra.security.repository.IReviewRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.ProductMapper;
import ra.security.service.mapper.ReviewMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IGenericService<ReviewResponse, ReviewRequest, Long> {
    @Autowired
    private IReviewRepository reviewRepository;
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<ReviewResponse> findAll() {
        return null;
    }

    public List<ReviewResponse> findAllByPro(Long idPro) throws CustomException {
        Optional<Product> optional = productRepository.findById(idPro);
        if (optional.isPresent()) {
            if (optional.get().getReviews() == null) {
                throw new CustomException("No review to show");
            } else {
                return optional.get().getReviews()
                        .stream().map(review -> reviewMapper.toResponse(review))
                        .collect(Collectors.toList());
            }
        }
        throw new CustomException("Product not found");
    }

    @Override
    public ReviewResponse findById(Long aLong) throws CustomException {
        Optional<Review> optional = reviewRepository.findById(aLong);
        return reviewMapper.toResponse(optional.orElseThrow(()
                -> new CustomException("Review not found")));
    }

    @Override
    public ReviewResponse save(ReviewRequest reviewRequest) throws CustomException {
        return reviewMapper.toResponse(reviewRepository.save(reviewMapper.toEntity(reviewRequest)));
    }

    @Override
    public ReviewResponse update(ReviewRequest reviewRequest, Long id) throws CustomException {
        Review review = reviewMapper.toEntity(reviewRequest);
        review.setId(id);
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    public ReviewResponse updateReviewInPro(ReviewRequest reviewRequest, Long idReview, Object user, Long proId) throws CustomException {
        Optional<Users> u = userService.findByUserName(String.valueOf(user));
        Optional<Product> p = productRepository.findById(proId);
        Optional<Review> review = reviewRepository.findById(idReview);
        if (!(p.isPresent())) {
            throw new CustomException("Product not found");
        }
        if (review.isPresent()) {
            if (review.get().getProduct().getId() == proId
                    && review.get().getUsers().getId() == u.get().getId()) {
                ReviewRequest request = ReviewRequest.builder()
                        .users(u.get())
                        .comment(reviewRequest.getComment())
                        .product(p.orElseThrow(() -> new CustomException("Product not found")))
                        .rating(reviewRequest.getRating())
                        .build();
                return reviewMapper.toResponse(reviewRepository.save(reviewMapper.toEntity(request)));
            } else {
                throw new CustomException("Review not exist, plz add review to update");
            }
        }
        throw new CustomException("Review not exist, plz add review to update");
    }

    @Override
    public ReviewResponse delete(Long aLong) throws CustomException {
        Optional<Review> id = reviewRepository.findById(aLong);
        if (id.isPresent()) {
            reviewRepository.deleteById(aLong);
            return reviewMapper.toResponse(id.get());
        }
        return null;
    }

    public ReviewResponse deleteByUser(Long aLong, Object user) throws CustomException {
        Optional<Users> u = userService.findByUserName(String.valueOf(user));
        Optional<Review> review = reviewRepository.findById(aLong);
        if (review.isPresent()) {
            if (review.get().getUsers().getId() == u.get().getId()) {
                reviewRepository.deleteById(aLong);
                return reviewMapper.toResponse(review.get());
            } else {
                throw new CustomException("Cant find review by this user");
            }

        } else {
            throw new CustomException("Review isn't exist");
        }
    }

    public ReviewResponse add(ReviewRequest reviewRequest, Long proId, Object user) throws CustomException {
        Optional<Users> u = userService.findByUserName(String.valueOf(user));
        Optional<Product> p = productRepository.findById(proId);

        ReviewRequest request = ReviewRequest.builder()
                .users(u.get())
                .comment(reviewRequest.getComment())
                .product(p.orElseThrow(() -> new CustomException("Product not found")))
                .rating(reviewRequest.getRating())
                .build();
        return reviewMapper.toResponse(reviewRepository.save(reviewMapper.toEntity(request)));
    }
}
