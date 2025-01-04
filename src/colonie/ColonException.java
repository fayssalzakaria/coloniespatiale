package colonie;

/**
 * Cette exception est utilisée comme classe de base pour toutes les exceptions liées aux colons dans le programme principal.
 * Elle hérite de la classe  Exception
 * 

 * @author Zakaria Fayssal

 */
public class ColonException extends Exception {

	private static final long serialVersionUID = 1L; //Numéro uid pour la sérialisation

	/**
     * Constructeur pour créer une nouvelle exception avec un message spécifié.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public ColonException(String message) {
        super(message);
    }
}
