package utils;

/**
 * Cette exception est utilisée pour signaler une erreur liée à une section d'un fichier décrivant la colonie.
 * Elle hérite de la classe FichierColonieException.
 * 

 * @author Zakaria Fayssal

 */
public class FichierColonieSectionException extends FichierColonieException {

	private static final long serialVersionUID = 1L;//Numéro uid pour la sérialisation

	/**
     * Constructeur pour créer une nouvelle exception avec un message spécifié.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public FichierColonieSectionException(String message) {
        super(message);
    }
}
