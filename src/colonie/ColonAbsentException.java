package colonie;

/**
 * Cette exception est utilisée pour signaler une erreur lorsqu'un colon est absent dans le programme principal.
 * Elle hérite de la classe ColonException.

 * @author Zakaria Fayssal

 */
public class ColonAbsentException extends ColonException {

    private static final long serialVersionUID = 1L;//Numéro uid pour la sérialisation

    /**
     * Constructeur pour créer une nouvelle exception avec un message spécifié.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public ColonAbsentException(String message) {
        super(message);
    }
}
