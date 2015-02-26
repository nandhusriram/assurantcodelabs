package com.assurant.inc.transformation.endpoints;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.wordnik.swagger.annotations.Api;

@Component
@Path("/")
@Api(value = "/", description = "Index service")
public class IndexEndpoint {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAll( @Context Application application, @Context HttpServletRequest request)
    {
		
        String basePath =request.getRequestURL().toString();
 
        JSONObject root = new JSONObject();
        JSONArray resources = new JSONArray();
 
        root.put( "resources", resources );
 
        for ( Class<?> aClass : application.getClasses() )
        {
            if ( isAnnotatedResourceClass( aClass ) )
            {
                Resource resource = Resource.from(aClass);
                JSONObject resourceNode = new JSONObject();
                String uriPrefix = resource.getPath();
 
                for ( Resource srm : resource.getChildResources() )
                {
                    String uri = uriPrefix+ srm.getPath();
                    for ( ResourceMethod resourceMethod : srm.getAllMethods())
                    {
                    	 addTo( resourceNode, uri, resourceMethod, joinUri(basePath, uri) );
                    }
                   
                }
 
                for ( ResourceMethod srm : resource.getResourceMethods() )
                {
                    addTo( resourceNode, uriPrefix, srm, joinUri( basePath, uriPrefix ) );
                }
 
                resources.put( resourceNode );
            }
 
        }
 
 
        return Response.ok().entity( root.toString() ).build();
    }
 
	private String joinUri(String base, String prefix)
	{
		return base+prefix;
	}
    private void addTo( JSONObject resourceNode, String uriPrefix, ResourceMethod srm, String path )
    {
    	if(uriPrefix!=null)uriPrefix.replaceAll("/$", "");
    	if(path!=null)path.replaceAll("/$", "");
        JSONObject inner=new JSONObject();
        inner.put("path", path);
        inner.put("verbs",new JSONArray());
        resourceNode.put( uriPrefix, inner );
        ((JSONArray) ((JSONObject)resourceNode.get( uriPrefix )).get("verbs")).put( srm.getHttpMethod() );
    }
 
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isAnnotatedResourceClass( Class rc )
    {
        if ( rc.isAnnotationPresent( Path.class ) )
        {
            return true;
        }
 
        for ( Class i : rc.getInterfaces() )
        {
            if ( i.isAnnotationPresent( Path.class ) )
            {
                return true;
            }
        }
 
        return false;
    }
}
