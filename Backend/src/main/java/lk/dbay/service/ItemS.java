package lk.dbay.service;

import lk.dbay.dto.ItemDTO;
import lk.dbay.entity.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemS {
    ItemDTO addItem(Item item, MultipartFile file) throws Exception;

    List<ItemDTO> getItems();
}
