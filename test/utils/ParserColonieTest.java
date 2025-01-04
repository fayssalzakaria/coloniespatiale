package utils;

import static org.junit.jupiter.api.Assertions.*;


import java.io.File;

import java.io.IOException;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.ValueSource;

import colonie.Colon;

import colonie.Colonie;

/**
 * Classe de test unitaire pour la classe ParserColonie.
 * Elle valide le bon fonctionnement des méthodes de traitement des fichiers
 * et des données de colonies.
 *
 * Les tests incluent la vérification des erreurs syntaxiques, des doublons,
 * des incohérences dans les ressources , les relations entre colons et les erreurs liee aux sections.
 *
 * Cette classe utilise JUnit 5 pour l'exécution des tests.
 *

 * @author Zakaria Fayssal

 */

public class ParserColonieTest {
	
	private static Colonie colonie;//colonie que l'on va initialise avant que le programme se lance
	
	@BeforeAll
	
	/**
     * Initialise une instance de Colonie à partir d'un fichier avant l'exécution des tests.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
	
	public static void setUp() throws IOException{
		
		String fichier = "fichierATester\\col1.txt";
		
        File f = new File(fichier);
        
        colonie = ParserColonie.lireColonie(f);	
	}
	
	  /**
     * Vérifie qu'une ligne sans point final déclenche une exception de syntaxe.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
	
	@Test
	
    void testLigneSansPoint() throws IOException {
		
        // Test si une ligne sans point déclenche une exception de syntaxe
		
        String fichier = "fichierATester\\colAvecLigneSansPoint.txt";
        
        File f=new File(fichier);
        
        FichierColonieException exception = assertThrows(FichierColonieSyntaxeException.class, () -> {
            ParserColonie.verifierFichier(f);
        });
        
        assertEquals("Erreur à la ligne 1: les lignes doivent se terminer par un point.", exception.getMessage());
    }
	
	  /**
     * Vérifie que la section "colon" doit apparaître en premier dans le fichier.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
	
    @Test
    void testColonSectionEnSecond() throws IOException {
    	
        // Test si la section "colon" n'est pas en premier
    	
    	String fichier = "fichierATester\\colonieSectionColonSecond.txt";
    	
        File f=new File(fichier);
        
        FichierColonieException exception = assertThrows(FichierColonieSectionException.class, () -> {
        	ParserColonie.verifierFichier(f);
        });
        
        assertEquals("Erreur à la ligne 1: l'ordre des section doit etre le suivant : Les colons puis les ressources puis les relation de detestations si ils existent et enfin les preferences.", exception.getMessage());
        
    }
    
    /**
     * Vérifie qu'un doublon dans les noms de colons déclenche une exception.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
    
    @Test
    
    void testDoublonColon() throws IOException {
    	
        // Test si un doublon de colon est détecté
    	
    	String fichier = "fichierATester\\colDoublonsColons.txt";
    	
        File f=new File(fichier);
        
        FichierColonieException exception = assertThrows(FichierColonieParametresException.class, () -> {
        	ParserColonie.verifierFichier(f);
        });
        
        assertEquals("Erreur à la ligne 2: 2 colons possèdent exactement le même nom", exception.getMessage());
        
    }
    
    /**
     * Vérifie que le nombre de ressources correspond au nombre de colons.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
    
    @Test
    
    void testRessourcesNonCorrespondantes() throws IOException {
    	
        // Test si le nombre de ressources est différent du nombre de colons
    	
    	String fichier= "fichierATester\\colRessourceDiffColons.txt";
    	
        File f=new File(fichier);
        
        FichierColonieException exception = assertThrows(FichierColonieParametresException.class, () -> {
        	
        	ParserColonie.verifierFichier(f);
        });
        
        assertEquals("Erreur à la ligne 19: le nombre de ressources doit être égal au nombre de colons.", exception.getMessage());
        
    }
    
    /**
     * Vérifie qu'un colon ne peut pas se détester lui-même.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
    
    @Test
    void testDetesteSelf() throws IOException {
    	
        // Test si un colon ne peut pas se détester lui-même
    	
    	String ligne = "fichierATester\\colDetesteLuimeme.txt";
    	
        File f=new File(ligne);
        
        FichierColonieException exception = assertThrows(FichierColonieParametresException.class, () -> {
        	ParserColonie.verifierFichier(f);
        });
        
        assertEquals("Erreur à la ligne 24: 1 colons ne peut pas se detester lui meme", exception.getMessage());
        
    }
    
    /**
     * Vérifie qu'une relation "déteste" ne peut pas être définie plusieurs fois.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
    
    @Test
    
    void testRelationsDetesteDouble() throws IOException {
    	
        // Test si une relation 'deteste' est déclarée plusieurs fois
    	
        String fichier = "fichierATester\\colRelationDouble.txt";
        
        File f=new File(fichier);
        
        FichierColonieException exception = assertThrows(FichierColonieParametresException.class, () -> {
        	ParserColonie.verifierFichier(f);
        });
        
        assertEquals("Erreur à la ligne 25: la relation entre '5' et '0' est déjà définie.", exception.getMessage());
        
    }
    
    /**
     * Vérifie que les fichiers valides ne génèrent pas d'exception.
     *
     * @param content le chemin du fichier à tester.
     * @throws IOException en cas de problème d'accès au fichier.
     */
   
    @ParameterizedTest
    
    @ValueSource(strings = {
        "fichierATester\\col1.txt",
        "fichierATester\\col2.txt",
        "fichierATester\\col3.txt"
    })
    
    void testValides(String content) throws IOException {
    	
        File f = new File(content);
        
        assertDoesNotThrow(() -> {
        	ParserColonie.verifierFichier(f);
        });
        
    }
    
    /**
     * Vérifie que les ressources et les colons ont la meme taille et que la colonie est bien initialise.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
    
    @Test
    
    void testLireColonieRessourcesEgalColon() throws IOException {
        
        assertNotNull(colonie);
        
        assertTrue(colonie.getColonie().size() ==10);  
        
        assertTrue(colonie.getRessourceDispo().size() == 10);  
         
    }
    
    /**
     * Vérifie que les ressources de la colonie sont correctement lues.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
  
    @Test
    
    void testLireColonieRessources() throws IOException {
    	
    	assertEquals(colonie.getRessourceDispo(),Arrays.asList(new String[] {"o1","o2","o3","o4","o5","o6","o7","o8","o9","o10"}));
    	
    }
    
    /**
     * Vérifie que les colons de la colonie sont correctement lues.
     *
     * @throws IOException en cas de problème d'accès au fichier.
     */
    
    @Test
    
    void testLireColonieColons() throws IOException {
    	
    	 assertEquals(colonie.getColonie(),Arrays.asList(new Colon[] {new Colon("0"),new Colon("1"),new Colon("2"),new Colon("3"),new Colon("4"),
    	new Colon("5"),new Colon("6"),new Colon("7"),new Colon("8"),new Colon("9")}));
    	 
    }
    
    /**
     * Vérifie que les relations entre les colons sont correctement lues et initialisées.
     */
    
    @Test
    
    void testLireColonieRelations() {
    	
    	assertEquals(colonie.getRelations().get(colonie.getColonie().get(0)),Arrays.asList(new Colon[] {new Colon("5"),new Colon("7")}));
    	
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(1)),Arrays.asList(new Colon[] {new Colon("6"),new Colon("7"),new Colon("8")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(2)),Arrays.asList(new Colon[] {new Colon("9")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(3)),Arrays.asList(new Colon[] {new Colon("4"),new Colon("8"),new Colon("9")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(4)),Arrays.asList(new Colon[] {new Colon("3"),new Colon("5")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(5)),Arrays.asList(new Colon[] {new Colon("0"),new Colon("4"),new Colon("8")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(6)),Arrays.asList(new Colon[] {new Colon("1"),new Colon("7")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(7)),Arrays.asList(new Colon[] {new Colon("0"),new Colon("1"),new Colon("6"),new Colon("8"),new Colon("9")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(8)),Arrays.asList(new Colon[] {new Colon("1"),new Colon("3"),new Colon("5"),new Colon("7")}));
        
        assertEquals(colonie.getRelations().get(colonie.getColonie().get(9)),Arrays.asList(new Colon[] {new Colon("2"),new Colon("3"),new Colon("7")}));
        
    }
    
    /**
     * Vérifie que les préférences des colons pour les ressources sont correctement lues.
     */
    
    @Test
    
    void testLireColoniePreferences() {
    	
    	assertEquals(colonie.getColonie().get(0).getPreferences(), Arrays.asList(new String[] {"o3","o9","o8","o6","o1","o10","o2","o5","o4","o7"}));
    	
        assertEquals(colonie.getColonie().get(1).getPreferences(), Arrays.asList(new String[] {"o6","o8","o2","o1","o9","o3","o5","o7","o10","o4"}));
        
        assertEquals(colonie.getColonie().get(2).getPreferences(), Arrays.asList(new String[] {"o2","o9","o3","o1","o10","o8","o7","o4","o5","o6"}));
        
        assertEquals(colonie.getColonie().get(3).getPreferences(), Arrays.asList(new String[] {"o7","o9","o6","o1","o5","o10","o4","o3","o8","o2"}));
        
        assertEquals(colonie.getColonie().get(4).getPreferences(), Arrays.asList(new String[] {"o4","o1","o5","o8","o10","o7","o9","o6","o3","o2"}));
        
        assertEquals(colonie.getColonie().get(5).getPreferences(), Arrays.asList(new String[] {"o6","o2","o9","o8","o5","o4","o1","o7","o3","o10"}));
        
        assertEquals(colonie.getColonie().get(6).getPreferences(), Arrays.asList(new String[] {"o4","o5","o8","o6","o10","o1","o2","o7","o9","o3"}));
        
        assertEquals(colonie.getColonie().get(7).getPreferences(), Arrays.asList(new String[] {"o9","o7","o10","o5","o8","o1","o6","o3","o4","o2"}));
        
        assertEquals(colonie.getColonie().get(8).getPreferences(), Arrays.asList(new String[] {"o5","o9","o2","o6","o8","o4","o1","o3","o10","o7"}));
        
        assertEquals(colonie.getColonie().get(9).getPreferences(), Arrays.asList(new String[] {"o5","o7","o6","o2","o9","o3","o4","o8","o10","o1"}));

    }
  
}
