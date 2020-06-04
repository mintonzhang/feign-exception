# feign-exception 使用说明

##### 前言

*在springcloud中 服务与服务之间,通常使用feign进行服务调用。但是在fei中，默认返回feign包装后的异常。eg:如果服务a调用服务b，当服务b发生异常时，如果什么都不处理的话，将抛出feign自带的异常，**并不会携带服务b本身抛出的异常**,本组件就是为了解决这个问题。*



### 1.版本

```xml
maven用户

<!-- https://mvnrepository.com/artifact/cn.minsin/mutils-core -->
<dependency>
    <groupId>cn.minsin.feign</groupId>
    <artifactId>feign-exception</artifactId>
    <version>${last-release}</version>
</dependency>


```



### 2.优势

##### 	1.体积小

​		jar包没有集成任何额外第三方jar，纯spring相关组件。

##### 	2.使用简单，注解启动

​		只需要一个注解即可开启，如不加注解,不会自动配置任何启动项。

​	3.

### 3.代码说明

```java



```



### 4.demo





