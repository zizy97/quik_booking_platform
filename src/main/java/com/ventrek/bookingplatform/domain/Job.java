package com.ventrek.bookingplatform.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Job extends Trace {

    private static final long serialVersionUID = 4371716136989514711L;

    @Column(name = "job_number")
    private String jobNumber;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "category")
    private String category;

    @Column(name = "location")
    private String location;

    @Column(name = "vehicle")
    private String vehicle;

    @Column(name = "driver")
    private String driver;

}
