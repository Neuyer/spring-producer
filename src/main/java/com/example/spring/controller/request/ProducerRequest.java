package com.example.spring.controller.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProducerRequest {

    Long id;
    String message;

}
