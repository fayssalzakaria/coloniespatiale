package colonie;

import org.junit.jupiter.api.Test;
import utils.ParserColonie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 * Classe de tests unitaires pour la classe Colonie.
 * Elle couvre les principales fonctionnalités telles que la gestion des relations entre colons,
 * l'affectation et l'échange des ressources, ainsi que la minimisation de la jalousie.
 * 
 * @author Zakaria Fayssal

 */
public class ColonieTest {

    private Colonie colonie;// colonie saisie manuellement
    private Colon colon1;//premier colon de la colonie saisie manuellement
    private Colon colon2;//deuxieme colon de la colonie saisie manuellement
    private Colon colon3;//troisieme colon de la colonie saisie manuellement
    
    private static Colonie colonieFichier1;//colonie du fichier 1
    private static Colonie colonieFichier2;//colonie du fichier 2
    private static Colonie colonieFichier3;//colonie du fichier 3
    
    /**
     * Méthode exécutée avant chaque test.
     * Initialise une colonie et configure les colons avec leurs préférences par défaut.
     * 
     * @throws ColonAbsentException si un colon n'est pas trouvé
     * @throws PreferencesException si les préférences des colons ne sont pas valides
     */
    @BeforeEach
    public void setUp() throws ColonException {
    	 // Initialisation de la colonie et des colons
        colonie = new Colonie(3); // Initialisation avec 3 colons
        colon1 = colonie.trouverColonParNom("A");
        colon2 = colonie.trouverColonParNom("B");
        colon3 = colonie.trouverColonParNom("C");

        // Configuration des préférences des colons
        colon1.setPreferences(Arrays.asList(new String[]{"1", "2", "3"}));
        colon2.setPreferences(Arrays.asList(new String[]{"1", "2", "3"}));
        colon3.setPreferences(Arrays.asList(new String[]{"1", "2", "3"}));
       
    }
    /**
     * Méthode exécutée une seule fois avant tous les tests.
     * Initialise des colonies à partir de fichiers pour tester des cas spécifiques.
     * 
     * @throws IOException si les fichiers nécessaires ne peuvent pas être lus
     */
    @BeforeAll
    public static void setUpFichier() throws IOException {
    	File f1=new File("fichierATester\\col1.txt");
        colonieFichier1 = ParserColonie.lireColonie(f1); // Initialise une colonie avec 3 colons
        File f2=new File("fichierATester\\col2.txt");
        colonieFichier2 = ParserColonie.lireColonie(f2); // Initialise une colonie avec 3 colons
        File f3=new File("fichierATester\\col3.txt");
        colonieFichier3= ParserColonie.lireColonie(f3); // Initialise une colonie avec 3 colons
    }
    /**
     * Teste la création d'une colonie avec les paramètres initiaux.
     * Vérifie que la taille des listes de colons et de ressources correspond à l'attendu.
     */
    @Test
    public void testColonieCreation() {
        
        assertEquals(3, colonie.getColonie().size());
        assertEquals(3, colonie.getRessourceDispo().size());
    }
    /**
     * Teste l'ajout d'une relation valide entre deux colons.
     * 
     * @throws ColonException si une erreur survient lors de l'ajout de la relation
     */
    @Test
    void testAjouterRelation() throws ColonException {

        colonie.ajouterRelation(colon1, colon2);
        assertTrue(colonie.getRelations().get(colon1).contains(colon2) && colonie.getRelations().get(colon2).contains(colon1));
    }
    /**
     * Teste les cas d'erreurs lors de l'ajout de relations entre colons.
     * 
     * @throws ColonException si une erreur survient lors de l'ajout de la relation
     */
    @Test
    void testAjouterRelationErreurs() throws ColonException {
    
        colonie.ajouterRelation(colon2, colon1);        
        assertThrows(ColonRelationsException.class,()->colonie.ajouterRelation(colon1, colon1));//verifier que si on ajoute une relation entre 2 meme colon on obtient une erreur
        assertThrows(ColonRelationsException.class,()->colonie.ajouterRelation(colon1, colon2));///verifier que si on ajoute un relation deja ajouter, on obtient une erreu
        
    }
    /**
     * Teste la recherche d'un colon par son nom en ignorant la casse.
     * 
     * @throws ColonAbsentException si le colon n'est pas trouvé
     */
    
    @Test
  
    void testTrouverColonParNom() throws ColonAbsentException {
        // Vérifie que la recherche ignore la casse des lettres
        Colon foundColon = colonie.trouverColonParNom("b");
        assertNotNull(foundColon);
        assertEquals("B", foundColon.getNom()); // Vérifie que le bon colon a été trouvé
    }
    /**
     * Vérifie qu'une exception est levée si un nom vide est fourni lors de la recherche d'un colon.
     */
    @Test
    void testTrouverColonParNomNomVide() {
        // Vérifie qu'une exception est levée si le nom est vide
        assertThrows(ColonAbsentException.class, () -> colonie.trouverColonParNom(""));
    }
    /**
     * Teste l'affectation des ressources aux colons en fonction de leurs préférences.
     */
    @Test
    public void testAffecterRessources() {
        // Affectation des ressources
        colonie.affecterRessources();

        // Vérifier que chaque colon a bien reçu une ressource de ses préférences
        assertEquals("1", colon1.getRessource());
        assertEquals("2", colon2.getRessource());
        assertEquals("3", colon3.getRessource());
    }
    /**
     * Teste l'échange de ressources entre deux colons.
     * 
     * @throws ColonException si une erreur survient lors de l'échange
     */
    @Test
    public void testEchangerRessourcesValide() throws ColonException {
        // Echange des ressources entre colon1 et colon2
         colonie.affecterRessources();
        colonie.echangerRessources(colon1, colon2);

        // Vérifier que les ressources ont bien été échangées
        assertEquals("2", colon1.getRessource());
        assertEquals("1", colon2.getRessource());
    }
    /**
     * Teste les erreurs lors de l'échange de ressources avec un colon absent.
     */
    @Test
    public void testEchangerRessourcesAvecColonAbsent() {
        // Vérifier que l'exception est lancée si un colon n'est pas présent dans la colonie
        assertThrows(ColonAbsentException.class, () -> {
            colonie.echangerRessources(colon1, new Colon("D"));
        });
    }
    /**
     * Teste les erreurs lors de l'échange de ressources avec le même colon.
     */
    @Test
    public void testEchangerRessourcesAvecMemeColon() {
        // Vérifier que l'exception est lancée si on essaie d'échanger les ressources entre le même colon
        assertThrows(ColonException.class, () -> {
            colonie.echangerRessources(colon1, colon1);
        });
    }
    /**
     * Teste l'affectation des ressources à partir d'un fichier.
     */
    @Test
    public void testAffecterRessource() {
    	colonieFichier1.affecterRessources();//affectation naive des ressources
    	assertEquals(colonieFichier1.getColonie().get(0).getRessource(),"o3");//verfiier que l'affectation naive affecte les bonne ressources
    	assertEquals(colonieFichier1.getColonie().get(1).getRessource(),"o6");
    	assertEquals(colonieFichier1.getColonie().get(2).getRessource(),"o2");
    	assertEquals(colonieFichier1.getColonie().get(3).getRessource(),"o7");
    	assertEquals(colonieFichier1.getColonie().get(4).getRessource(),"o4");
    	assertEquals(colonieFichier1.getColonie().get(5).getRessource(),"o9");
    	assertEquals(colonieFichier1.getColonie().get(6).getRessource(),"o5");
    	assertEquals(colonieFichier1.getColonie().get(7).getRessource(),"o10");
    	assertEquals(colonieFichier1.getColonie().get(8).getRessource(),"o8");
    	assertEquals(colonieFichier1.getColonie().get(9).getRessource(),"o1");
    }
    /**
     * Teste le calcul du nombre de colons jaloux après l'affectation des ressources.
     */
    @Test
    public void testCalculerJalousie() {
    	colonieFichier2.affecterRessources();
    	colonieFichier3.affecterRessources();
    	assertEquals(colonieFichier1.calculerColonsJaloux(),2);//Tester si la nombre de colon jaloux est bien celui qui est attendu
    	assertEquals(colonieFichier2.calculerColonsJaloux(),2);
    	assertEquals(colonieFichier3.calculerColonsJaloux(),3);
    	
    }
    /**
     * Teste la minimisation de la jalousie à travers un algorithme.
     * 
     * @throws ColonException si une erreur survient lors de l'exécution de l'algorithme
     */
    @Test 
    public void testMinimiserJalousie() throws ColonException {
    	//Tester si l'algo de minimisation ernvoie une valeur minimale
    	colonieFichier1.minimiserJalousie(100);
    	colonieFichier2.minimiserJalousie(100);
    	colonieFichier3.minimiserJalousie(100);
    	assertEquals(colonieFichier1.calculerColonsJaloux(),0);
    	assertEquals(colonieFichier2.calculerColonsJaloux(),1);
    	assertEquals(colonieFichier3.calculerColonsJaloux(),0);
    }
    /**
     * Teste la vérification des préférences des colons.
     */

    @Test
    public void testVerifierPreferences() {
     
        assertDoesNotThrow(() -> {//cas ou tout les colons ont des prefences
            colonie.verifierPreferences(); 
        });

        
        colon3.setPreferences(new ArrayList<>()); //cas ou un colon n'a pas de preferences
        ColonPreferencesException exception = assertThrows(ColonPreferencesException.class, () -> {
            colonie.verifierPreferences();
        });

       
        assertTrue(exception.getMessage().contains("Le colon C ne possedent pas de preferences"));
        assertTrue(exception.getMessage().contains("Des colons n'ont pas de preferences, affectation imposible"));
    }
    

  
    
}
