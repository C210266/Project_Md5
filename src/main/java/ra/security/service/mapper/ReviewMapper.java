package ra.security.service.mapper;
import org.springframework.stereotype.Component;
import ra.security.model.domain.Review;
import ra.security.model.dto.request.ReviewRequest;
import ra.security.model.dto.response.ReviewResponse;
import ra.security.service.IGenericMapper;

@Component
public class ReviewMapper implements IGenericMapper<Review, ReviewRequest, ReviewResponse> {
    @Override
    public Review toEntity(ReviewRequest reviewRequest) {
        return Review.builder()
                .product(reviewRequest.getProduct())
                .users(reviewRequest.getUsers())
                .comment(reviewRequest.getComment())
                .rating(reviewRequest.getRating())
                .build();
    }

    @Override
    public ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .product_id(review.getProduct().getId())
                .users_id(review.getUsers().getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }
}