package com.kosmo.fitnesstogether.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total_count",
        "row",
        "RESULT"
})
@Getter
@Setter
public class I2790 {
    public String total_count;
    public List<row1> row = null;
    public RESULT RESULT;



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    static class RESULT {
        public String CODE;
        public String MSG;
    }

}
