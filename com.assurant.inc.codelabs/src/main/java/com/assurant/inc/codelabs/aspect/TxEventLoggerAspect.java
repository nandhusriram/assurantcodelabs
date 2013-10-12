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
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;

@Aspect
@Component
public class TxEventLoggerAspect {

	private @Autowired TransactionEventLoggerDao eventDao;
	public static final ThreadLocal<InstrumentationObject.InstrumentationObjectBuilder> txEventThreadLocal = new ThreadLocal<InstrumentationObject.InstrumentationObjectBuilder>();
	private static final String FAILED="0";
	private static final String SUCCESS="1";
	
	@Before("execution(* com.assurant.inc.codelabs.jersey.rs.*.*(..))")
	public void logBefore(JoinPoint joinPoint) throws Throwable {
		String serviceName;
        serviceName = new StringBuilder().append(joinPoint.getTarget().getClass().getSimpleName()).append(":").append(joinPoint.getSignature().getName()).toString();
        String startTS=new DateTime().toString();
		Joiner joiner = Joiner.on(":").skipNulls();
		InstrumentationObject.InstrumentationObjectBuilder builder=InstrumentationObject.InstrumentationObjectBuilder
				.service(serviceName).app("TEST").system("TST")
				.start(startTS).user("NANDHU").arg(joiner.join(joinPoint.getArgs()));
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
		String exception=Throwables.getStackTraceAsString(error);
		eventDao.insert(txEventThreadLocal.get().end(endTS).result(FAILED).err(exception).build(),"TxEvents");
		txEventThreadLocal.remove();
	}
}
