package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopCartDTO extends DateTimeDTO {

    private String cartId;
    private String cartTxt;
    private DbayUserDTO dbayUser;

    public ShopCartDTO(ShopCart shopCart) {
        if (shopCart != null) {
            this.cartId = shopCart.getCartId();
            this.cartTxt = shopCart.getCartTxt();
        }
    }

}
