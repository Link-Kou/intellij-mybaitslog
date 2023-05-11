package com.linkkou.mybatislog.orm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linkkou.mybatislog.orm.domain.EmployeesTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<EmployeesTable> {

}