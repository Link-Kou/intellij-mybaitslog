package com.linkkou.mybatislog.mybatisplus;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linkkou.mybatislog.mybatisplus.boot.App;
import com.linkkou.mybatislog.orm.dao.EmployeesDao;
import com.linkkou.mybatislog.orm.dao.UserMapper;
import com.linkkou.mybatislog.orm.domain.Employees;
import com.linkkou.mybatislog.orm.domain.EmployeesTable;
import com.plugins.mybaitslog.transformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * A <code>main</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/5/9 12:11</b></p>
 */
@SpringBootTest(classes = App.class)
public class Index {

    @Autowired
    private UserMapper userMapper;

    static {
        //注入
        final transformer transformer = new transformer(null);
        transformer.transform("test");
    }

    @Test
    public void queryById() {
        final List<EmployeesTable> employeesTables = userMapper.selectList(null);
        System.out.println(employeesTables);
    }

    @Test
    public void doTest2() {
        userMapper.selectCount(Wrappers.<EmployeesTable>lambdaQuery()
                .eq(EmployeesTable::getEmpNo, 12));
    }


    @Test
    public void doTest3() {
        LambdaQueryWrapper<EmployeesTable> queryWrapper = new LambdaQueryWrapper<EmployeesTable>(new EmployeesTable());
        queryWrapper.eq(EmployeesTable::getEmpNo, 12);
        queryWrapper.eq(EmployeesTable::getEmpNo, 12);
        userMapper.selectList(queryWrapper);
    }

    @Test
    public void doTest4() {
        LambdaQueryWrapper<EmployeesTable> queryWrapper = new LambdaQueryWrapper<EmployeesTable>(new EmployeesTable());
        queryWrapper.eq(EmployeesTable::getLastName, "12");
        queryWrapper.eq(EmployeesTable::getLastName, "12");
        userMapper.selectList(queryWrapper);
    }

    @Test
    public void doTest5() {
        LambdaQueryWrapper<EmployeesTable> queryWrapper = new LambdaQueryWrapper<EmployeesTable>(new EmployeesTable());
        queryWrapper.eq(EmployeesTable::getHireDate, new Date());
        queryWrapper.eq(EmployeesTable::getHireDate, new Date());
        userMapper.selectList(queryWrapper);
    }


}
