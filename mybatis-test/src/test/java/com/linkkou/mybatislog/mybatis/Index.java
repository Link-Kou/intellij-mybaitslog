package com.linkkou.mybatislog.mybatis;


import com.linkkou.mybatislog.mybatis.boot.App;
import com.linkkou.mybatislog.orm.dao.EmployeesDao;
import com.linkkou.mybatislog.orm.domain.Employees;
import com.plugins.mybaitslog.transformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    private EmployeesDao employeesDao;

    static {
        //注入
        final transformer transformer = new transformer(null);
        transformer.transform("test");
    }

    @Test
    public void queryById() {
        final Employees employees = employeesDao.queryById(12);
        System.out.println(employees);
    }

    @Test
    public void queryByJson() {
        final Employees employees = employeesDao.queryByInjectJson("12");
        System.out.println(employees);
    }

    @Test
    public void queryByMultiple() {
        final Employees employees = new Employees();
        employees.setEmpNo(12);
        employees.setLastName("");
        final List<Employees> query = employeesDao.queryByMultiple(employees);
        System.out.println(employees);
    }

    @Test
    public void queryByInject() {
        final Employees employees = employeesDao.queryByInject("'cs' or '1=1'");
        System.out.println(employees);
    }

    @Test
    public void queryByInjectD() {
        final Employees employees = employeesDao.queryByInjectD("111");
        System.out.println(employees);
    }

 @Test
    public void queryByInjectE() {
        final Employees employees = employeesDao.queryByInjectD("[httpServerRequest[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@67b80766]]");
        System.out.println(employees);
    }


    @Test
    public void queryByInjectLocalDate() {
        final LocalDate now = LocalDate.now();
        final Employees employees = employeesDao.queryByInjectLocalDate(now);
        System.out.println(employees);
    }

    @Test
    public void queryByInjectThread() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        int i = 5;
        for (int i1 = 0; i1 < i; i1++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        final Employees employees = new Employees();
                        employees.setEmpNo(12);
                        employees.setLastName("");
                        final List<Employees> query = employeesDao.queryByMultiple(employees);
                    }
                }
            });
            t.start();
        }
        countDownLatch.await();
    }
}
