package com.assurant.inc.maven.customrules;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.enforcer.rule.api.EnforcerLevel;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRule2;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.rtinfo.RuntimeInformation;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;



public class SwaggerEnforcer implements EnforcerRule2 {

	   /**
     * Simple param. This rule will fail if the value is true.
     */
    private String level = null;
	
	
	public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException 
	{
		    final Log log = helper.getLog();
	        try
	        {
	            // get the various expressions out of the helper.
	            MavenProject project = (MavenProject) helper.evaluate( "${project}" );
	            MavenSession session = (MavenSession) helper.evaluate( "${session}" );
	            String target = (String) helper.evaluate( "${project.build.directory}" );
	            String artifactId = (String) helper.evaluate( "${project.artifactId}" );

	            // retrieve any component out of the session directly
	            ArtifactResolver resolver = (ArtifactResolver) helper.getComponent( ArtifactResolver.class );
	            RuntimeInformation rti = (RuntimeInformation) helper.getComponent( RuntimeInformation.class );

	            log.info( "Retrieved Target Folder: " + target );
	            log.info( "Retrieved ArtifactId: " +artifactId );
	            log.info( "Retrieved Project: " + project );
	            log.info( "Retrieved RuntimeInfo: " + rti );
	            log.info( "Retrieved Session: " + session );
	            log.info( "Retrieved Resolver: " + resolver );
	            String workingDir = System.getProperty("user.dir");
	     	    java.nio.file.Path swaggerPath=Paths.get(workingDir+File.separatorChar+"target"+File.separatorChar+ "swagger.txt");

				try 
				{
					List<String> filecontents = java.nio.file.Files.readAllLines(swaggerPath, StandardCharsets.UTF_8);
					String[] strarray = filecontents.toArray(new String[0]);				
					if ( filecontents.size()>1 && (EnforcerLevel.valueOf(level).compareTo(EnforcerLevel.WARN)==0))
		            {
		            	log.warn(Arrays.toString(strarray) ,  new EnforcerRuleException( "None or some of the endpoints have the required swagger documentation." ));
		            }
		            if ( filecontents.size()>1 && (EnforcerLevel.valueOf(level).compareTo(EnforcerLevel.ERROR)==0) )
		            {
		            	log.error(Arrays.toString(strarray) ,  new EnforcerRuleException( "None ot some of the endpoints have the required swagger documentation." ));
		                throw new EnforcerRuleException( "None or some of the endpoints have swagger documentation." );
		            }
					
					
				} catch (IOException e)
				{
					e.printStackTrace();
				}

	           
	           
	        }
	        catch ( ComponentLookupException e )
	        {
	            throw new EnforcerRuleException( "Unable to lookup a component " + e.getLocalizedMessage(), e );
	        }
	        catch ( ExpressionEvaluationException e )
	        {
	            throw new EnforcerRuleException( "Unable to lookup an expression " + e.getLocalizedMessage(), e );
	        }
		
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getCacheId() 
	{
		
		return null;
	}

	
	public boolean isCacheable() {
		
		return false;
	}


	public boolean isResultValid(EnforcerRule helper) {
		
		return false;
	}


	public EnforcerLevel getLevel() 
	{
		return EnforcerLevel.valueOf(level);
	}

}
