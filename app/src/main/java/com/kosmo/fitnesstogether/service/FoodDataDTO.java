package com.kosmo.fitnesstogether.service;

import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "I2790"
})
@Getter
@Setter
public class FoodDataDTO {

    public I2790 I2790;

}
