package com.springstudy.ch03.declaration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotationAspect {

	private static final Log logger = LogFactory.getLog(AnnotationAspect.class);
	
	// 포인트컷 (point cut) - 포인트컷 지정자를 이용해 작성
	
	// 어드바이스 (Before Advice, AfterReturning Advice, After Advice, AfterThrowing Advice, Around Advice)
	
	
}
