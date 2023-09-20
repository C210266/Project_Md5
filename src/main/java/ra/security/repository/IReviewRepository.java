package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Product;
import ra.security.model.domain.Review;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {

}
