package com.example.firstproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PeformanceAspect {

    @Pointcut("@annotation(com.example.firstproject.annotation.RunningTime)")
    //위를 통해, 특정 어노테이션을 대상 지정함.
    private void enableRunningTime() {}

    @Pointcut("execution(* com.example.firstproject..*.*(..))")
    //위를 통해, 기본패키지의 모든 메소드를 지정함.
    private void cut(){}

    @Around("cut() && enableRunningTime()")
    //위를 통해 cut()과 enableRunningTime()에 동시 적용함.
    //즉, cut()에 지정된 것 + enableRunningTime()에 지정된 RunningTime어노테이션을 가지고 있는 것을 아래코드로 수행하겠다!
    //@Around - @Around실행시점을 설정하는데, 파라미터를 통해 두 조건을 모두 만족하는 대상에 대해,
    // 전후로 부가 기능을 삽입하여 실행함.
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable { //@Around를 할때는 joinpoint가 ProceedingJoinPoint로 해야함.

        // 1. 메소드명
        String methodName = joinPoint.getSignature()
                .getName();

        // 2. 메소드 수행 전, 측정을 시작함.
        StopWatch stopWatch = new StopWatch(); //StopWatch - 시간측정을 위해 스프링에서 제공하는 객체
        stopWatch.start();

        // 3. 메소드를 수행
        Object returningObj = joinPoint.proceed();

        // 4. 메소드를 수행 후, 측정 종료 및 로깅을 함.

        stopWatch.stop();
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());
    }

}
