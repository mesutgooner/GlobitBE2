package com.globits.da.dto;

import com.globits.da.domain.Employee;

public class EmployeeDTO {

    private Long id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer age;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee e) {
        if (e!=null){
            this.setId(e.getId());
            this.setCode(e.getCode());
            this.setName(e.getName());
            this.setEmail(e.getEmail());
            this.setPhone(e.getPhone());
            this.setAge(e.getAge());
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
