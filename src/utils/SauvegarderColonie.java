package utils;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;

import java.io.PrintWriter;

import colonie.Colon;

import colonie.Colonie;

/**
 * La classe SauvergarderColonie permet de sauvegarder une instance de Colonie
 * dans un fichier en format texte. Chaque colon de la colonie est écrit avec son nom et la ressource
 * associée, séparés par un deux-points.

 * @author Zakaria Fayssal

 */

public class SauvegarderColonie {

    /**
     * Sauvegarde une colonie dans un fichier spécifié.
     * Cette méthode parcourt tous les colons de la colonie et écrit leur nom et ressource
     * dans le fichier sous la forme `nom:ressource`.
     * 
     * @param nomCol L'objet Colonie contenant les colons à sauvegarder.
     * @param f Le fichier dans lequel les informations de la colonie doivent être sauvegardées.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'écriture dans le fichier.
     */
	
    public static void sauvegarderColonie(Colonie nomCol, File f) throws IOException {
    	
        // Ouvre le fichier en mode écriture avec un BufferedWriter et PrintWriter
    	
        try (PrintWriter printW = new PrintWriter(new BufferedWriter(new FileWriter(f)))) {
        	
            // Parcourt chaque colon et écrit son nom et sa ressource dans le fichier
        	
            for (Colon col : nomCol.getColonie()) {
            	
                printW.println(col.getNom() + ":" + col.getRessource());
                
            }
        }
    }
}
