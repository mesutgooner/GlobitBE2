package com.globits.da.service.impl;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.EmployeeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDTO saveOrUpdate(EmployeeDTO dto) {
        if (dto!=null){
            Employee result = null;
            if (dto.getId()!=null){
                result = employeeRepository.getOne(dto.getId());
            }
            result = new Employee(dto);
            employeeRepository.save(result);
            return new EmployeeDTO(result);
        }
        return null;
    }

    @Override
    public List<EmployeeDTO> search(EmployeeSearchDTO dto) {
        List<Employee> entities = employeeRepository.findAll();
        List<EmployeeDTO> results = new ArrayList<>();
        if (dto!=null){
            if (dto.getKeyword()!=null && StringUtils.hasText(dto.getKeyword()))
                entities = employeeRepository.search(dto.getKeyword());
        }
        for (Employee e:entities){
            results.add(new EmployeeDTO(e));
        }
        if (results.size()>0)
            return results;
        else
            return null;
    }

    @Override
    public void delete(Long id) {
        if (employeeRepository.existsById(id))
            employeeRepository.deleteById(id);
    }

    @Override
    public File exportExcel(){
        String[] cols = {"STT","Tên","Mã","Email","SDT","Tuổi"};
        List<Employee> employees = employeeRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //format and create header
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i=0;i<cols.length;i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(cols[i]);
            cell.setCellStyle(headerCellStyle);
        }
        //Write data
        int rowNum = 1;
        int stt=1;
        for (Employee employee:employees){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt);
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getCode());
            row.createCell(3).setCellValue(employee.getEmail());
            row.createCell(4).setCellValue(employee.getPhone());
            row.createCell(5).setCellValue(employee.getAge());
            stt++;
        }
        //Write file
        try {
            File file = new File("Employee.xls");
            FileOutputStream fop = new FileOutputStream(file);
            if (!file.exists()){
                file.createNewFile();
            }
            workbook.write(fop);
            fop.close();
            workbook.close();

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
