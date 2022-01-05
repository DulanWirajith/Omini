package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopCart extends DateTime {

    @Id
    private String cartId;
    @Column(columnDefinition = "TEXT")
    private String cartTxt;

    @ManyToOne(optional = false)
    private DbayUser dbayUser;
}
