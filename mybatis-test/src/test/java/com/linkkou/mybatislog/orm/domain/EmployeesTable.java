package com.linkkou.mybatislog.orm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (Employees)实体类
 *
 * @author makejava
 * @since 2023-05-10 09:34:51
 */
@Data
@Accessors(chain = true)
@TableName("employees")
public class EmployeesTable implements Serializable {
    private static final long serialVersionUID = -97648538279091943L;
    
    private Integer empNo;
    
    private Date birthDate;
    
    private String firstName;
    
    private String lastName;
    
    private String gender;
    
    private Date hireDate;

}

