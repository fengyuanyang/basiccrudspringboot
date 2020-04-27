package com.owen.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@ApiModel(description = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @ApiModelProperty(value = "company", hidden = true)
    private Company company;

    @ApiModelProperty(value = "client name", required = true)
    private String name;
    @ApiModelProperty(value = "email", required = true)
    private String email;

    @ApiModelProperty(value = "phone", required = true)
    private String phone;

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

    public Client(){}

    public Client(String name, String email, String phone, String createdBy, Date createdAt, String updatedBy, Date updatedAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
