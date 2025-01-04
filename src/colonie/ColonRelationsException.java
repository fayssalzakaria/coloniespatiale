package colonie;

/**
 * Cette exception est utilisée pour signaler une erreur lorsque le colon est présent en double dans le programme principal.
 * Elle hérite de la classe  ColonException.
 * 

 * @author Zakaria Fayssal

 */
public class ColonRelationsException extends ColonException {

	private static final long serialVersionUID = 1L;//Numero uid pour la sérialisation

	/**
     * Constructeur pour créer une nouvelle exception avec un message spécifié.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public ColonRelationsException(String message) {
        super(message);
    }
}
