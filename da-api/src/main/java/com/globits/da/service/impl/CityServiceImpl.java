package com.globits.da.service.impl;

import com.globits.da.domain.City;
import com.globits.da.dto.CityDTO;
import com.globits.da.dto.ResponseMessage;
import com.globits.da.repository.CityRepository;
import com.globits.da.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public Object saveOrUpdate(CityDTO dto) {
        if (dto!=null){
            City entity = null;
            if (dto.getId()!=null){
                entity = cityRepository.getOne(dto.getId());
            }
            entity = new City(dto,true);
            cityRepository.save(entity);
            return new CityDTO(entity,true);
        }
        return new ResponseMessage("Không thể lưu do không có dữ liệu truyền vào");
    }
}
