package com.globits.da.rest;

import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class RestEmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody EmployeeDTO dto){
        return new ResponseEntity<>(employeeService.saveOrUpdate(dto), HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<?> searchByDto(@RequestBody EmployeeSearchDTO dto){
        return new ResponseEntity<>(employeeService.search(dto),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody EmployeeDTO dto, @PathVariable("id") Long id){
        dto.setId(id);
        return new ResponseEntity<>(employeeService.saveOrUpdate(dto),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        employeeService.delete(id);
    }
    @GetMapping("/exportExcel")
    public ResponseEntity<?> exportExcel(){
        return new ResponseEntity<>(employeeService.exportExcel(),HttpStatus.OK);
    }
}
