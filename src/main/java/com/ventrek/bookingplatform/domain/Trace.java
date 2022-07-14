package com.ventrek.bookingplatform.domain;

import com.ventrek.bookingplatform.security.models.User;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class Trace implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String createdUser;

    @PrePersist
    @PreUpdate
    private void beforeAnyUpdate() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User userPrincipal = ((User) principal);

            this.createdUser = userPrincipal.getEmail();
        }

        this.createdDate = new Date();
    }

}
