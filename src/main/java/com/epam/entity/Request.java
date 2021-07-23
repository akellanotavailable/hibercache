package com.epam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({@NamedQuery(name = "user",
        query = "select r from Request r where r.user.id=:userId")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Request {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    //efpknrgjktbgjmbndkjnkljbn;klDMFc;lSNGV
    private String id;

    private String name;

    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
