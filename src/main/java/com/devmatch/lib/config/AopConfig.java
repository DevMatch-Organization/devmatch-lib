package com.devmatch.lib.config;

import com.devmatch.lib.annotation.log.Debug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class AopConfig {

    private final Logger log = LogManager.getLogger();

    @Around("@annotation(debug)")
    public Object debug(final ProceedingJoinPoint pjp, final Debug debug) throws Throwable {

        final String classe = pjp.getSignature().getDeclaringTypeName();
        final String metodo = pjp.getSignature().getName();

        log.debug("INICIO :: CLASSE {} :: METODO {} :: PARAMETROS {}", classe, metodo, pjp.getArgs());
        final StopWatch monitor = new StopWatch(classe);
        monitor.start(metodo);

        final Object saida = pjp.proceed();

        monitor.stop();
        log.info("FIM :: CLASSE {} :: METODO {} :: EXECUCAO {} ms", classe, metodo, monitor.getTotalTimeMillis());

        return saida;
    }


}
