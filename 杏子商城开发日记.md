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



![截图](https://github.com/pf666nb/xingzi/blob/master/src/main/resources/image/1

.png)

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

以上是目前为止生成的实体类

