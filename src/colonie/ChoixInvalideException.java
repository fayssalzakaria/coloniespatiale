package colonie;

/**
 * Cette exception est utilisée pour gérer les erreurs concernant un choix erroné dans les menus du programme principal.
 * Elle hérite de la classe  Exception

 * @author Zakaria Fayssal

 */

public class ChoixInvalideException extends Exception {

    private static final long serialVersionUID = 1L;//Numéro uid pour la sérialisation

    /**
     * Constructeur pour créer une nouvelle exception avec un message spécifié.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public ChoixInvalideException(String message) {
        super(message);
    }
}
