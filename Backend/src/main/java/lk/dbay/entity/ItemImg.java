package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemImg extends DateTime {

    @Id
    private String itemImgId;

    @Lob
    private byte[] itemImg;
    private String itemImgName;
    private String itemImgType;
    private boolean thumbnail;

    @ManyToOne
    private Item item;

}
