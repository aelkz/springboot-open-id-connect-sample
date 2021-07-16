package com.redhat.sso.sample.controller;

import com.redhat.sso.sample.controller.validator.ProductValidator;
import com.redhat.sso.sample.model.Product;
import com.redhat.sso.sample.service.ProductService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Validated //required for @Valid on method parameters such as @RequesParam, @PathVariable, @RequestHeader
public class ProductController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    ProductService service;

    @Timed(value = "product.create", description = "Create a new product endpoint")
    @PreAuthorize("hasAuthority('ROLE_PRODUCT_MAINTAINER')")
    @RequestMapping(path = "/v1/product", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Create new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "product created",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Product.class))})
    })
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product record = service.create(product);
        return ResponseEntity.ok().body(record);
    }

    @Timed(value = "product.getAll", description = "Get all products endpoint")
    @PreAuthorize("hasRole('ROLE_PRODUCT_VIEWER')")
    @RequestMapping(path = "/v1/product", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Find all products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "product(s) found",
            content = {@Content(mediaType = "application/json",
            array = @ArraySchema(arraySchema = @Schema(implementation = Product.class)))})
    })
    public Page<Product> getAll(
            @Parameter(description = "Zero-based page index") @RequestParam(required = false) Integer page,
            @Parameter(description =  "The size of the page to be returned") @RequestParam(required = false) Integer size
    ) {

        if (size == null)
            size = DEFAULT_PAGE_SIZE;

        if (page == null)
            page = 0;

        Pageable pageable = PageRequest.of(page, size);

        return service.findAll(pageable);
    }

    /**
     * this is an example of unsecured endpoint
     */
    @Timed(value = "application.health", description = "Health check endpoint")
    @RequestMapping(path = "/v1/health", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "health check")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "application health status",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    public ResponseEntity<String> health( ) {
        return ResponseEntity.ok().body("ok");
    }

    @InitBinder("event")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new ProductValidator());
    }
}
