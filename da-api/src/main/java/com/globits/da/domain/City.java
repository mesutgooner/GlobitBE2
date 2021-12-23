package com.globits.da.domain;

import com.globits.da.dto.CityDTO;
import com.globits.da.dto.DistrictDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @OneToMany(mappedBy = "city", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<District> districts = new ArrayList<>();

    public City() {
    }

    public City(CityDTO dto, boolean needDistricts){
        if (dto!=null){
            this.setId(dto.getId());
            this.setCode(dto.getCode());
            this.setName(dto.getName());
            if (needDistricts){
                if (dto.getDistricts()!=null && dto.getDistricts().size()>0){
                    for (DistrictDTO districtDTO: dto.getDistricts()){
                        District district = new District(districtDTO);
                        this.getDistricts().add(district);
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

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
