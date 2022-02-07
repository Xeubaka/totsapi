package com.tots.api.totsapi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TB_VEHICLE")
public class Vehicle implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String model;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date dtFab;

    @NotNull
    @Column(nullable = false, length = 2)
    private Long kmCity;

    @NotNull
    @Column(nullable = false, length = 2)
    private Long KmRoad;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getDtFab() {
        return dtFab;
    }

    public void setDtFab(Date dtFab) {
        this.dtFab = dtFab;
    }

    public Long getKmCity() {
        return kmCity;
    }

    public void setKmCity(Long kmCity) {
        this.kmCity = kmCity;
    }

    public Long getKmRoad() {
        return KmRoad;
    }

    public void setKmRoad(Long kmRoad) {
        this.KmRoad = kmRoad;
    }

}
