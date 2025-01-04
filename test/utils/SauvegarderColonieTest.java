package utils;

import static org.junit.jupiter.api.Assertions.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import colonie.Colonie;

/**
 * La classe SauvegarderColonieTest permet de tester les fonctionnalités
 * de la classe SauvegarderColonie, notamment la sauvegarde des informations
 * d'une colonie dans un fichier texte.
 * 
 * Elle vérifie que les données de chaque colon (nom et ressource associée)
 * sont correctement enregistrées dans le fichier spécifié.
 * 

 * @author Zakaria Fayssal

 */
public class SauvegarderColonieTest {

    /**
     * Teste la méthode SauvegarderColonie.sauvegarderColonie.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture
     *                     ou de l'écriture du fichier.
     */
    @Test
    public void sauvegarderColonieTest() throws IOException {
        // Créer une colonie avec deux colons
        Colonie col = new Colonie(2);

        // Attribuer des ressources aux colons
        col.getColonie().get(0).setRessource("1");
        col.getColonie().get(1).setRessource("2");

        // Créer un fichier pour sauvegarder les données
        File fichierCol = new File("colonie.txt");

        // Sauvegarder la colonie dans le fichier
        SauvegarderColonie.sauvegarderColonie(col, fichierCol);

        // Lire le contenu du fichier et vérifier son contenu
        List<String> lines = Files.readAllLines(Paths.get(fichierCol.getAbsolutePath()));

        // Vérifier que le fichier contient les bonnes données
        assertEquals(2, lines.size(), "Le fichier doit contenir deux lignes");
        assertTrue(lines.contains("A:1"), "La ligne A:1 doit être présente dans le fichier");
        assertTrue(lines.contains("B:2"), "La ligne B:2 doit être présente dans le fichier");
    }
}
