package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.Coupon;
import ra.security.model.domain.Coupon;
import ra.security.model.dto.request.CouponRequest;
import ra.security.model.dto.response.CouponResponse;
import ra.security.repository.ICouponRepsository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.CouponMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponService implements IGenericService<CouponResponse, CouponRequest, Long> {
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private ICouponRepsository couponRepsository;

    @Override
    public List<CouponResponse> findAll() {
        return couponRepsository.findAll().stream()
                .map(d -> couponMapper.toResponse(d)).collect(Collectors.toList());
    }

    @Override
    public CouponResponse findById(Long aLong) throws CustomException {
        Optional<Coupon> discount = couponRepsository.findById(aLong);
        return discount.map(item -> couponMapper.toResponse(item)).orElseThrow(() ->
                new CustomException(("Coupon not found")));
    }

    @Override
    public CouponResponse save(CouponRequest couponRequest) throws  CustomException {
        if (couponRepsository.existsByName(couponRequest.getName())) {
            throw new CustomException("Coupon already exists");
        }
        return couponMapper.toResponse(couponRepsository.save(couponMapper.toEntity(couponRequest)));
    }

    @Override
    public CouponResponse update(CouponRequest couponRequest, Long id) {
        Coupon discount = couponMapper.toEntity(couponRequest);
        discount.setId(id);
        return couponMapper.toResponse(couponRepsository.save(discount));
    }

    @Override
    public CouponResponse delete(Long aLong) {
        Optional<Coupon> discount = couponRepsository.findById(aLong);
        if (discount.isPresent()) {
            couponRepsository.deleteById(aLong);
            return  couponMapper.toResponse(discount.get());
        }
        return null;
    }
}
