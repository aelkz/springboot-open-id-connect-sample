package com.redhat.rhsso.sample.controller;

import com.redhat.rhsso.sample.controller.validator.ProductValidator;
import com.redhat.rhsso.sample.model.Product;
import com.redhat.rhsso.sample.service.ProductService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @RequestMapping(path = "/v1/product", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(
            value = "Create",
            notes = "Create new product",
            response = Product.class)
    @PreAuthorize("hasAuthority('ROLE_PRODUCT_MAINTAINER')")
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product record = service.create(product);
        return ResponseEntity.ok().body(record);
    }

    @Timed(value = "product.getAll", description = "Get all products endpoint")
    @RequestMapping(path = "/v1/product", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(
            value = "Get",
            notes = "Get all products",
            response = Product.class)
    @PreAuthorize("hasAuthority('ROLE_PRODUCT_VIEWER')")
    public Page<Product> getAll(
            @ApiParam("Zero-based page index") @RequestParam(required = false) Integer page,
            @ApiParam("The size of the page to be returned") @RequestParam(required = false) Integer size
    ) {

        if (size == null)
            size = DEFAULT_PAGE_SIZE;

        if (page == null)
            page = 0;

        Pageable pageable = PageRequest.of(page, size);

        return service.findAll(pageable);
    }

    @InitBinder("event")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new ProductValidator());
    }
}
