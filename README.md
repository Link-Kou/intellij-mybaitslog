# [![Mybatislog](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/pluginIcon.svg)](https://github.com/Link-Kou/intellij-mybaitslog) Mybatislog

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Link-Kou/intellij-mybaitslog)
![GitHub](https://img.shields.io/github/license/Link-Kou/intellij-mybaitslog)
![GitHub issues](https://img.shields.io/github/issues/Link-Kou/intellij-mybaitslog)

### Mybatislog能做什么？

> Mybatislog是基于IntelliJ 开发的插件项目，用来格式化输出Mybatis的Sql。

![样列](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/2020-03-25_09-28-47.jpg "样列")

![样列](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/2023-04-28_08-33-52.gif "样列")


### 在线安装(搜索)

IDEA --> Setting --> Plugins --> 搜索 MyBatis Log EasyPlus

![样列](https://raw.githubusercontent.com/Link-Kou/intellij-mybaitslog/master/image/img.png "样列")


### 捐赠 | Donate

> 虽然日常时间很忙碌，也为了碎银几两而发愁，但是希望借此慰藉心中早已经开始缥缈的诗与远方。<br/>
> 欢迎Fork，欢迎继续扩展


## <p style="color:red">★★超级无敌丝滑★★</p>
> 版本V5.<b style="color:red">支持所有数据库的所有SQL都能均能正常格式化输出</b><br/>
> 版本V5.插件支持IDEA版本2020.1以上<br/>
> 版本V5.JKD8以上<br/>
> 版本V5.插件支持Mybatis版本<b>3.2.0<b/>以上，3.2.0已经是2013发布的。所以更低版本将不在支持<br/>
> 版本V5.插件对MybatisPlus等插件也进行了测试，目前大多数功能已支持<br/>

## <p style="color:red">★★已知问题★★</p>
> 不支持Gradle，正在想办法中。如有路过大神知道如何解决还请度化本项目一下。
> 不支持自定义实现SqlSource，有解决办法测试不理想，在继续想办法中。

### Sponsors

<table>
      <td>
        <a href="https://www.jetbrains.com/?from=TreeInfotip" target="_blank">
            <img src="https://cdn.jsdelivr.net/gh/YiiGuxing/TranslationPlugin@master/images/jetbrains.svg" alt="JetBrains" title="Development powered by JetBrains.">
        </a>
      </td>
</table>

#### <kbd>2023.05.28</kbd> -> <kbd>对额外的扩展插件进行支持</kbd>

#### <kbd>2023.05.22</kbd> -> <kbd>《IDEA 2021.1 以上》感谢各位社区伙伴的测试反馈，现已修复若干个已知问题</kbd>

#### <kbd>2023.05.11</kbd> -> <kbd>《IDEA 2021.1 以上》★★★优化了对mybatis-plus的支持，添加了对参数自动解析能力，现已修复若干个已知问题★★★</kbd>

#### <kbd>2023.04.19</kbd> -> <kbd>《IDEA 2021.1 以上》感谢各位社区伙伴的测试反馈，现已修复若干个已知问题</kbd>

#### <kbd>2023.04.10</kbd> -> <kbd>《IDEA 2019.3 以上》感谢各位社区伙伴的测试反馈，现已修复若干个输出格式化的问题</kbd>

#### <kbd>2023.03.30</kbd> -> <kbd>《IDEA 2019.3 以上》★★★超级重大更新，从版本V5将实现丝滑体验;多IDEA版本支持★★★</kbd>

#### <kbd>2022.12.01</kbd> -> <kbd>V4不断探索</kbd>

#### <kbd>2022.08.16</kbd> -> <kbd>★★★重大更新，从版本V3开始将彻底解决字符串替代弊端，支持所有数据库★★★</kbd>

#### <kbd>2021.06.12</kbd> -> <kbd>《IDEA 2021.3 测试通过支持使用。》</kbd>

#### <kbd>2021.01.18</kbd> ->  <kbd>支持IDEA：2020.3.1，Master代码升级到IDEA2020.3.1版本(JAVA_11版本)</kbd>

#### <kbd>2021.01.31</kbd> ->  <kbd>发布2.0包。不向下兼容IDEA:2020.3以下版本，拥抱Java11，代码将保持兼容度，如果需要2020.3以下包支持，自行编译以下。</kbd>

#### <kbd>2021.02.11</kbd> ->  <kbd>发布2.0.1包。修复兼容性问题，不兼容还是会有不少后遗症。</kbd>

#### <kbd>2021.06.15</kbd> ->  <kbd>发布2.0.5包。修复无法自定义关键字问题。</kbd>

#### <kbd>2021.09.16</kbd> ->  <kbd>发布2.0.6包。增加自动生成字面量按钮。</kbd>

#### <kbd>2021.12.15</kbd> ->  <kbd>支持IDEA 2020.3 发布2.0.7包。修复无法自定义关键字问题。</kbd>

#### <kbd>2020.08.11</kbd> -> <kbd>《IDEA 2020.2 测试通过支持使用。》</kbd>

#### <kbd>2020.08.25</kbd> ->  <kbd>重构代码所有代码，代码中完善备注信息</kbd>

#### <kbd>2020.09.19</kbd> ->  <kbd>添加对特点类型的引号支持</kbd>

#### <kbd>2020.10.07</kbd> ->  <kbd>支持2020.2.3版本</kbd>

#### <kbd>2020.11.20</kbd> ->  <kbd>改进分隔判断，由单纯通过，判断会存在无法对文本正常分隔的情况下。</kbd>

#### <kbd>2020.12.18</kbd> ->  <kbd>改进Like判断异常(解决还不够优雅,不影响正常使用)。</kbd>

#### <kbd>2020.12.19</kbd> ->  <kbd>添加可选是否格式化。</kbd>

---

### 使用环境

`IntelliJ IDEA Ultimate版（191+）`

### 源代码构建

    项目管理：Gradle



