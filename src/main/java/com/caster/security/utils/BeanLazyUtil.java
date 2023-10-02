package com.caster.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BeanLazyUtil {

    public static <S, T> T beanCopy(S source, T target, String... ignoreProperties) {

//        log.info("BeanLazyUtils.source:{}", JsonUtil.toJsonLog(source));
//        log.info("BeanLazyUtils.target:{}", JsonUtil.toJsonLog(source));

        Set<String> toIgnoreSet = getIgnoredStrings(source, target, ignoreProperties);
        BeanUtils.copyProperties(source, target, toIgnoreSet.toArray(new String[0]));
        return target;
    }

    public static Set<String> getEqualsPropertyNames(Object source, Object target) {
        final BeanWrapper sourceWrappedSource = new BeanWrapperImpl(source);
        final BeanWrapper targetWrappedSource = new BeanWrapperImpl(target);

        return Stream.of(targetWrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName ->
                        sourceWrappedSource.isReadableProperty(propertyName)
                                &&
                                sourceWrappedSource.isWritableProperty(propertyName)
                                &&
                                targetWrappedSource.isReadableProperty(propertyName)
                                &&
                                targetWrappedSource.isWritableProperty(propertyName)
                                &&
                                Objects.equals(sourceWrappedSource.getPropertyValue(propertyName),
                                        targetWrappedSource.getPropertyValue(propertyName))
                )
                .collect(Collectors.toSet());
    }

    private static <S, T> Set<String> getIgnoredStrings(S source, T target, String[] ignoreProperties) {
        Set<String> toIgnoreSet = getEqualsPropertyNames(source, target);
        Collections.addAll(toIgnoreSet, ignoreProperties);
//        log.info("BeanLazyUtils to ignore properties:{}", toIgnoreSet);
        return toIgnoreSet;
    }
}
