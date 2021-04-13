# 杏子商城开发日记

### 后台技术选型

1.springboot版本2.4.4

2.mysql8.0.21

3.开发工具 idea Navicat Vscode

### 预先阶段：

将开发环境与线上环境的配置分离开来，使用yml格式的配置文件

application.yml 与application-dev.yml

```yaml
#spring
spring:
  profiles:
    active: dev
```

将开发环境的配置写入application-dev中，把配置分离开,最后生产环境中的配置全部存入application-prod.yml中

用application来选择使用哪个配置文件

### 第一阶段：设计用户表和完善持久层

首先新建数据库

使用Navicat新建数据库



![截图](https://github.com/pf666nb/xingzi/blob/master/src/main/resources/image/1.png)

字符集的选择为utf8mb4

[utf8和utf8mb4的选择](https://www.jianshu.com/p/6967ce16a202)，<---参考网址

[排序规则](https://blog.csdn.net/world_ding/article/details/96447413) <--参考网址

![屏幕快照 2021-04-12 下午11.18.08](https://github.com/pf666nb/xingzi/blob/master/src/main/resources/image/2.png)

XZ_User表的设计

java中的User对应对实体类设计

对应的sql为

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for XZ_User
-- ----------------------------
DROP TABLE IF EXISTS `XZ_User`;
CREATE TABLE `XZ_User` (
  `User_Id` int NOT NULL COMMENT '用户ID，用户的唯一标识符',
  `User_Name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称，用户昵称不允许空',
  `User_PassWord` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码不允许为空',
  `User_CreateTime` datetime NOT NULL COMMENT '用户的创建时间',
  `User_UpdateTime` datetime NOT NULL COMMENT '用户数据的更新时间',
  `User_IsBan` tinyint(1) DEFAULT '1' COMMENT '是否能登陆',
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
```



#### springboot整合mybatis

本项目使用mybatis作为持久层框架，导入前的pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.swjt</groupId>
    <artifactId>xingzishop</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>xingzishop</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```

导入mybatis所需要的相关依赖

#### 使用mybatis逆向工程来生成繁琐的xml文件

参考资料[SpringBoot+MyBatis实现逆向工程](https://blog.csdn.net/heqiang000/article/details/90486396)

首先导入需要的相关依赖

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->

 <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.23</version>
            <scope>runtime</scope>
        </dependency>
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.7</version>
</dependency>
<build>
        <plugins>
            <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.2</version>
            <configuration>
                <configurationFile>${basedir}/src/main/resources/generatorConfig.xml</configurationFile>
                <overwrite>true</overwrite>
                <verbose>true</verbose>
            </configuration>
        </plugin>

            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

然后在resource目录下面新增generatorConfig.xml文件

配置如下

```xml
generatorConfig.xml

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!-- 数据库驱动:选择你的本地硬盘上面的数据库驱动包-->
    <classPathEntry location="/Users/mac/.m2/repository/mysql/mysql-connector-java/8.0.23/mysql-connector-java-8.0.23.jar"/>
    <context id="DB2Tables" targetRuntime="MyBatis3">
        <!-- JavaBean 实现 序列化 接口 -->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
        <!-- 生成toString -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
        <!-- optional，旨在创建class时，对注释进行控制 -->
        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <!-- <property name="suppressAllComments" value="true"/>-->
        </commentGenerator>
        <!--数据库链接URL，用户名、密码 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/xingzishop"
                        userId="root"
                        password="12345678">
        </jdbcConnection>
        <!-- 类型转换 -->
        <javaTypeResolver >
            <!-- 是否使用bigDecimal,
                false: 把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer(默认)
                true:  把JDBC DECIMAL 和 NUMERIC 类型解析为java.math.BigDecimal
            -->
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>
        <!-- 生成模型的包名和位置-->
        <javaModelGenerator targetPackage="com.swjt.xingzishop.Bean" targetProject="src/main/java">
            <!-- 默认false 是否允许子包 -->
            <property name="enableSubPackages" value="true" />
            <!-- 默认false 是否对model添加 构造函数 -->
            <property name="constructorBased" value="false"/>
            <!-- 默认false 建立的Model对象是否 不可改变  即生成的Model对象不会有 setter方法，只有构造方法 -->
            <property name="immutable" value="false"/>
            <!-- 默认false 是否对类CHAR类型的列的数据进行trim操作 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- 生成映射文件的包名和位置-->
        <sqlMapGenerator targetPackage="Mapper" targetProject="src/main/resources">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>
        <!-- 生成DAO的包名和位置-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.swjt.xingzishop.Mapper" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>
        <!-- 要生成的表 tableName是数据库中的表名或视图名 domainObjectName是实体类名-->
        <!-- <table tableName="risk_model_order" domainObjectName="DSRiskModelOrder" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"></table>
         <table tableName="tel_bill_record" domainObjectName="DSTelBillRecord" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"></table>-->
        <table tableName="XZ_User"></table>
    </context>
</generatorConfiguration>

```

上述文件中有相对应的注释可以根据对应注释来修改，其中需要使用到本地mysql的jar包的地址

可以在以下找到对应的本地jar包地址

![屏幕快照 2021-04-13 上午10.35.55](https://github.com/pf666nb/xingzi/blob/master/src/main/resources/image/3.png)

前提是要导入了mysql-connector-java-8.0.23.jar

![屏幕快照 2021-04-13 上午10.39.28](https://github.com/pf666nb/xingzi/blob/master/src/main/resources/image/4.png)

然后执行右侧的maven插件中的generator来生成逆向的代码

生成的类中有一个实体类和一个*Example类

关于这个类具体详情

[example类的用法](https://blog.csdn.net/Nerver_77/article/details/79707190?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-2.baidujs&dist_request_id=1331302.8353.16182817925988555&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-2.baidujs)

后续要集成spring security 所以这个实体类还要修改

```java
package com.swjt.xingzishop.Bean;

import java.io.Serializable;
import java.util.Date;

public class XzUser implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_Id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_Name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    private String userPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    private Date userCreatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    private Date userUpdatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    private Boolean userIsban;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table XZ_User
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_Id
     *
     * @return the value of XZ_User.User_Id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_Id
     *
     * @param userId the value for XZ_User.User_Id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_Name
     *
     * @return the value of XZ_User.User_Name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_Name
     *
     * @param userName the value for XZ_User.User_Name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_PassWord
     *
     * @return the value of XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_PassWord
     *
     * @param userPassword the value for XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_CreateTime
     *
     * @return the value of XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    public Date getUserCreatetime() {
        return userCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_CreateTime
     *
     * @param userCreatetime the value for XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    public void setUserCreatetime(Date userCreatetime) {
        this.userCreatetime = userCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_UpdateTime
     *
     * @return the value of XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    public Date getUserUpdatetime() {
        return userUpdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_UpdateTime
     *
     * @param userUpdatetime the value for XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    public void setUserUpdatetime(Date userUpdatetime) {
        this.userUpdatetime = userUpdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_IsBan
     *
     * @return the value of XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    public Boolean getUserIsban() {
        return userIsban;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_IsBan
     *
     * @param userIsban the value for XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    public void setUserIsban(Boolean userIsban) {
        this.userIsban = userIsban;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table XZ_User
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userCreatetime=").append(userCreatetime);
        sb.append(", userUpdatetime=").append(userUpdatetime);
        sb.append(", userIsban=").append(userIsban);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
```

以上是目前为止生成的实体类,

接下来需要写测试类来测试之前的持久层代码

新增以下包的结构，



编写测试类时候遇到一个小问题，@RunWith这个注解无法找到

[RunWith无法找到](https://blog.csdn.net/y2020520/article/details/107690958?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-0&spm=1001.2101.3001.4242) <--参考资料

根据参考资料我们加入依赖

```xml
  <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
```



@RunWith便可以使用了

> 最后提示: 虽然 idea生成的测试类,只自动生成了一个@SpringBootTest注解;没有@RunWith注解;也是能够运行的;
> 但是建议:
> 标准测试类里还是要有@RunWith的，作用是告诉java你这个类通过用什么运行环境运行，例如启动和创建spring的应用上下文。否则你需要为此在启动时写一堆的环境配置代码。你在IDEA里去掉@RunWith仍然能跑是因为在IDEA里识别为一个JUNIT的运行环境，相当于就是一个自识别的RUNWITH环境配置。但在其他IDE里并没有。
>
> 所以，为了你的代码能在其他IDE里边正常跑，建议还是加@RunWith
> ————————————————
> 版权声明：本文为CSDN博主「逸佳6」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
> 原文链接：https://blog.csdn.net/y2020520/article/details/107690958

新建一个总的测试类

```java
package com.swjt.xingzishop;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {


}

```

内部不需要写方法，其他测试类只需要继承这个类就能进行测试了

现在来测试持久层

编写测试类

```java
package com.swjt.xingzishop.Service;

import com.swjt.xingzishop.Bean.XzUser;
import com.swjt.xingzishop.Mapper.XzUserMapper;
import com.swjt.xingzishop.SpringTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import javax.annotation.Resource;
import java.util.Date;

@FixMethodOrder(MethodSorters.JVM)
public class UserServiceTest extends SpringTest {
    @Resource
    private XzUserMapper mapper;
    @Test
    public void save() {
        XzUser xzUser = new XzUser();
        xzUser.setUserId("qwertyuiopasd");
        xzUser.setUserCreatetime(new Date());
        xzUser.setUserUpdatetime(new Date());
        xzUser.setUserLoginname("123456789");
        xzUser.setUserPassword("123456789");
        xzUser.setUserName("wpf");
        xzUser.setUserIsban(true);
        mapper.insert(xzUser);

    }
    @Test
    public void findUser() {
        XzUser xzUser = mapper.selectByPrimaryKey("qwertyuiopasd");
        System.out.println(xzUser.toString());

    }
    @Test
    public void updateUser() {
        XzUser xzUser = new XzUser();
        xzUser.setUserId("qwertyuiopasd");
        xzUser.setUserCreatetime(new Date());
        xzUser.setUserUpdatetime(new Date());
        xzUser.setUserLoginname("123456789");
        xzUser.setUserPassword("123456789");
        xzUser.setUserName("zhy");
        xzUser.setUserIsban(true);
        int i = mapper.updateByPrimaryKey(xzUser);
        System.out.println(i);

    }

    @Test
    public void delUser() {
        mapper.deleteByPrimaryKey("qwertyuiopasd");

    }




}

```

@FixMethodOrder(MethodSorters.JVM)这行代码是用来让方法进行排序，按照顺序执行

使得数据可以循环

>  JUnit是通过@FixMethodOrder注解(annotation)来控制测试方法的执行顺序的。
> @FixMethodOrder注解的参数是org.junit.runners.MethodSorters对象,
> 在枚举类org.junit.runners.MethodSorters中定义了如下三种顺序类型：
>
> **MethodSorters.JVM(按照JVM得到的方法顺序，代码中定义的方法顺序)**
> Leaves the test methods in the order returned by the JVM. Note that the order from the JVM may vary from run to run
>
> **MethodSorters.DEFAULT(默认的顺序)**
> Sorts the test methods in a deterministic, but not predictable, order() (以确定但不可预期的顺序执行)
>
> **MethodSorters.NAME_ASCENDING(按方法名字母顺序执行)**
> Sorts the test methods by the method name, in lexicographic order, with Method.toString() used as a tiebreaker

### springboot整合Swagger

为了方便后台调用接口进行测试，以往一般使用postman对后台接口进行测试，过程比较繁琐，有了swagger之后，后台测试会方便许多

> Swagger 为开发者提供了一套规范去定义接口和接口相关的信息，通过 springfox-swagger 依赖 jar 包可以将基于 Spring MVC 和 Spring Boot 项目的项目代码，自动生成 JSON 格式的描述文件，我们可以通过这套接口描述数据生成各种接口文档。
>
> 目前有很大一部分 Spring Boot 的开发者会将其用来构建 RESTful API，而我们构建RESTful API的目的通常都是由于多终端的原因，这些终端会共用很多底层业务逻辑，因此我们会抽象出这样一层来同时服务于多个移动端或者Web前端。这样一来，我们的RESTful API就有可能要面对多个开发人员或多个开发团队：iOS 开发、Android 开发或是Web开发等。为了减少与其他团队平时开发期间的频繁沟通成本，传统做法我们会创建一份 API 文档来记录所有接口细节，然而这样的做法有以下几个问题：
>
> - 由于接口众多，并且细节复杂（需要考虑不同的HTTP请求类型、HTTP头部信息、HTTP请求内容等），编写一份完整的 API 文档非常吃力。
> - 随着时间推移，不断修改接口实现的时候都必须同步修改接口文档，维护起来十分麻烦。
>
> 为了解决上面这样的问题，主要是简化开发人员的开发成本以及减少前后端开发人员之间的交流成本，就不得不提一下当前最流行的 API 管理工具 Swagger，你可以叫它“丝袜哥”。
>
> Swagger 的目标是为 RESTful API 定义一个标准的，与语言无关的接口，使人和计算机在看不到源码或者看不到文档或者不能通过网络流量检测的情况下能发现和理解各种服务的功能，在 Spring Boot 项目中集成 Swagger 可以使用注解来标记出需要在 API 文档中展示的信息，Swagger 会根据项目中标记的注解来生成对应的 API 文档。当然，不仅仅是 Spring Boot 项目，在其他技术栈的项目中也可以通过对应的整合方式去使用 Swagger。

##### 导入依赖

首先，在 pom.xml 中加入 Swagger 的依赖信息，pom.xml 文件更新如下：

```xml
<!-- swagger2 -->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.8.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.8.0</version>
		</dependency>
		<!-- swagger2 -->
```

##### 编写配置类

```java
package com.swjt.xingzishop.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.swjt.xingzishop.Controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger-api文档")
                .description("swagger文档 by wpf")
                .version("1.0")
                .build();
    }
}

```

然后启动项目，项目启动之后访问http://localhost:8080/swagger-ui.html#/

### springboot整合spring security

本项目使用的安全框架是spring security，他能帮助我们校验用户提高安全型，还能对资源访问做出限制

##### 导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

spring security 中的用户实体类必须实现UserDetails 

```java
package com.swjt.xingzishop.Bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class XzUser implements Serializable, UserDetails {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_Id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_Name
     *
     * @mbggenerated
     */
    private String userName;



    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    private String userPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    private Date userCreatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    private Date userUpdatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    private Boolean userIsban;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_LoginName
     *
     * @mbggenerated
     */
    private String userLoginname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table XZ_User
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    public XzUser() {
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_Id
     *
     * @return the value of XZ_User.User_Id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_Id
     *
     * @param userId the value for XZ_User.User_Id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_Name
     *
     * @return the value of XZ_User.User_Name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_Name
     *
     * @param userName the value for XZ_User.User_Name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_PassWord
     *
     * @return the value of XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_PassWord
     *
     * @param userPassword the value for XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_CreateTime
     *
     * @return the value of XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    public Date getUserCreatetime() {
        return userCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_CreateTime
     *
     * @param userCreatetime the value for XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    public void setUserCreatetime(Date userCreatetime) {
        this.userCreatetime = userCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_UpdateTime
     *
     * @return the value of XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    public Date getUserUpdatetime() {
        return userUpdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_UpdateTime
     *
     * @param userUpdatetime the value for XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    public void setUserUpdatetime(Date userUpdatetime) {
        this.userUpdatetime = userUpdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_IsBan
     *
     * @return the value of XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    public Boolean getUserIsban() {
        return userIsban;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_IsBan
     *
     * @param userIsban the value for XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    public void setUserIsban(Boolean userIsban) {
        this.userIsban = userIsban;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_LoginName
     *
     * @return the value of XZ_User.User_LoginName
     *
     * @mbggenerated
     */
    public String getUserLoginname() {
        return userLoginname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_LoginName
     *
     * @param userLoginname the value for XZ_User.User_LoginName
     *
     * @mbggenerated
     */
    public void setUserLoginname(String userLoginname) {
        this.userLoginname = userLoginname == null ? null : userLoginname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table XZ_User
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userCreatetime=").append(userCreatetime);
        sb.append(", userUpdatetime=").append(userUpdatetime);
        sb.append(", userIsban=").append(userIsban);
        sb.append(", userLoginname=").append(userLoginname);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    
    private List<GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }



    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userLoginname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userIsban;
    }
}

```

以上是实现完之后的配置类

然后编写springSecurity的配置类

```java
package com.simplestudio.fugitive.config;

import com.simplestudio.fugitive.sercurity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //未认证处理器
    @Autowired
    private JSONAuthenticationPoint jsonAuthenticationPoint;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> MyWebAuthenticationDetailsSource;

    //未授权处理器
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    //登录错误处理器
    @Autowired
    private AuthFailHandler authFailHandler;

    //用户注销处理器
    @Autowired
    private MyLogoutSuccessHandler logoutSuccessHandler;

    //用户登录成功处理器
    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    //未认证处理器
    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/user/api/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/admin/api/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jsonAuthenticationPoint)
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .authenticationDetailsSource(MyWebAuthenticationDetailsSource)
                .successHandler(authSuccessHandler)
                .failureHandler(authFailHandler)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();

    }

    private CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        String encode = bCryptPasswordEncoder.encode("12345678");
        System.out.println(encode);
    }
}

```

