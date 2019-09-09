# TreeInfotip

### TreeInfotip能做什么？

> TreeInfotip是基于IntelliJ 开发的项目目录自定义备注显示，主要通过自定义XML来生成项目目录树备注。
---
### 使用环境
`IntelliJ IDEA Ultimate版（172+）`

`WebStrom（172+）`


### 一、示列
> ##### 图片示列教程：
> ![样列](https://raw.githubusercontent.com/Link-Kou/intellij-treeInfotip/master/2019-09-09_15-02-56.png "样列")
> ![样列](https://raw.githubusercontent.com/Link-Kou/intellij-treeInfotip/master/2019-09-09_15-01-56.png "样列")


> ##### 说明文档：
1. 在项目根目录下创建Directory.xml文件(文件名称不能改变)

2. 文件内容示列
```xml：
  <?xml version="1.0" encoding="UTF-8"?>
  <trees properties="" code="" codeval="" msg="">
      <model path="/src" title="视图" tooltip="" properties="" code="" codeval="" msg="">
          <tree path="/main">
              <tree path="/java/com">
                  <tree path="/plugins" title="插件">
                      <tree path="/infotip" title="模块名称-信息显示">
                          <tree path="/parsing" title="读取"/>
                      </tree>
                  </tree>
              </tree>
              <tree path="/resources" title="资源文件夹"/>
          </tree>
      </model>
  </trees>
```

3. 标签说明
```标签说明文档：
         <trees/> 默认即可，所有子标签都在此标签里面
         <model/> 模块（Maven多模块可以采用此标签作为标识）
         <tree/> 文件夹说明
```

4. 属性说明
```属性说明文档：
         <path/> 默认即可，所有子标签都在此标签里面
         <title/> 模块（Maven多模块可以采用此标签作为标识）
```