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

    BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile, MultipartFile[] files) throws Exception;

    BusinessProfileDTO getBusinessProfile(String businessProfileId, boolean needItems, String customerId);

    BusinessProfileDTO updateBusinessProfile(BusinessProfile businessProfile, MultipartFile[] files, String businessProfileId) throws Exception;

    BusinessProfileDTO getItemsBusinessProfile(String businessProfileId, String categoryId, String customerId);

    boolean followBusiness(String customerId, String businessProId);

    List<BusinessProfileDTO> getFollowedBusinesses(String customerId);

    List<BusinessReviewDTO> getBusinessReviews(String businessId, String customerId);

    BusinessReviewDTO addBusinessReview(BusinessReview businessReview);

    BusinessReviewResponseDTO addBusinessReviewResponse(BusinessReviewResponse businessReviewResponse);
}
