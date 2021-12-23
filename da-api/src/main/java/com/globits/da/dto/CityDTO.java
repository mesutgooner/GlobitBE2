package com.globits.da.dto;

import com.globits.da.domain.City;
import com.globits.da.domain.District;

import java.util.ArrayList;
import java.util.List;

public class CityDTO {
    private Long id;
    private String code;
    private String name;
    private List<DistrictDTO> districts = new ArrayList<>();

    public CityDTO() {
    }

    public CityDTO(City entity, boolean needDistricts){
        if (entity!=null){
            this.setId(entity.getId());
            this.setCode(entity.getCode());
            this.setName(entity.getName());
            if (needDistricts){
                if (entity.getDistricts()!=null && entity.getDistricts().size()>0){
                    for (District district: entity.getDistricts()){
                        DistrictDTO districtDTO = new DistrictDTO(district);
                        this.getDistricts().add(districtDTO);
                    }
                }
            }
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

    public List<DistrictDTO> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictDTO> districts) {
        this.districts = districts;
    }
}
