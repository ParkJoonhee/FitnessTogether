package com.kosmo.fitnesstogether.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "C005"
})
@Getter
@Setter
public class BarcodeDataDTO {

    public C005 C005;

}
