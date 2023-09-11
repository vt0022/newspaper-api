package com.intern.newspaper_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel<T> {
    private String status;
    private String message;
    private T data;
}
