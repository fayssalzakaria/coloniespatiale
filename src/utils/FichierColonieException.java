package utils;

/**
 * Cette exception est utilisée comme classe de base pour toutes les exceptions liées aux erreurs rencontrées
 * lors de la gestion des fichiers décrivant la colonie.
 * Elle hérite de la classe  Exception.
 * 

 * @author Zakaria Fayssal

 */
public class FichierColonieException extends Exception {

	private static final long serialVersionUID = 1L;//Numéro uid pour la sérialisation

	/**
     * Constructeur pour créer une nouvelle exception avec un message spécifié.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public FichierColonieException(String message) {
        super(message);
    }
}
