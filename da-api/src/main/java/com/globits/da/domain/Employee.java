package com.globits.da.domain;

import com.globits.da.dto.EmployeeDTO;

import javax.persistence.*;

@Entity
@Table(name = "tbl_employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer age;

    public Employee() {
    }

    public Employee(EmployeeDTO dto) {
        if (dto!=null){
            this.setId(dto.getId());
            this.setCode(dto.getCode());
            this.setName(dto.getName());
            this.setEmail(dto.getEmail());
            this.setPhone(dto.getPhone());
            this.setAge(dto.getAge());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
