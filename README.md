# [![Mybatislog](logo-bird-ninja.svg)](https://github.com/Link-Kou/intellij-mybaitslog) Mybatislog

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Link-Kou/intellij-mybaitslog)
![GitHub](https://img.shields.io/github/license/Link-Kou/intellij-mybaitslog)
![GitHub issues](https://img.shields.io/github/issues/Link-Kou/intellij-mybaitslog)

### Mybatislog能做什么？

> Mybatislog是基于IntelliJ 开发的项目，用来格式化输出Mybatis的Sql。

![样列](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/2020-03-25_09-28-47.jpg "样列")

![样列](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/2020-04-17_23-51-18.gif "样列")

## <p style="color:red">★★重大更新★★</p>
> 版本从V3.*开始<b style="color:red">支持所有数据库的SQL都能均能正常格式化输出</b><br/>
> 版本从V3.*开始必须需要配合Mybatis插件使用否则就无效。<br/>
> 版本从V3.*插件只支持IDEA版本2020.3以上<br/>
> 版本从V3.*插件只支持SpringBoot版本2以上或SpringCloud<br/>
> 版本从V3.*插件只支持Mybatis版本3.5.0以上<br/>
####
第一步：
```xml
<dependency>
    <groupId>com.github.link-kou</groupId>
    <artifactId>mybatis-plugin</artifactId>
    <version>1.0.2</version>
</dependency>
```
####
第二步：<br/>
**SpringBoot配置示例：**
```java
SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
sqlSessionFactoryBean.setPlugins(new LogInterceptor());
return sqlSessionFactoryBean.getObject();
```


### Sponsors

<table>
      <td>
        <a href="https://www.jetbrains.com/?from=TreeInfotip" target="_blank">
            <img src="https://cdn.jsdelivr.net/gh/YiiGuxing/TranslationPlugin@master/images/jetbrains.svg" alt="JetBrains" title="Development powered by JetBrains.">
        </a>
      </td>
</table>

#### <kbd>2022.08.16</kbd> -> <kbd>《IDEA 2022.2 测试通过支持使用。》★★★重大更新，从版本V3开始将彻底解决字符串替代弊端，支持所有数据库★★★</kbd>

#### <kbd>2021.06.12</kbd> -> <kbd>《IDEA 2021.3 测试通过支持使用。》</kbd>

#### <kbd>2020.08.11</kbd> -> <kbd>《IDEA 2020.2 测试通过支持使用。》</kbd>

#### <kbd>2020.08.25</kbd> ->  <kbd>重构代码所有代码,代码中完善备注信息</kbd>

#### <kbd>2020.09.19</kbd> ->  <kbd>添加对特点类型的引号支持</kbd>

#### <kbd>2020.10.07</kbd> ->  <kbd>支持2020.2.3版本</kbd>

#### <kbd>2020.11.20</kbd> ->  <kbd>改进分隔判断,由单纯通过,判断会存在无法对文本正常分隔的情况下。</kbd>

#### <kbd>2020.12.18</kbd> ->  <kbd>改进Like判断异常(解决还不够优雅,不影响正常使用)。</kbd>

#### <kbd>2020.12.19</kbd> ->  <kbd>添加可选是否格式化。</kbd>

#### <kbd>2021.01.18</kbd> ->  <kbd>支持IDEA：2020.3.1，Master代码升级到IDEA2020.3.1版本(JAVA_11版本)</kbd>

#### <kbd>2021.01.31</kbd> ->  <kbd>发布2.0包。不向下兼容IDEA:2020.3以下版本,拥抱Java11,代码将保持兼容度，如果需要2020.3以下包支持,自行编译以下。</kbd>

#### <kbd>2021.02.11</kbd> ->  <kbd>发布2.0.1包。修复兼容性问题，不兼容还是会有不少后遗症。</kbd>

#### <kbd>2021.06.15</kbd> ->  <kbd>发布2.0.5包。修复无法自定义关键字问题。</kbd>

#### <kbd>2021.09.16</kbd> ->  <kbd>发布2.0.6包。增加自动生成字面量按钮。</kbd>

#### <kbd>2021.12.15</kbd> ->  <kbd>支持IDEA 2020.3 发布2.0.7包。修复无法自定义关键字问题。</kbd>


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

2020.3 以上版本都支持
>
[plugin.intellij.assistant.mybaitslog-2020.3.X-3.0.0.zip](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/plugin/MybatisLog-3.0.2.zip)


> ##### 说明文档：

    1、插件基于日志来进行打印，如果无法打印SQL语句。排查一下日志
    2、插件默认随项目启动而启动
    3、如果不行！试试看！无敌大法，卸载插件，然后在重新安装