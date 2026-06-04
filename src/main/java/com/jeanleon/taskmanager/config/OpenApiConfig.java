// OpenApiConfig.java
package com.jeanleon.taskmanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI taskManagerOpenAPI() {

        // Configuration du schéma de sécurité JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Entrez votre token JWT avec le préfixe 'Bearer '");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        // Configuration des serveurs
        Server devServer = new Server()
                .url("http://localhost:8080")
                .description("Serveur de développement local");

        Server prodServer = new Server()
                .url("https://api.taskmanager.com")
                .description("Serveur de production");

        // Informations de contact
        Contact contact = new Contact()
                .name("TaskManager Support")
                .email("support@taskmanager.com")
                .url("https://taskmanager.com");

        // Licence
        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        // Configuration des informations de l'API
        Info info = new Info()
                .title("TaskManager API")
                .description("""
                        API REST complète pour la gestion de tâches TaskManager.
                        
                        ## Fonctionnalités
                        - Authentification JWT (inscription et connexion)
                        - CRUD complet des tâches
                        - Filtrage par statut et priorité
                        - Réorganisation par drag & drop
                        - Tableau de bord statistique
                        
                        ## Utilisation
                        1. Créez un compte via `/auth/register`
                        2. Connectez-vous via `/auth/login` pour obtenir un token JWT
                        3. Utilisez le token pour accéder aux endpoints protégés
                        """)
                .version("1.0.0")
                .contact(contact)
                .license(license)
                .termsOfService("https://taskmanager.com/terms");

        return new OpenAPI()
                .info(info)
                .servers(Arrays.asList(devServer, prodServer))
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}