package com.redhat.sso.sample.model;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
public class Product extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="status", nullable = false)
    @Pattern(regexp = "ACTIVE|INACTIVE", flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotNull
    private String status;

    @Column(name="description", nullable = false)
    @Length(max = 255)
    @NotNull
    private String description;

    @Column(name="value", nullable = false)
    @Digits(integer = 5 /*precision*/, fraction = 2 /*scale*/)
    @NotNull
    private Long value;

    public Product() { }

    public Product(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getId().equals(product.getId()) &&
                getStatus().equals(product.getStatus()) &&
                getDescription().equals(product.getDescription()) &&
                getValue().equals(product.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getDescription(), getValue());
    }
}
