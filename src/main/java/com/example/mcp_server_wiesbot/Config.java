package com.example.mcp_server_wiesbot;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    ToolCallbackProvider teamTools() {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(new TeamRepo())
                .build();
    }


}
