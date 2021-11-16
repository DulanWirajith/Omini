package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Complaint extends DateTime {

    @Id
    private String complaintId;
    private String description;

    @ManyToOne(optional = false)
    private DbayUser complainedBy;

    @ManyToOne(optional = false)
    private Complaint complaint;
}
