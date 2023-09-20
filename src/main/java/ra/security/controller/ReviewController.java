package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.CustomException;
import ra.security.model.dto.request.CategoryRequest;
import ra.security.model.dto.request.ReviewRequest;
import ra.security.model.dto.response.CategoryResponse;
import ra.security.model.dto.response.ReviewResponse;
import ra.security.service.impl.ReviewService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v4/auth/review")
@CrossOrigin("*")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/findReviewByPro/{proId}")
    public ResponseEntity<List<ReviewResponse>> findReviewByPro(@PathVariable Long proId) throws CustomException {
        return new ResponseEntity<>(reviewService.findAllByPro(proId), HttpStatus.OK);
    }

    @PostMapping("/addReviewToPro/{proId}/ByUser")
    public ResponseEntity<ReviewResponse> addReview(@Valid @RequestBody @ModelAttribute ReviewRequest reviewRequest, @PathVariable Long proId, HttpSession session) throws CustomException {
        return new ResponseEntity<>(reviewService.add(reviewRequest, proId, session.getAttribute("CurrentUser")), HttpStatus.CREATED);
    }

    @PutMapping("/update/{idReview}/InPro/{proId}")
    public ResponseEntity<ReviewResponse> updateReviewInPro(@Valid @RequestBody @ModelAttribute ReviewRequest reviewRequest, @PathVariable Long idReview, HttpSession session, @PathVariable Long proId) throws CustomException {
        return new ResponseEntity<>(reviewService.updateReviewInPro(reviewRequest, idReview, session.getAttribute("CurrentUser"), proId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idRe}")
    public ResponseEntity<?> deleteByUser(@PathVariable Long idRe, HttpSession session) throws CustomException {
        return new ResponseEntity<>(reviewService.deleteByUser(idRe, session.getAttribute("CurrentUser")), HttpStatus.OK);

    }
}
