package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification extends DateTime {

    @Id
    private String notificationId;
//    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    private DbayUser dbayUser;
}
