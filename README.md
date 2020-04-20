# Mybatislog


### 此项目拷贝于 [https://github.com/kookob/mybatis-log-plugin](https://github.com/kookob/mybatis-log-plugin)

> 原来项目在IDEA的 2019.3 版本的时候出现无法使用的情况。此项目已经修复BUG

> 我在原来的基础上对项目进行部分重写,有部分核心代码依旧采用原作者

> 项目会持续优化BUG，但是不添加新功能。如果原作者持续更新，优先建议使用原作者的

> 由于原作者提供一个收费版本,这里就不在对插件发布到插件市场


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

 2019.3.X
 [plugin.intellij.assistant.mybaitslog-2019.3-1.0.0.jar](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/plugin/plugin.intellij.assistant.mybaitslog-2019.3-1.0.0.jar)
 
 2020.1
 [plugin.intellij.assistant.mybaitslog-2020.1-1.0.1.jar](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/plugin/plugin.intellij.assistant.mybaitslog-2020.1-1.0.1.jar)
  

> ##### 说明文档：
    
    1、插件基于日志来进行打印，如果无法打印SQL语句。排查一下日志
    2、插件默认随项目启动而启动
    3、如果不行！试试看！无敌大法，卸载插件，然后在重新安装