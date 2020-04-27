package com.owen.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@ApiModel(description = "Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "company name", required = true)
    private String name;

    @ApiModelProperty(value = "company address", required = true)
    private String address;

    @ApiModelProperty(value = "created by", hidden = true)
    @Column(name="created_by")
    private String createdBy;

    @ApiModelProperty(value = "created at", hidden = true)
    @Column(name="created_at")
    private Date createdAt;

    @ApiModelProperty(value = "updated by", hidden = true)
    @Column(name="updated_by")
    private String updatedBy;

    @ApiModelProperty(value = "updated at", hidden = true)
    @Column(name="updated_at")
    private Date updatedAt;

    public Company(){}

    public Company(String name, String address, String createdBy, Date createdAt, String updatedBy, Date updatedAt) {
        this.name = name;
        this.address = address;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


}
