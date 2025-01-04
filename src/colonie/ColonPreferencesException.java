 package colonie;

/**
 * Cette exception est utilisée  pour toutes les exceptions liées aux préférences des colons.
 * Elle hérite de la classe ColonException.
 * 

 * @author Zakaria Fayssal

 */
public class ColonPreferencesException extends ColonException {

	private static final long serialVersionUID = 1L;//Numéro uid pour la sérialisation

	/**
     * Constructeur pour créer une nouvelle exception avec un message spécifié.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public ColonPreferencesException(String message) {
        super(message);
    }
}
