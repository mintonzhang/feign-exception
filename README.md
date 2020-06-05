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
#### 输出格式: 
**[status]:[applicationName] timestamp:'timestamp',exceptionClass:'exception',message:'message',path: 'url'**
- status 状态 有HAPPEN、THROW、END三种 分别代表每个服务是怎么处理异常的
- applicationName 发生异常的application-name
- timestamp 出现异常的时间 格式yyyy-MM-dd HH:mm:ss.SSS
- exception 出现的异常全称
- message 异常返回的message
- path feign请求的url applicationName/url

** 注意:错误栈信息是以发生时间升序排列，也就是最开始发生的异常在最上面。**

### 3.代码说明

```java



```



### 4.demo





