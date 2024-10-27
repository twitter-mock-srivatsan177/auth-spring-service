package com.srivatsan177.twitter.auth.models.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyErrorDTO {
    Map<String, String> errors;
}
