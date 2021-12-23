package com.globits.da.dto;

import com.globits.da.domain.District;

public class DistrictDTO {
    private Long id;
    private String code;
    private String name;
    private CityDTO city;

    public DistrictDTO() {
    }

    public DistrictDTO(District entity){
        if (entity!=null){
            this.setId(entity.getId());
            this.setCode(entity.getCode());
            this.setName(entity.getName());
            this.setCity(new CityDTO(entity.getCity(),false));
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

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }
}
