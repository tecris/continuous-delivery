package org.terra.bs.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/rest")
public class RestApplication extends Application {

	public RestApplication() {
        // https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-RESTEasy-2.X-Project-Setup-1.5
        // http://localhost:8080/bookstore/rest/swagger.json
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setTitle("Swagger Demo");
        beanConfig.setDescription("JAXRS RESTEasy Demo");
        beanConfig.setBasePath("/bookstore/rest");
        beanConfig.setResourcePackage("org.terra.bs");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }

}