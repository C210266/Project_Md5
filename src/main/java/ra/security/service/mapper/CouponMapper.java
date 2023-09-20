package ra.security.service.mapper;

import org.springframework.stereotype.Component;
import ra.security.model.domain.Coupon;
import ra.security.model.dto.request.CouponRequest;
import ra.security.model.dto.response.CouponResponse;
import ra.security.service.IGenericMapper;

@Component
public class CouponMapper implements IGenericMapper<Coupon, CouponRequest, CouponResponse> {


    @Override
    public Coupon toEntity(CouponRequest couponRequest) {
        return null;
    }

    @Override
    public CouponResponse toResponse(Coupon coupon) {
        return null;
    }
}
