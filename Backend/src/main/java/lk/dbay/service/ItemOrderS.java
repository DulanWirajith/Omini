package lk.dbay.service;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemImgDTO;
import lk.dbay.dto.ItemItemPackageDTO;
import lk.dbay.dto.ItemOrderDTO;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemOrder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemOrderS {

    ItemOrderDTO addOrder(ItemOrder itemOrder);
}
