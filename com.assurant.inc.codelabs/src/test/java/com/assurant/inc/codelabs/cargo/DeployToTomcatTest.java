package com.assurant.inc.codelabs.cargo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.cargo.container.ContainerType;
import org.codehaus.cargo.container.RemoteContainer;
import org.codehaus.cargo.container.configuration.RuntimeConfiguration;
import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployable.DeployableType;
import org.codehaus.cargo.container.deployer.Deployer;
import org.codehaus.cargo.container.deployer.DeployerType;
import org.codehaus.cargo.container.deployer.URLDeployableMonitor;
import org.codehaus.cargo.container.tomcat.TomcatRuntimeConfiguration;
import org.codehaus.cargo.generic.DefaultContainerFactory;
import org.codehaus.cargo.generic.deployable.DefaultDeployableFactory;
import org.codehaus.cargo.generic.deployer.DefaultDeployerFactory;
import org.codehaus.cargo.util.log.LogLevel;
import org.codehaus.cargo.util.log.SimpleLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeployToTomcatTest {

	
	@Before
	public void setup() throws Exception
	{

		Deployable war = new DefaultDeployableFactory().createDeployable(
				"tomcat7x", "./target/codelabs.war", DeployableType.WAR);

		RuntimeConfiguration runtimeConfig=new TomcatRuntimeConfiguration();
		runtimeConfig.setProperty("cargo.remote.username", "tomcat");
		runtimeConfig.setProperty("cargo.remote.uri", "http://localhost:8081/manager/text");
		runtimeConfig.setProperty("cargo.remote.password", "tomcat");
		runtimeConfig.setProperty("cargo.servlet.port","8081");
		RemoteContainer container = (RemoteContainer) new DefaultContainerFactory()
				.createContainer("tomcat7x", ContainerType.REMOTE,
						runtimeConfig);
		SimpleLogger simple=new SimpleLogger();
		simple.setLevel(LogLevel.DEBUG);
		container.setLogger(simple);
		container.setConfiguration(runtimeConfig);
		URLDeployableMonitor monitor = new URLDeployableMonitor(new URL(
				"http://localhost:8081/mongodbtest/test"));
		Deployer deployer = new DefaultDeployerFactory().createDeployer(
				container, DeployerType.REMOTE);
		deployer.undeploy(war);
		deployer.deploy(war, monitor);

		
	}
	
	@After
	public void cleanup()
	{
		
	}
	@Test
	public void test()  throws Exception{
		try {
			
			  final URL url = new URL("http://localhost:8081/codelabs/member");
			  final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			  urlConn.setConnectTimeout(1000 * 2); // mTimeout is in seconds
			  final long startTime = System.currentTimeMillis();
			  urlConn.connect();
			  final long endTime = System.currentTimeMillis();
			  if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			   System.out.println("Time (ms) : " + (endTime - startTime));
			   System.out.println("Ping to "+url.getPath() +" was success");
			   
			  }
			 } catch (final MalformedURLException e1) {
			  e1.printStackTrace();
			 } catch (final IOException e) {
			  e.printStackTrace();
			 }
		
	}

}
