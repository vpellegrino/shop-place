package com.acme.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "An error message")
public class ApiError {

    @JsonProperty(value = "message")
    @ApiModelProperty(notes = "Short error message", example = "An error occurred")
    private final String message;

    @JsonProperty(value = "code")
    @ApiModelProperty(notes = "Error code", example = "500")
    private final String code;

    @JsonProperty(value = "transient")
    @ApiModelProperty(notes = "If true, indicates that the server considers the error a transient condition, such that a retry may succeed; " +
            "if false, indicates that the error is permanent, and retrying will always produce an error")
    private final Boolean trans;

    public ApiError(String code, String message, Boolean trans) {
        this.message = message;
        this.code = code;
        this.trans = trans;
    }

}
