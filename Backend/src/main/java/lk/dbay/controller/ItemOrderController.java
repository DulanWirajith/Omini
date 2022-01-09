package lk.dbay.controller;

import lk.dbay.dto.ItemOrderDTO;
import lk.dbay.dto.OrderDetailDTO;
import lk.dbay.entity.ItemOrder;
import lk.dbay.entity.OrderDetail;
import lk.dbay.service.ItemOrderS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.ITEM_ORDER)
public class ItemOrderController {

    @Autowired
    private ItemOrderS itemOrderS;

    @PostMapping(value = "/addOrderDetail")
    public ResponseEntity addOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetailDTO orderDetailDTO = itemOrderS.addOrderDetail(orderDetail);
            if (orderDetailDTO != null) {
                return ResponseEntity.ok(orderDetailDTO);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(e.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getIncOrder/{customerId}")
    public ResponseEntity getInCompletedOrder(@PathVariable String customerId) {
        return ResponseEntity.ok(itemOrderS.getInCompletedOrder(customerId));
    }

    @PutMapping(value = "/updateOrderDetail/{updateType}")
    public ResponseEntity updateOrderDetail(@RequestBody OrderDetail orderDetail, @PathVariable String updateType) {
        try {
            OrderDetailDTO orderDetailDTO = itemOrderS.updateOrderDetail(orderDetail, updateType);
            if (orderDetailDTO != null) {
                return ResponseEntity.ok(orderDetailDTO);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/placeOrder")
    public ResponseEntity placeOrder(@RequestBody ItemOrder itemOrder) {
        try {
            ItemOrderDTO itemOrderDTO = itemOrderS.placeOrder(itemOrder);
            if (itemOrderDTO != null) {
                return ResponseEntity.ok(itemOrderDTO);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
//        return ResponseEntity.ok(itemOrderS.placeOrder(itemOrder));
    }
}
