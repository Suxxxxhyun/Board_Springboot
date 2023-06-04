package com.example.firstproject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
//@Target({ElementType.TYPE, ElementType.METHOD})를 통해
//어노테이션 적용 대상을 지정
@Retention(RetentionPolicy.RUNTIME)
//어노페이션 유지기간을 RUNTIME시간동안 유지하겠다는 의미임!
public @interface RunningTime {
}
