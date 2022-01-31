package lk.dbay.service;

import lk.dbay.dto.*;
import lk.dbay.entity.item.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemS {

    ItemDTO addItem(Item item, MultipartFile[] files) throws Exception;

    ItemDTO updateItem(Item item, MultipartFile[] files, String itemId) throws Exception;

//    List<ItemDTO> getItems();

    boolean removeItem(String itemId) throws Exception;

    List<ItemDTO> getItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit);

    ItemDTO getItemSelected(String itemId);

    List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId);

}