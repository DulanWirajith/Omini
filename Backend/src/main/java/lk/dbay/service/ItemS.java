package lk.dbay.service;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemImgDTO;
import lk.dbay.entity.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemS {
    ItemDTO addItem(Item item, MultipartFile[] files) throws Exception;

    List<ItemDTO> getItems();

    List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId);

    ItemImgDTO getItemImg(String id);

    List<ItemDTO> getItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit);

    ItemDTO getItemSelected(String itemId);

    boolean setItemAvailable(String itemId);
}
