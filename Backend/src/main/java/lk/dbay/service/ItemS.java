package lk.dbay.service;

import lk.dbay.dto.ItemDTO;
import lk.dbay.entity.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemS {
    ItemDTO addItem(Item item, MultipartFile file) throws Exception;

    List<ItemDTO> getItems();

    List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId);

    ItemDTO getItemImg(String id);

    List<ItemDTO> getItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit);

    boolean setItemAvailable(String itemId);
}
