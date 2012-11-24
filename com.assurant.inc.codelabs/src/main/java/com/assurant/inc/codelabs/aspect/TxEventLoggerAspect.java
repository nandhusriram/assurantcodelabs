package com.assurant.inc.codelabs.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assurant.inc.codelabs.domain.InstrumentationObject;
import com.assurant.inc.codelabs.mongo.dao.TransactionEventLoggerDao;

@Aspect
@Component
public class TxEventLoggerAspect {

	private @Autowired TransactionEventLoggerDao eventDao;
	public static final ThreadLocal<InstrumentationObject.InstrumentationObjectBuilder> txEventThreadLocal = new ThreadLocal<InstrumentationObject.InstrumentationObjectBuilder>();
	private static final String FAILED="0";
	private static final String SUCCESS="1";
	
	@Before("execution(* com.assurant.inc.codelabs.jersey.rs.*.*(..))")
	public void logBefore(JoinPoint joinPoint) throws Throwable {
		String serviceName=new StringBuilder().append(joinPoint.getTarget().getClass().getSimpleName()).append(":").append(joinPoint.getSignature().getName()).toString();
		String startTS=new DateTime().toString();
		InstrumentationObject.InstrumentationObjectBuilder builder=InstrumentationObject.InstrumentationObjectBuilder
				.service(serviceName).app("MIDAAS").system("IMM")
				.start(startTS).user("NS48235");
		txEventThreadLocal.set(builder);
	}
	

	@After("execution(* com.assurant.inc.codelabs.jersey.rs.*.*(..))")
	public void logAfter(JoinPoint joinPoint) throws Throwable {
		DateTime endTS=new DateTime();
		String difference=String.valueOf(endTS.getMillis()-new DateTime(txEventThreadLocal.get().getStartTime()).getMillis());
		eventDao.insert(txEventThreadLocal.get().end(endTS.toString()).total(difference+"(TimeInMillis)").result(SUCCESS).build(),"TxEvents");
		txEventThreadLocal.remove();
	}

	@AfterThrowing(pointcut = "execution(* com.assurant.inc.codelabs.jersey.rs.*.*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		String endTS=new DateTime().toString();
		eventDao.insert(txEventThreadLocal.get().end(endTS).result(FAILED).build(),"TxEvents");
		txEventThreadLocal.remove();
	}
}
