package com.globits.da.rest;


import com.globits.da.dto.DistrictDTO;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/district")
public class RestDistrictController {
    @Autowired
    private DistrictService districtService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody DistrictDTO dto){
        return new ResponseEntity<>(districtService.saveOrUpdate(dto), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody DistrictDTO dto, @PathVariable Long id){
        dto.setId(id);
        return new ResponseEntity<>(districtService.saveOrUpdate(dto),HttpStatus.OK);
    }
}
