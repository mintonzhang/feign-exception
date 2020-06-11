[TOC]
# feign-exception 使用说明

##### 前言

*在springcloud中 服务与服务之间,通常使用feign进行服务调用。但是在feign中，默认返回feign包装后的异常。eg:如果服务a调用服务b，当服务b发生异常时，如果什么都不处理的话，将抛出feign自带的异常，**并不会携带服务b本身抛出的异常**,本组件就是为了解决这个问题。*


### [查看版本RELEASE日志](https://github.com/mintonzhang/feign-exception/releases)

### 1.版本

```xml
maven用户

<!-- https://mvnrepository.com/artifact/cn.minsin/feign-exception -->
<dependency>
    <groupId>cn.minsin.feign</groupId>
    <artifactId>feign-exception</artifactId>
    <version>${last-release}</version>
</dependency>

```
#### 注意：Feign是springCloud服务间互相调用的框架,是在http的基础上,所以对应的项目里面必须加入以下依赖或其核心依赖
```xml
    <dependencies>
        <dependency>
              <!--表示服务是支持feign服务,代表注解 @FeignClient,@EnableFeignClients等-->
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <!--表示服务是一个web服务,代表注解 @RequestMapping,@RestController等-->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

```
注意:如果没有以上注解或核心注解,已知报错信息如下(其原因是缺少spring-boot-starter-web)：
```text
java.lang.annotation.AnnotationFormatError: Invalid default: public abstract java.lang.Class cn.minsin.feign.annotation.EnableFeignExceptionHandler.handlerClass()
	at java.lang.reflect.Method.getDefaultValue(Method.java:612) ~[na:1.8.0_191]
	at sun.reflect.annotation.AnnotationType.<init>(AnnotationType.java:132) ~[na:1.8.0_191]
	at sun.reflect.annotation.AnnotationType.getInstance(AnnotationType.java:85) ~[na:1.8.0_191]
	at sun.reflect.annotation.AnnotationParser.parseAnnotation2(AnnotationParser.java:266) ~[na:1.8.0_191]
    省略.....
```



### 2.优势

##### 	2.1.体积小

​		jar包没有集成任何额外第三方jar，纯spring相关组件。

##### 	2.2.使用简单，注解启动

​		只需要一个注解即可开启，如不加注解,不会自动配置任何启动项。

##### 	2.3.重写printStackTrace, 链路清晰,排查异常清晰(栈信息是倒序打印出来的)

```text
Slf4j打印到控制台:

cn.minsin.feign.exception.RemoteCallException: 模拟错误
	at [HAPPEN]:[provider] timestamp:'2020-06-05 17:18:56.103',exceptionClass:'java.lang.RuntimeException',message:'模拟错误',path: '/data2'.(:0) ~[na:na]
	at [THROW]:[consumer] timestamp:'2020-06-05 17:18:58.121',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/cdata2'.(:0) ~[na:na]
	at [THROW]:[provider] timestamp:'2020-06-05 17:18:58.219',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/data1'.(:0) ~[na:na]
	at [END]:[consumer] timestamp:'2020-06-05 17:18:58.222',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/cdata1'.(:0) ~[na:na]

slf4j打印到日志:
cn.minsin.feign.exception.RemoteCallException: 模拟错误
	at [HAPPEN]:[provider] timestamp:'2020-06-05 17:18:56.103',exceptionClass:'java.lang.RuntimeException',message:'模拟错误',path: '/data2'.(:0)
	at [THROW]:[consumer] timestamp:'2020-06-05 17:18:58.121',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/cdata2'.(:0)
	at [THROW]:[provider] timestamp:'2020-06-05 17:18:58.219',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/data1'.(:0)
	at [END]:[consumer] timestamp:'2020-06-05 17:18:58.222',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/cdata1'.(:0)


直接使用异常.printStackTrace 打印到控制台
cn.minsin.feign.exception.RemoteCallException : 模拟错误
	[HAPPEN]:[provider] timestamp:'2020-06-05 17:25:53.933',exceptionClass:'java.lang.RuntimeException',message:'模拟错误',path: '/data2'.(:0)
	[THROW]:[consumer] timestamp:'2020-06-05 17:25:54.025',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/cdata2'.(:0)
	[THROW]:[provider] timestamp:'2020-06-05 17:25:54.071',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/data1'.(:0)
	[END]:[consumer] timestamp:'2020-06-05 17:25:54.072',exceptionClass:'cn.minsin.feign.exception.RemoteCallException',message:'模拟错误',path: '/cdata1'.(:0)
```
######  输出格式: 
**格式==>[status]:[applicationName] timestamp:'timestamp',exceptionClass:'exception',message:'message',path: 'url'**

- status 状态 有HAPPEN、THROW、END三种。流程 HAPPEN==>THROW==>END 当多个服务调用时 THROW会出现多个 
- applicationName 发生异常的application-name
- timestamp 出现异常的时间 格式yyyy-MM-dd HH:mm:ss.SSS
- exception 出现的异常全称
- message 异常返回的message
- path feign请求的url 

**注意:错误栈信息是以发生时间升序排列，也就是最开始发生的异常在最上面。**

### 3.快速上手

在启动类上加上@EnableFeignExceptionHandler注解即可开启

```java
@EnableDiscoveryClient
@SpringBootApplication
//只需要开启此注解
@EnableFeignExceptionHandler
//
@EnableFeignClients
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}

```


### 4.静态spring常量容器

```java
public final class FeignExceptionHandlerContext {

    private static Environment ENVIRONMENT;

    public static String getApplicationName() {
        return ENVIRONMENT == null ? "unknownServer" : ENVIRONMENT.getProperty("spring.application.name");
    }
    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }
    public static void setEnvironment(Environment environment) {
        if (ENVIRONMENT == null) {
            FeignExceptionHandlerContext.ENVIRONMENT = environment;
        }
    }
}
```
说明:这个类,在注入时会将当前环境存放进去,但是只能赋值一次。通过Environment 可以获取到yaml或properties中的配置,默认提供获取application name的方法。


### 5.CA条件断言 (condition assert)
```text
//DEMO
public static void main(String[] args) {
        String s =null;
        CA.isNull(s).withRuntimeException("测试异常");
    }

console log:

Exception in thread "main" java.lang.RuntimeException: 测试异常
	at cn.minsin.feign.assert_.Then.withRuntimeException(Then.java:81)
	at cn.minsin.feign.default_.FeignExceptionDecoder.main(FeignExceptionDecoder.java:40)
```
说明：条件断言 默认的判断,默认只有在判断条件为true才会执行. 可以通过with方法进行手动判断. 这样一来断言的灵活性大大增强。
这是从[mutils-core](https://github.com/mintonzhang/mutils-spring-boot-starter/tree/master/mutils-dependencies/mutils-core-manage)中移植过来的。


### 7.DEMO
[下载之后在本地运行测试](https://github.com/mintonzhang/feign-exception-demo)。




