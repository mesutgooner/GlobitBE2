package com.globits.da.service.impl;

import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDTO;
import com.globits.da.dto.ResponseMessage;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public Object saveOrUpdate(DistrictDTO dto) {
        if (dto!=null){
            District entity = null;
            if (dto.getId()!=null){
                entity = districtRepository.getOne(dto.getId());
            }
            entity = new District(dto);
            districtRepository.save(entity);
            return new DistrictDTO(entity);
        }
        return new ResponseMessage("Không thể lưu do không có dữ liệu truyền vào");
    }
}
