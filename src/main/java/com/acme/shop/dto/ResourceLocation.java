package com.acme.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.servlet.http.HttpServletRequest;

@ApiModel(description = "A wrapper to locate a REST resource, typically used as response for resource creation")
public class ResourceLocation {

    @ApiModelProperty(notes = "The URI to locate a REST resource", example = "http://localhost:8080/shop-place/api/v1/resources/1", readOnly = true)
    @JsonProperty(value = "uri")
    private String uri;

    public ResourceLocation() {
    }

    public ResourceLocation(HttpServletRequest request, Long identifier) {
        this.uri = String.format("%s/%s", removeLastSlash(request.getRequestURL().toString()), identifier);
    }

    private String removeLastSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.lastIndexOf("/"));
        } else {
            return url;
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
