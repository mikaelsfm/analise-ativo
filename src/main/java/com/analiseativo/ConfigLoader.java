package com.analiseativo;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties = new Properties();

    public ConfigLoader() {
        try (var input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo config.properties não encontrado.");
            }
            properties.load(input);
            resolveEnvironmentVariables();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }

    private void resolveEnvironmentVariables() {
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (value != null && value.startsWith("${") && value.endsWith("}")) {
                String envVar = value.substring(2, value.length() - 1);
                String envValue = System.getenv(envVar); 
                if (envValue != null) {
                    properties.setProperty(key, envValue);
                } else {
                    throw new RuntimeException("Variável de ambiente não encontrada: " + envVar);
                }
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}