package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.*;
import lk.dbay.repository.*;
import lk.dbay.service.ItemS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ItemSImpl implements ItemS {

    @Autowired
    private ItemR itemR;
    @Autowired
    private ItemPackageR itemPackageR;
    @Autowired
    private ItemFeatureR itemFeatureR;
    @Autowired
    private ItemImgR itemImgR;
    @Autowired
    private ItemReviewR itemReviewR;
    @Autowired
    private ItemReviewResponseR itemReviewResponseR;
    @Autowired
    private ItemItemFeatureR itemItemFeatureR;
    @Autowired
    private CustomerItemFavouriteR customerItemFavouriteR;
    @Value("${image.path}")
    private String filePath;

    @Override
    @Transactional
    public ItemDTO addItem(Item item, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            BusinessProfileCategory businessProfileCategory = item.getBusinessProfileCategory();
            item.setItemId("ITM" + businessProfileCategory.getBusinessProfile().getBusinessProId() + format);
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            addFeaturesToItem(item);
            addImagesToItem(item, files);
            return new ItemDTO(itemR.save(item), true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    @Transactional
    public ItemDTO updateItem(Item item, MultipartFile[] files, String itemId) throws Exception {
        try {
            Optional<Item> itemOptional = itemR.findById(itemId);
            if (itemOptional.isPresent()) {
                Item itemObj = itemOptional.get();
                itemObj.setName(item.getName());
                itemObj.setQuantity(item.getQuantity());
                itemObj.setPrice(item.getPrice());
                itemObj.setMakeToOrder(item.isMakeToOrder());
                itemObj.setQuantity(item.getQuantity());
                itemObj.setDescription(item.getDescription());
                itemObj.setDiscount(item.getDiscount());
                itemObj.setDiscountType(item.getDiscountType());
                itemObj.setBusinessProfileCategory(item.getBusinessProfileCategory());
                itemObj.getBusinessProfileCategory().setBusinessProfileCategoryId(
                        new BusinessProfileCategoryPK(itemObj.getBusinessProfileCategory().getBusinessProfile().getBusinessProId(), itemObj.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId())
                );
//                addFeaturesToItem(item);
//                addImagesToItem(item, files);

                HashSet<ItemItemFeature> itemItemFeatures = new HashSet<>(item.getItemItemFeatures());
                itemItemFeatures.retainAll(itemObj.getItemItemFeatures());
                Set<ItemItemFeature> itemItemFeaturesSetRemove = new HashSet<>(itemObj.getItemItemFeatures());
                itemItemFeaturesSetRemove.removeAll(itemItemFeatures);
                Set<ItemItemFeature> itemItemFeaturesSetAdd = new HashSet<>(item.getItemItemFeatures());
                itemItemFeaturesSetAdd.removeAll(itemItemFeatures);
                itemObj.setItemItemFeatures(itemItemFeaturesSetAdd);
                addFeaturesToItem(itemObj);

                HashSet<ItemImg> itemImgs = new HashSet<>(item.getItemImgs());
                itemImgs.retainAll(itemObj.getItemImgs());
                Set<ItemImg> itemImgsSetRemove = new HashSet<>(itemObj.getItemImgs());
                itemImgsSetRemove.removeAll(itemImgs);
                Set<ItemImg> itemImgsSetAdd = new HashSet<>(item.getItemImgs());
                itemImgsSetAdd.removeAll(itemImgs);
                itemObj.setItemImgs(itemImgsSetAdd);
                addImagesToItem(itemObj, files);

                if (itemItemFeaturesSetRemove.size() > 0) {
                    itemItemFeatureR.deleteAll(itemItemFeaturesSetRemove);
                }
                if (itemImgsSetRemove.size() > 0) {
                    itemImgR.deleteAll(itemImgsSetRemove);
                }

                return new ItemDTO(itemR.save(itemObj), true);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addImagesToItem(Item item, MultipartFile[] files) {
        try {
//            Set<ItemImg> itemImgs = new HashSet<>();
            for (ItemImg itemImg : item.getItemImgs()) {
                itemImg.setItem(item);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
//            String filePath = "C:\\xampp\\htdocs\\Dbay";
            String filePathCur = filePath + "\\item";
            for (MultipartFile file : files) {
                ItemImg itemImg = new ItemImg();
                itemImg.setItemImgId("ITIMG" + ++i + format);
//                itemImg.setItemImg(file.getBytes());
                Path root = Paths.get(filePathCur);
                if (!Files.exists(root)) {
                    Files.createDirectories(Paths.get(filePathCur));
                }
                try {
                    Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
                } catch (FileAlreadyExistsException e) {
                    e.printStackTrace();
                }
                itemImg.setItemImgName("item/" + StringUtils.cleanPath(file.getOriginalFilename()));
//                itemImg.setItemImgPath("C:\\xampp\\htdocs\\Dbay\\" + itemImg.getItemImgName());
                itemImg.setItemImgType(file.getContentType());
                itemImg.setItem(item);
                item.getItemImgs().add(itemImg);
            }
//            item.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFeaturesToItem(Item item) {
        for (ItemItemFeature itemItemFeature : item.getItemItemFeatures()) {
//            if (itemItemFeature.getItemFeature().getItemFeatureId().equals("0")) {
//                LocalDateTime localDateTime = LocalDateTime.now();
//                String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            ItemFeature itemFeatureObj = itemItemFeature.getItemFeature();
            itemFeatureObj.setItemFeatureId("ITF" + itemFeatureObj.getName().replace(" ", "_") + item.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId());
            ItemFeature itemFeature = itemFeatureR.save(itemItemFeature.getItemFeature());
            itemItemFeature.setItemFeature(itemFeature);
//            }
            itemItemFeature.setItemItemFeatureId(new ItemItemFeaturePK(item.getItemId(), itemItemFeature.getItemFeature().getItemFeatureId()));
            itemItemFeature.setItem(item);
        }
    }

    @Override
    public List<ItemDTO> getItems() {
        List<Item> itemList = itemR.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
            itemDTOS.add(new ItemDTO(item, false));
        }
        return itemDTOS;
    }

    @Override
    public List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId) {
        List<Item> itemList = itemR.getAllByBusinessProfileCategory_BusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
            itemDTOS.add(new ItemDTO(item, false));
        }
        return itemDTOS;
    }

    @Override
    public ItemImgDTO getItemImg(String id) {
        Optional<ItemImg> itemImgOptional = itemImgR.findById(id);
        if (itemImgOptional.isPresent()) {
            ItemImg itemImg = itemImgOptional.get();
            return new ItemImgDTO(itemImg);
        }
        return null;
    }

    @Override
    public List<ItemDTO> getItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit) {
        List<Item> itemList = itemR.getItemsOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId), PageRequest.of(start, limit));
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
            itemDTOS.add(new ItemDTO(item, true));
        }
        return itemDTOS;
    }

    @Override
    public ItemDTO getItemSelected(String itemId) {
        Optional<Item> itemOptional = itemR.findById(itemId);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
//            List<ItemFeature> itemFeatureRAll = itemFeatureR.getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(item.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId(), true);
//            List<ItemFeatureDTO> itemFeatureDTOS = new ArrayList<>();
//            for (ItemFeature itemFeature : itemFeatureRAll) {
//                itemFeatureDTOS.add(new ItemFeatureDTO(itemFeature));
//            }
            ItemDTO itemDTO = new ItemDTO(item, true);
            itemDTO.setBusinessProfileCategory(item);
            itemDTO.setItemItemFeatures(item);
            itemDTO.setItemFeatures(item);
            itemDTO.setItemCategory(item);
            itemDTO.setOrderDetail(new OrderDetailDTO());
            return itemDTO;
        }
        return null;
    }

    @Override
    public boolean setItemAvailable(String itemId) {
        Optional<Item> itemOptional = itemR.findById(itemId);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setAvailable(!item.isAvailable());
            itemR.save(item);
            return item.isAvailable();
        }
        return false;
    }

    @Override
    public ItemItemPackageDTO getItemsPackagesBySearch(String txt, String category) {
        List<Item> itemsBySearch;
        List<ItemPackage> itemPackagesBySearch;
        if (category.equals("no")) {
            itemsBySearch = itemR.getItemsBySearch("%" + txt + "%");
            itemPackagesBySearch = itemPackageR.getItemPackagesBySearch("%" + txt + "%");
        } else {
            itemsBySearch = itemR.getItemsBySearch("%" + txt + "%", category);
            itemPackagesBySearch = itemPackageR.getItemPackagesBySearch("%" + txt + "%", category);
        }
        return new ItemItemPackageDTO(itemsBySearch, itemPackagesBySearch, true);
    }

    @Override
    public ItemReviewDTO addItemReview(ItemReview itemReview) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        itemReview.setItemReviewId("IR" + format);
        itemReview = itemReviewR.save(itemReview);
        ItemReviewDTO itemReviewDTO = new ItemReviewDTO(itemReview);
        itemReviewDTO.setCustomerProfile(itemReview);
        return itemReviewDTO;
    }

    @Override
    public List<ItemReviewDTO> getItemReviews(String itemId, String customerId, String reviewType) {
        List<ItemReview> itemReviews = null;
        if (reviewType.equals("Item")) {
            itemReviews = itemReviewR.getAllByItem_ItemIdAndReviewType(itemId, reviewType);
        } else if (reviewType.equals("ItemPackage")) {
            itemReviews = itemReviewR.getAllByItemPackage_ItemPackageIdAndReviewType(itemId, reviewType);
        }
        List<ItemReviewDTO> itemReviewDTOS = new ArrayList<>();
        if (itemReviews != null) {
            for (ItemReview itemReview : itemReviews) {
                ItemReviewDTO itemReviewDTO = new ItemReviewDTO(itemReview);
                List<ItemReviewResponse> responses = itemReviewResponseR.getAllByItemReview_ItemReviewId(itemReview.getItemReviewId());
                for (ItemReviewResponse reviewResponse : responses) {
                    if (reviewResponse.getCustomerProfile().getCustomerProId().equals(customerId)) {
                        itemReviewDTO.setResponseByMe(new ItemReviewResponseDTO(reviewResponse));
                    }
                    if (reviewResponse.getResponse().equals("like")) {
                        itemReviewDTO.setLikeCount(itemReviewDTO.getLikeCount() + 1);
                    } else if (reviewResponse.getResponse().equals("dislike")) {
                        itemReviewDTO.setDislikeCount(itemReviewDTO.getDislikeCount() + 1);
                    }
                }
                itemReviewDTO.setCustomerProfile(itemReview);
                itemReviewDTOS.add(itemReviewDTO);
            }
        }
        return itemReviewDTOS;
    }

    @Override
    public ItemReviewResponseDTO addItemReviewResponse(ItemReviewResponse itemReviewResponse) {
        if (!itemReviewResponse.getResponse().equals("remove")) {
            if (itemReviewResponse.getItemReviewResponseId().equals("")) {
                LocalDateTime localDateTime = LocalDateTime.now();
                String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                itemReviewResponse.setItemReviewResponseId("IRRE" + format);
                return new ItemReviewResponseDTO(itemReviewResponseR.save(itemReviewResponse));
            } else {
                Optional<ItemReviewResponse> optionalItemReviewResponse = itemReviewResponseR.findById(itemReviewResponse.getItemReviewResponseId());
                if (optionalItemReviewResponse.isPresent()) {
                    ItemReviewResponse itemReviewResponseObj = optionalItemReviewResponse.get();
                    itemReviewResponseObj.setResponse(itemReviewResponse.getResponse());
                    return new ItemReviewResponseDTO(itemReviewResponseR.save(itemReviewResponse));
                }
            }
        } else {
            itemReviewResponseR.deleteById(itemReviewResponse.getItemReviewResponseId());
            return new ItemReviewResponseDTO(itemReviewResponse);
        }
        return null;
    }

    @Override
    public boolean removeItem(String itemId) throws Exception {
        try {
            itemR.deleteById(itemId);
            return true;
        } catch (Exception e) {
            throw new Exception("You cannot remove this item, since it is used.");
        }
    }

    @Override
    public boolean setItemFavourite(String customerId, String itemId) {
        Optional<CustomerItemFavourite> itemFavourite = customerItemFavouriteR.findById(new CustomerItemFavouritePK(customerId, itemId));
        if (itemFavourite.isPresent()) {
            customerItemFavouriteR.deleteById(new CustomerItemFavouritePK(customerId, itemId));
            return false;
        } else {
            CustomerItemFavourite customerItemFavourite = new CustomerItemFavourite();
            customerItemFavourite.setCustomerItemFavouriteId(new CustomerItemFavouritePK(customerId, itemId));
            CustomerProfile customerProfile = new CustomerProfile();
            customerProfile.setCustomerProId(customerId);
            customerItemFavourite.setCustomerProfile(customerProfile);
            Item item = new Item();
            item.setItemId(itemId);
            customerItemFavourite.setItem(item);
            customerItemFavouriteR.save(customerItemFavourite);
            return true;
        }
//        return new CustomerItemFavouriteDTO(customerItemFavourite.getCustomerProfile(), customerItemFavourite.getItem());
    }
}
