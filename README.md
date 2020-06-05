# feign-exception 使用说明

##### 前言

*在springcloud中 服务与服务之间,通常使用feign进行服务调用。但是在fei中，默认返回feign包装后的异常。eg:如果服务a调用服务b，当服务b发生异常时，如果什么都不处理的话，将抛出feign自带的异常，**并不会携带服务b本身抛出的异常**,本组件就是为了解决这个问题。*



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



### 2.优势

##### 	1.体积小

​		jar包没有集成任何额外第三方jar，纯spring相关组件。

##### 	2.使用简单，注解启动

​		只需要一个注解即可开启，如不加注解,不会自动配置任何启动项。

##### 	3.重写printStackTrace, 链路清晰,排查异常清晰(栈信息是倒序打印出来的)

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

### 4.快速上手

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

### 5.核心功能及拓展
 - 默认异常处理器
 ```java
@Slf4j
public class FeignExceptionHandler extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        Throwable error = super.getError(webRequest);
        List<ExceptionChain> exceptionChains = null;
        if (error instanceof RemoteCallException) {
            exceptionChains = ((RemoteCallException) error).getExceptionChains();
        } else {
            Object attribute = webRequest.getAttribute(ExceptionConstant.EXCEPTION_CHAIN_KEY, RequestAttributes.SCOPE_REQUEST);
            if (attribute != null) {
                exceptionChains = JSON.parseArray(attribute.toString(), ExceptionChain.class);
            }
            if (exceptionChains == null) {
                exceptionChains = new ArrayList<>(1);
            }
        }

        ExceptionChain exceptionChain = new ExceptionChain();
        exceptionChain.setMessage(error.getMessage());
        exceptionChain.setPath(errorAttributes.get("path").toString());
        exceptionChain.setTimestamp(new Date());
        exceptionChain.setApplicationName(FeignExceptionHandlerContext.getApplicationName());
        //添加发生的异常类信息 以便下一步处理
        if (error.getClass() != null) {
            exceptionChain.setExceptionClass(error.getClass().getTypeName());
        }
        exceptionChains.add(exceptionChain);
        errorAttributes.put(ExceptionConstant.EXCEPTION_CHAIN_KEY, exceptionChains);
        return errorAttributes;
    }
}

```
作用:当其他feign客户端在调用接口时,如果本服务发送异常,应该怎么返回异常信息？可以理解为异常处理器抛出=>异常解析器。需要实现ErrorAttributes
 - 默认异常解析器
```java
//FeignExceptionDecoder    
@Slf4j
public class FeignExceptionDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            Reader reader = response.body().asReader();
            String body = Util.toString(reader);
            ExceptionModel exceptionModel = JSON.parseObject(body, ExceptionModel.class);
            return new RemoteCallException(exceptionModel.getMessage(), exceptionModel.getExceptionChain());
        } catch (Exception e) {
            log.error("{} has an unknown exception.", methodKey, e);
            return new RemoteCallException("unKnowException", e);
        }

    }
}
```
作用：异常解析器,需要实现ErrorDecoder。作用是,当feign服务调用其他服务出现异常,收到的异常数据流处理类.

 - 自定义异常处理器及解析器 
 异常处理器需要实现ErrorAttributes、异常解析器需要实现ErrorDecoder .然后使用@EnableFeignExceptionHandler,代码如下
 
```java
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableFeignExceptionHandler(decoderClass = DecodeCoderClass.class,handlerClass =handlerClass.class )
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
```
要点: 无论是decoderClass 还是handlerClass 都需要无参构造方法。 因为EnableFeignExceptionHandler注册bean是需要初始化一个对象
如果,确实需要注入bean或者属性, 需要实现BeanFactoryPostProcessor 来获取BeanFactory 这样就可以获取到bean了。
但是,一般来说,处理器和解析器是不需要bean的.


### 5.静态spring常量容器

```java
public final class FeignExceptionHandlerContext {

    
    private static Environment ENVIRONMENT;


    public static String getApplicationName() {
        return ENVIRONMENT.getProperty("spring.application.name");
    }


    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }

    public static void setEnvironment(Environment environment) {
        if (ENVIRONMENT != null) {
            FeignExceptionHandlerContext.ENVIRONMENT = environment;
        }
    }
}
```
说明:这个类,在注入时会将当前环境存放进去,但是只能赋值一次。通过Environment 可以获取到yaml或properties中的配置,默认提供获取application name的方法。


### 6.CA条件断言 (condition assert)
```
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
[下载之后在本地运行测试](https://github.com/mintonzhang/feign-exception/tree/master/demo)。




