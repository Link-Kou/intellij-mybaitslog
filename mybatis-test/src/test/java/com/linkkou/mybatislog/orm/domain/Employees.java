package com.linkkou.mybatislog.orm.domain;

import java.util.Date;
import java.io.Serializable;

/**
 * (Employees)实体类
 *
 * @author makejava
 * @since 2023-05-10 09:34:51
 */
public class Employees implements Serializable {
    private static final long serialVersionUID = -97648538279091943L;
    
    private Integer empNo;
    
    private Date birthDate;
    
    private String firstName;
    
    private String lastName;
    
    private String gender;
    
    private Date hireDate;


    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

}

