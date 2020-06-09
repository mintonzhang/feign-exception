package cn.minsin.feign.config;


import cn.minsin.feign.exception.BaseRemoteCallException;
import cn.minsin.feign.exception.RemoteCallException;
import feign.Contract;
import feign.Feign;
import feign.Request.Options;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这个bean默认不会生效，使用者在FeignClient中指定 FeignConfiguration
 */
@Configuration
@ConditionalOnClass({Feign.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
public class FeignConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Contract feignContract() {
        return new SpringMvcContract();
    }

    @Bean
    @ConditionalOnMissingBean
    public Options feignOptions() {
        //修改默认超时时间
        return new Options(10 * 1000, 10 * 1000);
    }

    @Bean
    @ConditionalOnMissingBean
    public Retryer feignRetry() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDecoder errorDecoder(@Autowired BaseRemoteCallException baseRemoteCallException) {
        return new FeignExceptionDecoder(baseRemoteCallException);
    }

    @Bean
    @ConditionalOnMissingBean
    public BaseRemoteCallException baseRemoteCallException() {
        //默认异常处理器
        return new RemoteCallException();
    }
}