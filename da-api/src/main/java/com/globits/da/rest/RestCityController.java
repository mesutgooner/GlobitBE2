package com.globits.da.rest;

import com.globits.da.dto.CityDTO;
import com.globits.da.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/city")
public class RestCityController {
    @Autowired
    private CityService cityService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CityDTO dto){
        return new ResponseEntity<>(cityService.saveOrUpdate(dto), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CityDTO dto, @PathVariable Long id){
        dto.setId(id);
        return new ResponseEntity<>(cityService.saveOrUpdate(dto),HttpStatus.OK);
    }
}
