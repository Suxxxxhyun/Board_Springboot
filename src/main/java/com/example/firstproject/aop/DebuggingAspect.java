package com.example.firstproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
//@Aspect는 AOP클래스 선언을 알리는 어노테이션으로, AOP클래스란 부가 기능을 주입하는 클래스를 의미한다.
@Component
//@Component는 IOC컨테이너가 해당 객체를 생성 및 관리
@Slf4j
//@Slf4j는 log를 위한 어노테이션
public class DebuggingAspect {

    //cut() - 어느 지점에 메소드를 주입할 것이냐를 위한 메소드
    @Pointcut("execution(* com.example.firstproject.service.CommentService.*(..))")
    //@Pointcut - 어느 대상을 타겟으로 해서 부가기능을 주입할것인지 (주입 대상을 지정하는 어노테이션)
    //타겟 - CommentService#create(), create(..)에서 ..은 파라미터가 어느것이든 상관없다의 의미임.
    // '* com'에서 *은 public과 return타입을 지정해주는 곳.
    private void cut(){}

    @Before("cut()")
    //@Before는 실행 시점을 지정해주는 어노테이션!으로
    // 파라미터에 cut()을 넣어 cut()의 대상인 CommentService#create()가 실행되기 이전에 아래코드(loggingArgs)를 수행하겠다는 의미임!
    public void loggingArgs(JoinPoint joinPoint){ //파라미터에 기본적으로 JoinPoint joinPoint를 넣어주는데, JoinPoint joinPoint는 cut()의 대상메소드를 의미함.
        // 1. 입력값 가져오기
        Object[] args = joinPoint.getArgs(); //대상 메소드 부근에서 아규먼트를 가져오는데, Object의 배열을 리턴

        // 2. 클래스명
        String className = joinPoint.getTarget() //대상 메소드 부근에서 타겟을 지정
                .getClass()
                .getSimpleName(); //그 타켓 클래스의 이름을 지정

        // 3. 메소드명
        String methodName = joinPoint.getSignature()
                .getName();

        // 4. 입력값 로깅하기
        // >> CommentService#create()의 입력값 => 5
        // >> CommentService#create()의 입력값 => CommentDto(id=null, ...) 와 같이 출력해줄 것임!
        for(Object obj : args){
            log.info("{}#{}의 입력값 => {}", className, methodName, obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    //@AfterReturning - cut()에 지정된 대상 호출이 성공적으로 진행된 후에 아래코드를 수행하겠다!
    public void loggingReturnValue(JoinPoint joinPoint, //JoinPoint는 cut()의 대상메소드를 의미함.
                                   Object returnObj){ //returnObj는 리턴값을 의미함. cut()의 대상메소드를 수행한 후에 반환값을 returnObj에서 받을 수 있음.
                                                        //단, @AfterReturning의 파라미터의 returning과 매칭시켜야함.

        // 1. 클래스명
        String className = joinPoint.getTarget() //대상 메소드 부근에서 타겟을 지정
                .getClass()
                .getSimpleName(); //그 타켓 클래스의 이름을 지정

        // 2. 메소드명
        String methodName = joinPoint.getSignature()
                .getName();

        // 3. 반환값 로깅
        // CommentService#create()의 반환값 => CommentDto(id=10, ...)과 같이 출력해주고 싶음
        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj);
    }
}
