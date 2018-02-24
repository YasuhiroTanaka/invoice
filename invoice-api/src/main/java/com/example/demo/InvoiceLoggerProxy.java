package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The Class InvoiceLoggerProxy.
 */
@Aspect
@Component
public class InvoiceLoggerProxy {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceApplication.class);
    /** method enter suffix. */
    private static final String ENTER_SUFFIX = " <";
    /** method exit suffix. */
    private static final String EXIT_SUFFIX = " >";

    /**
     * Invoke around.
     *
     * @param point the point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("execution(* com.example.demo..*(..))")
    public Object invokeAround(final ProceedingJoinPoint point) throws Throwable {
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        LOGGER.info(className + "." + methodName + ENTER_SUFFIX);
        Object object = point.proceed();
        if (object != null) {
            LOGGER.info(className + "." + methodName + ":" + object);
        }
        LOGGER.info(className + "." + methodName + EXIT_SUFFIX);
        return object;
    }
}
