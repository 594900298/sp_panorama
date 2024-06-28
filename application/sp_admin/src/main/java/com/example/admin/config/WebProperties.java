package com.example.admin.config;

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
     * admin包下统一前缀
     */
    Api admin = new Api("admin", "**.admin.controller.**");

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
