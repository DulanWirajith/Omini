package lk.dbay.service;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.BusinessReviewDTO;
import lk.dbay.dto.BusinessReviewResponseDTO;
import lk.dbay.entity.BusinessProfile;
import lk.dbay.entity.BusinessReview;
import lk.dbay.entity.BusinessReviewResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BusinessProfileS {

    BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile, MultipartFile file) throws Exception;

    BusinessProfileDTO getBusinessProfile(String businessProfileId, boolean needItems, String customerId, String type);

    BusinessProfileDTO updateBusinessProfile(BusinessProfile businessProfile, MultipartFile file, String businessProfileId) throws Exception;

    BusinessProfileDTO getItemsBusinessProfile(String businessProfileId, String categoryId, String customerId, String type);

    boolean followBusiness(String customerId, String businessProId);

    List<BusinessProfileDTO> getFollowedBusinesses(String customerId);

    BusinessReviewDTO getBusinessReviews(String businessId, String customerId);

    BusinessReviewDTO addBusinessReview(BusinessReview businessReview);

    BusinessReviewResponseDTO addBusinessReviewResponse(BusinessReviewResponse businessReviewResponse);

    List<BusinessProfileDTO> getShopsBySearch(String txt, String category, String district, String town, String customerId);

    BusinessReviewDTO updateBusinessReview(BusinessReview businessReview, String reviewId);

    boolean removeBusinessReview(String reviewId) throws Exception;
}
