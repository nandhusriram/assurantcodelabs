package com.assurant.inc.maven.customrules;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.ws.rs.Path;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@SupportedAnnotationTypes({"javax.ws.rs.Path","com.wordnik.swagger.annotations.Api"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class SwaggerAPIProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,RoundEnvironment roundEnv)
	{
	   Set<? extends Element> classElements = roundEnv.getElementsAnnotatedWith(Path.class);
	   String workingDir = System.getProperty("user.dir");
	   java.nio.file.Path target=Paths.get(workingDir+File.separatorChar+"target"+File.separatorChar+ "swagger.txt");
	   StringBuilder builder = new StringBuilder("The following endpoints have no swagger documentation");
	   for (Element element : classElements) 
	   {
		   if (ElementKind.METHOD.equals(element.getKind())) 
		   {
			   continue;
		   }
		   String classname=((TypeElement)element).getQualifiedName().toString();
		   if(classname!=null)
		   {
			  
			   Annotation annotation = element.getAnnotation(Api.class);
			   if(annotation==null) 
			   {
				   builder.append("\r\n");
				   builder.append(classname);
				   try 
				   {
					   java.nio.file.Path file = Files.createFile(target);
					   Files.write(file, builder.toString().getBytes(), StandardOpenOption.APPEND);
				   } catch (IOException e) 
				   {
					
					 e.printStackTrace();
				   }
			   }
		    }
		   /*try {
			getMethodsAnnotatedWithApiOperation(Class.forName(classname, false, this.getClass().getClassLoader()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	   }
	   return false;
	   
	}
	
	@SuppressWarnings("unused")
	private static List<Method> getMethodsAnnotatedWithApiOperation(final Class<Annotation> type) {
	    final List<Method> methods = new ArrayList<Method>();
	    Class<?> klass = type;
	    while (klass != Object.class)
	    { 
	        final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
	        for (final Method method : allMethods)
	        {
	            if (method.isAnnotationPresent(ApiOperation.class))
	            {
	            	ApiOperation annotInstance = method.getAnnotation(ApiOperation.class);
	                System.out.println(annotInstance.httpMethod());     
	                methods.add(method);
	             
	            }
	        }
	        // move to the upper class in the hierarchy in search for more methods
	        klass = klass.getSuperclass();
	    }
	    return methods;
	}
	
	
	
	@SuppressWarnings("unused")
	private static <T> T findAnnotationValue(Element element, String annotationClass,
	        String valueName, Class<T> expectedType) {
	    T ret = null;
	    for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
	        DeclaredType annotationType = annotationMirror.getAnnotationType();
	        TypeElement annotationElement = (TypeElement) annotationType
	                .asElement();
	        if (annotationElement.getQualifiedName().contentEquals(
	                annotationClass)) {
	            ret = extractValue(annotationMirror, valueName, expectedType);
	            break;
	        }
	    }
	    return ret;
	}

	private static <T> T extractValue(AnnotationMirror annotationMirror,
	        String valueName, Class<T> expectedType) {
	    Map<ExecutableElement, AnnotationValue> elementValues = new HashMap<ExecutableElement, AnnotationValue>(
	            annotationMirror.getElementValues());
	    for (Entry<ExecutableElement, AnnotationValue> entry : elementValues
	            .entrySet()) {
	        if (entry.getKey().getSimpleName().contentEquals(valueName)) {
	            Object value = entry.getValue().getValue();
	            return expectedType.cast(value);
	        }
	    }
	    return null;
	}

}
