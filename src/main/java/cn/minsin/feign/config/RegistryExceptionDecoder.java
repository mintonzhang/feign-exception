package cn.minsin.feign.config;

import cn.minsin.feign.annotation.EnableFeignExceptionHandler;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 18:38
 */
@Slf4j
public class RegistryExceptionDecoder implements ImportBeanDefinitionRegistrar {

    @SneakyThrows
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableFeignExceptionHandler.class.getName()));
        Class<? extends ErrorDecoder> decoderClass = annotationAttributes.getClass("decoderClass");

        ErrorDecoder errorDecoder = BeanUtils.instantiateClass(decoderClass);

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(ErrorDecoder.class, () -> errorDecoder)
                .setAutowireMode(AUTOWIRE_BY_TYPE)
                .getBeanDefinition();
        registry.registerBeanDefinition(beanDefinition.getBeanClassName(), beanDefinition);
        log.debug("The '{}' autowire succeed", decoderClass);
    }


}
