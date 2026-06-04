// ResourceNotFoundException.java
package com.jeanleon.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception personnalisée pour les ressources non trouvées
 * Retourne un statut HTTP 404 NOT FOUND
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructeur avec message personnalisé
     * @param message Le message d'erreur détaillé
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec message et cause
     * @param message Le message d'erreur
     * @param cause La cause de l'exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur pour les ressources avec ID
     * @param resourceName Nom de la ressource (ex: "Tâche", "Utilisateur")
     * @param fieldName Nom du champ utilisé pour la recherche (ex: "id", "email")
     * @param fieldValue Valeur du champ recherché
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s non trouvé(e) avec %s : '%s'", resourceName, fieldName, fieldValue));
    }
}