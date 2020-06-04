package cn.minsin.feign.config;

import cn.minsin.feign.annotation.EnableFeignExceptionHandler;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 18:38
 */
@Slf4j
public class RegistryFeignExceptionHandler implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    @SneakyThrows
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableFeignExceptionHandler.class.getName()));
        Class<? extends ErrorDecoder> decoderClass = annotationAttributes.getClass("decoderClass");
        ErrorDecoder errorDecoder = BeanUtils.instantiateClass(decoderClass);

        AbstractBeanDefinition decoder = BeanDefinitionBuilder
                .genericBeanDefinition(ErrorDecoder.class, () -> errorDecoder)
                .setAutowireMode(AUTOWIRE_BY_TYPE)
                .getBeanDefinition();
        registry.registerBeanDefinition(decoder.getBeanClassName(), decoder);

        Class<? extends ErrorAttributes> handlerClass = annotationAttributes.getClass("handlerClass");

        ErrorAttributes errorAttributes = BeanUtils.instantiateClass(handlerClass);

        AbstractBeanDefinition handler = BeanDefinitionBuilder
                .genericBeanDefinition(ErrorAttributes.class, () -> errorAttributes)
                .setAutowireMode(AUTOWIRE_BY_TYPE)
                .getBeanDefinition();
        registry.registerBeanDefinition(handler.getBeanClassName(), handler);
    }


    @Override
    public void setEnvironment(Environment environment) {
        //get the application name of project
        String property = environment.getProperty("spring.application.name");
        FeignExceptionHandlerContext.setApplicationName(property);
    }
}
