package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Coupon;

@Repository
public interface ICouponRepsository extends JpaRepository<Coupon, Long> {
    boolean existsByName(String discount);
}
