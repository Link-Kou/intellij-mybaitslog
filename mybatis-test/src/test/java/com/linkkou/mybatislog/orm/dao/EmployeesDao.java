package com.linkkou.mybatislog.orm.dao;

import com.linkkou.mybatislog.orm.domain.Employees;
import com.linkkou.mybatislog.orm.entity.Pageable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Employees)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-10 09:34:49
 */
@Repository
public interface EmployeesDao {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    Employees queryById(@Param("id") Integer id);

    /**
     * 查询指定行数据
     *
     * @param employees 查询条件
     * @param pageable  分页对象
     * @return 对象列表
     */
    List<Employees> queryAllByLimit(Employees employees, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param employees 查询条件
     * @return 总行数
     */
    long count(Employees employees);

    /**
     * 新增数据
     *
     * @param employees 实例对象
     * @return 影响行数
     */
    int insert(Employees employees);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Employees> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Employees> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Employees> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Employees> entities);

    /**
     * 修改数据
     *
     * @param employees 实例对象
     * @return 影响行数
     */
    int update(Employees employees);

    /**
     * 多条件查询
     * @param employees 实例对象
     * @return 对象列表
     */
    List<Employees> queryByMultiple(Employees employees);

    /**
     * SQL注入
     *
     * @return 实例对象
     */
    Employees queryByInject(@Param("inject") String inject);
}

