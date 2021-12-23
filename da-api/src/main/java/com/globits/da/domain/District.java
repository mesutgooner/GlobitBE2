package com.globits.da.domain;

import com.globits.da.dto.DistrictDTO;

import javax.persistence.*;

@Entity
@Table(name = "tbl_district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public District() {
    }

    public District(DistrictDTO dto){
        if (dto!=null){
            this.setId(dto.getId());
            this.setCode(dto.getCode());
            this.setName(dto.getName());
            this.setCity(new City(dto.getCity(),false));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
