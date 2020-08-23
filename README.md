# Mybatislog

### 2020.8.11 《IDEA 2020.2 测试通过支持使用。》

### 由于部分核心代码照搬某项目。现将代码重构。解决协议问题

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
    
    注意：国内网络原因，构建十分费力,如果不进行扶墙，几乎不可能完成构建
    （曾经的我是如此的单纯，挂机24小时就是没办法）
   
### 在线安装(搜索)

  不提供在线插件库安装
 
### 手动安装

 2020.2 以下版本都支持（协议冲突，暂不提供下载,以下链接暂时失效）
 [plugin.intellij.assistant.mybaitslog-2020.1-1.0.3.jar](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/plugin/plugin.intellij.assistant.mybaitslog-2020.1-1.0.3.jar)
  

> ##### 说明文档：
    
    1、插件基于日志来进行打印，如果无法打印SQL语句。排查一下日志
    2、插件默认随项目启动而启动
    3、如果不行！试试看！无敌大法，卸载插件，然后在重新安装