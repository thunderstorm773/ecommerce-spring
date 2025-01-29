package com.tu.ecommerce.config;

import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unsupportedMethods = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};

        // disable HTTP methods for Product: PUT, POST, DELETE
         config.getExposureConfiguration()
                 .forDomainType(Product.class)
                 .withItemExposure(((metadata, httpMethods) -> httpMethods.disable(unsupportedMethods)))
                 .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(unsupportedMethods)));

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure(((metadata, httpMethods) -> httpMethods.disable(unsupportedMethods)))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(unsupportedMethods)));
    }
}
