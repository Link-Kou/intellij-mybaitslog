#最近遭受恶意的乱提Issues，本人强烈谴责这种行为。
#我不会屈服于这种行为。暂时关闭Issues

# Mybatislog

#### 2020.08.11 《IDEA 2020.2 测试通过支持使用。》
#### 2020.08.25 重构代码所有代码,代码中完善备注信息
#### 2020.09.19 添加对特点类型的引号支持
#### 2020.10.07 支持2020.2.3版本
#### 2020.11.20 改进分隔判断,由单纯通过,判断会存在无法对文本正常分隔的情况下。

### Mybatislog能做什么？

> Mybatislog是基于IntelliJ 开发的项目，用来格式化输出Mybatis的Sql。

 ![样列](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/2020-03-25_09-28-47.jpg "样列")
 
 ![样列](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/2020-04-17_23-51-18.gif "样列")
 
```sql

--  1  2020.04.10 23:30:19 CST DEBUG com.cms.dao.ProductTypeConfigTitleDao.queryAll - ==>
select f_id, f_name, f_preId, f_type, createtime, updatedtime
 FROM cms.t_product_type_config_title
 WHERE f_type = 2;
------------------------------------------------------------------------------------------------------------------------
--  2  2020.04.10 23:30:20 CST DEBUG com.cms.dao.ProductTypeConfigGroupDao.queryAll - ==>
select f_id, f_titleId, f_name, f_preId, f_type, createtime, updatedtime
 FROM cms.t_product_type_config_group
 WHERE f_type = 2;
------------------------------------------------------------------------------------------------------------------------
--  3  2020.04.10 23:30:20 CST DEBUG com.cms.dao.ProductTypeConfigItemDao.queryAll - ==>
select f_id, f_groupId, f_preId, f_name, f_type, createtime, updatedtime
 FROM cms.t_product_type_config_item
 WHERE f_type = 2;
------------------------------------------------------------------------------------------------------------------------

```
---

### 使用环境

`IntelliJ IDEA Ultimate版（172+）`

### 源代码构建

    项目管理：Gradle
   
### 在线安装(搜索)

  不提供在线插件库安装
 
### 手动安装

 2020.2 以下版本都支持
 >
 [plugin.intellij.assistant.mybaitslog-2020.X-1.0.8.jar](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/plugin/plugin.intellij.assistant.mybaitslog-1.0.8.jar)
  

> ##### 说明文档：
    
    1、插件基于日志来进行打印，如果无法打印SQL语句。排查一下日志
    2、插件默认随项目启动而启动
    3、如果不行！试试看！无敌大法，卸载插件，然后在重新安装