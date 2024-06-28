package com.example.api.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@Component
public class WebProperties {
    /**
     * api包下统一前缀
     */
    Api api = new Api("api", "**.api.controller.**");

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Api {
        @NotEmpty
        private String prefix;

        @NotEmpty
        private String controllerPath;
    }

}
