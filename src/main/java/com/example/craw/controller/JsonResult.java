package com.example.craw.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult implements Serializable {

    private Integer status;
    private String message;
    private Object data;
}
