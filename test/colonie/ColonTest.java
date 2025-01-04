package colonie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

/**
 * Classe de test pour la classe  Colon.
 * 
 * Cette classe contient une série de tests unitaires pour vérifier le bon fonctionnement 
 * des méthodes de la classe Colon.
 *
 * @author Zakaria Fayssal

 */
public class ColonTest {

    /**
     * Teste la méthode getNom() pour vérifier que le nom du colon est correctement renvoyé.
     */
    @Test
    public void testGetNom() {
        Colon colon = new Colon("A");
        assertEquals("A", colon.getNom());
    }

    /**
     * Teste la méthode PreferencesFromInput(String[], List) avec une entrée valide.
     * 
     * @throws PreferencesException si une erreur survient lors de la définition des préférences.
     */
    @Test
    public void testSetPreferences_ValidInput() throws ColonPreferencesException {
        Colon colon = new Colon("A");
        List<String> ressourcesDispo = Arrays.asList("R1", "R2", "R3");
        String[] input = {"A", "R1", "R2", "R3"};
        colon.setPreferences(input, ressourcesDispo);
        assertEquals(Arrays.asList("R1", "R2", "R3"), colon.getPreferences());
    }

    /**
     * Teste la méthode setPreferencesFromInput(String[], List) 
     * lorsque des ressources invalides sont fournies.
     */
    @Test
    public void testSetPreferences_InvalidResource() {
        Colon colon = new Colon("A");
        List<String> ressourcesDispo = Arrays.asList("R1", "R2", "R3");
        String[] input = {"A", "R1", "R4", "R3"};
        assertThrows(ColonPreferencesException.class, () -> 
            colon.setPreferences(input, ressourcesDispo)
        );
    }

    /**
     * Teste la méthode setPreferencesFromInput(String[], List)
     * lorsque des préférences en double sont fournies.
     */
    @Test
    public void testSetPreferences_DuplicatePreferences() {
        Colon colon = new Colon("A");
        List<String> ressourcesDispo = Arrays.asList("R1", "R2", "R3");
        String[] input = {"A", "R1", "R2", "R1"};
        assertThrows(ColonPreferencesException.class, () -> 
            colon.setPreferences(input, ressourcesDispo)
        );
    }

    /**
     * Teste la méthode setPreferencesFromInput(String[], List) 
     * lorsque des préférences sont manquantes.
     */
    @Test
    public void testSetPreferences_MissingPreferences() {
        Colon colon = new Colon("A");
        List<String> ressourcesDispo = Arrays.asList("R1", "R2", "R3");
        String[] input = {"A", "R1", "R2"};
        assertThrows(ColonPreferencesException.class, () -> 
            colon.setPreferences(input, ressourcesDispo)
        );
    }

    /**
     * Teste la méthode setPreferencesFromInput(String[], List)
     * lorsque des préférences supplémentaires sont fournies.
     */
    @Test
    public void testSetPreferences_ExtraPreferences() {
        Colon colon = new Colon("A");
        List<String> ressourcesDispo = Arrays.asList("R1", "R2", "R3");
        String[] input = {"A", "R1", "R2", "R3", "R4"};
        assertThrows(ColonPreferencesException.class, () -> 
            colon.setPreferences(input, ressourcesDispo)
        );
    }

    /**
     * Teste la méthode afficherPreferences() pour s'assurer 
     * qu'elle affiche les préférences correctement.
     * 
     * @throws PreferencesException si une erreur survient lors de la définition des préférences.
     */
    @Test
    public void testAfficherPreferences() throws ColonPreferencesException {
        Colon colon = new Colon("A");
        colon.setPreferences(new String[]{"A", "R1", "R2", "R3"}, Arrays.asList("R1", "R2", "R3"));
        String output = colon.afficherPreferences();
        assertEquals("Voici les préférences du colon A\nR1>R2>R3", output);
    }

    /**
     * Teste la méthode estPotentiellementJalou(Colon)
     * pour vérifier si un colon peut être jaloux d'un autre colon (cas positif).
     * 
     * @throws PreferencesException si une erreur survient lors de la définition des préférences.
     */
    @Test
    public void testEstPotentiellementJalou_True() throws ColonPreferencesException {
        Colon colon1 = new Colon("A");
        Colon colon2 = new Colon("B");

        colon1.setPreferences(new String[]{"A", "R1", "R2", "R3"}, Arrays.asList("R1", "R2", "R3"));
        colon2.setPreferences(new String[]{"B", "R1", "R2", "R3"}, Arrays.asList("R1", "R2", "R3"));

        colon1.setRessource("R3");
        colon2.setRessource("R1");

        assertTrue(colon1.estPotentiellementJalou(colon2));
    }

    /**
     * Teste la méthode estPotentiellementJalou(Colon)
     * pour vérifier si un colon ne peut pas être jaloux d'un autre colon (cas négatif).
     * 
     * @throws PreferencesException si une erreur survient lors de la définition des préférences.
     */
    @Test
    public void testEstPotentiellementJalou_False() throws ColonPreferencesException {
        Colon colon1 = new Colon("A");
        Colon colon2 = new Colon("B");

        colon1.setPreferences(new String[]{"A", "R1", "R2", "R3"}, Arrays.asList("R1", "R2", "R3"));
        colon2.setPreferences(new String[]{"B", "R1", "R2", "R3"}, Arrays.asList("R1", "R2", "R3"));

        colon1.setRessource("R2");
        colon2.setRessource("R3");

        assertFalse(colon1.estPotentiellementJalou(colon2));
    }

    /**
     * Teste la méthode equals(Object) pour vérifier qu'un colon est égal à lui-même.
     */
    @Test
    public void testEquals_SameInstance() {
        Colon colon = new Colon("A");
        assertTrue(colon.equals(colon), "Un objet doit être égal à lui-même.");
    }

    /**
     * Teste la méthode equals(Object) pour vérifier que deux colons avec le même nom 
     * sont considérés égaux.
     */
    @Test
    public void testEquals_SameName() {
        Colon colon1 = new Colon("A");
        Colon colon2 = new Colon("A");
        assertTrue(colon1.equals(colon2), "Deux colons avec le même nom doivent être égaux.");
    }

    /**
     * Teste la méthode equals(Object) pour vérifier que deux colons avec des noms différents 
     * ne sont pas considérés égaux.
     */
    @Test
    public void testEquals_DifferentName() {
        Colon colon1 = new Colon("A");
        Colon colon2 = new Colon("B");
        assertFalse(colon1.equals(colon2), "Deux colons avec des noms différents ne doivent pas être égaux.");
    }

    /**
     * Teste la méthode equals(Object) pour vérifier qu'un colon n'est pas égal à un objet null.
     */
    @Test
    public void testEquals_NullObject() {
        Colon colon = new Colon("A");
        assertFalse(colon.equals(null), "Un colon ne doit pas être égal à un objet null.");
    }

    /**
     * Teste la méthode equals(Object) pour vérifier qu'un colon n'est pas égal 
     * à un objet d'une autre classe.
     */
    @Test
    public void testEquals_DifferentClass() {
        Colon colon = new Colon("A");
        String notAColon = "Not a colon";
        assertFalse(colon.equals(notAColon), "Un colon ne doit pas être égal à un objet d'une autre classe.");
    }
}
