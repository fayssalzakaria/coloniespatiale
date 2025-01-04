package utils;

import java.io.BufferedReader;


import java.io.File;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.HashSet;

import java.util.List;

import java.util.Set;

import colonie.Colon;

import colonie.ColonException;

import colonie.Colonie;



/**
 * La classe  ParserColonie fournit des méthodes pour analyser un fichier de qui represente une colonie,
 * vérifier la syntaxe du fichier, et lire les données pour créer des objets  Colonie.

 * @author Zakaria Fayssal

 */

public class ParserColonie {
	

	/**
     * Vérifie la syntaxe d'un fichier de colonie.
     * Cette méthode analyse le fichier ligne par ligne et vérifie les sections, 
     * la validité des paramètres et la cohérence des relations entre les colons.
     * 
     * @param f Le fichier à vérifier.
     * @throws FichierColonieException Si une erreur spécifique au fichier se produit.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture du fichier.
     */
	
    public static void verifierFichier(File f) throws FichierColonieException, IOException {
    	
        try (BufferedReader bufr = new BufferedReader(new FileReader(f))) {
        	
            List<String> colons = new ArrayList<>();
            
            List<String> ressources = new ArrayList<>();
            
            String ligne=null;
            
            boolean colonSection = false;
            
            boolean ressourcesSection = false;
            
            boolean detestesSection = false;
            
            boolean preferencesSection = false;
            
            int numLigne = 0;//ligne courante
            
            int derniereLigneColon = 0;//numero de la derniere ligne de la section colon
                        
            int derniereLigneRessource = 0;//numero de la derniere ligne de la section ressource
            
            int derniereLigneDeteste = 0;//numero de la derniere ligne de la section deteste
            
            int derniereLignePref = 0;//numero de la derniere ligne de la section preferences
            
            List<String>relationsDeteste=new ArrayList<String>();//Liste des couple de colons qui se detestent
            
            List<String> verifierPreference = new ArrayList<>();//verifier que on a une ligne de preferences par colon
            
            while ((ligne = bufr.readLine()) != null) {
            	numLigne++;
                if (ligne.isBlank()) { // Ignorer les lignes vides
                   	
                     continue;
                }
                               	
            	ligne=ligne.trim();//ignorer les espaces au debut et a la fin       
            	
                if (ligne.startsWith("colon(")) {
                	
                	colonSection = true;
                    
                    derniereLigneColon = numLigne;
                	
                    if (ressourcesSection || detestesSection || preferencesSection) { //Verifie que la section colon est en premier
                    	

                   	 		throw new FichierColonieSectionException(
                        		
                                "Erreur à la ligne " + numLigne + ": l'ordre des section doit etre le suivant : Les colons puis les ressources puis les relation de detestations si ils existent et enfin les preferences."
                            );
                        
                    }

                    String[] colonParams = verifierEtNettoyerParametres(ligne, numLigne);

                    if (colonParams.length > 1) {//verfier que nous avons 1 colon par ligne
                    	
                        throw new FichierColonieParametresException(
                        		
                            "Erreur à la ligne " + numLigne + ": une ligne 'colon' doit définir un seul colon."
                        );
                        
                    }
                    //verifier que le colom a un nom
                    if(colonParams[0].isEmpty()) {
                    	 throw new FichierColonieParametresException(
                         		
                                 "Erreur à la ligne " + numLigne + " un colon n'a pas de nom"
                             );
                    }
                    
                    if (colons.contains(colonParams[0])) {//verfifier que nous n'avons pas des colons qui se repetent
                    	
                        throw new FichierColonieParametresException(
                            "Erreur à la ligne " + numLigne + ": 2 colons possèdent exactement le même nom"
                        );
                    }
                    
                    colons.add(colonParams[0]);

                } else if (ligne.startsWith("ressource(")) {
                	
                    derniereLigneRessource = numLigne;
                    
                    ressourcesSection = true;
                    
                    if (!colonSection || detestesSection || preferencesSection) {//Verifie que la section des ressources est en deuxieme
                    	

                    		throw new FichierColonieSectionException(
                        		
                                "Erreur à la ligne " + numLigne + ": l'ordre des section doit etre le suivant : Les colons puis les ressources puis les relation de detestations si ils existent et enfin les preferences."
                            );
                        
                    }
                   

                    String[] ressourceParams = verifierEtNettoyerParametres(ligne, numLigne);
                    
                    //verifier que on a qu'une ressource
                    
                    if (ressourceParams.length > 1) {
                    	
                        throw new FichierColonieParametresException(
                            "Erreur à la ligne " + numLigne + ": une ligne 'ressource' doit définir une seule ressource."
                        );
                        
                    }
                    //verifier que la ressource est nommée
                    if(ressourceParams[0].isEmpty()) {
                    	 throw new FichierColonieParametresException(
                                 "Erreur à la ligne " + numLigne + ": une ressource n'a pas de nom"
                             );
                    }
                    if (ressources.contains(ressourceParams[0])) {
                    	
                        throw new FichierColonieParametresException(
                        		
                            "Erreur à la ligne " + numLigne + ": 2 ressources possèdent exactement le même nom"
                            
                        );                       
                    }
                    
                    ressources.add(ressourceParams[0]);                   
                                                                              
                } else if (ligne.startsWith("deteste(")) {
                	
                	detestesSection = true;
                      
                    derniereLigneDeteste = numLigne;
                    
                    if (!ressourcesSection || !colonSection ||preferencesSection) {// verifie que la section deteste est en troisieme
                    	
                        throw new FichierColonieSectionException(
                        		
                            "Erreur à la ligne " + numLigne + ": l'ordre des section doit etre le suivant : Les colons puis les ressources puis les relation de detestations si ils existent et enfin les preferences."
                        );
                    }
                	                                     
                    
                    String[] detesteParams = verifierEtNettoyerParametres(ligne, numLigne);//extraire les parametre de la ligne en verifiant la syntaxe

                    if (detesteParams.length != 2) {
                    	
                        throw new FichierColonieParametresException(
                            "Erreur à la ligne " + numLigne + ": on ne peut avoir que 2 personnes qui se détestent."
                        );
                        
                    }if(detesteParams[0].equals(detesteParams[1])) {
                    	
                   	 throw new FichierColonieParametresException(
                   			 
                             "Erreur à la ligne " + numLigne + ": 1 colons ne peut pas se detester lui meme");
                }
                //verifier que les colons qui se detestent sont definie    
                for (String det : detesteParams) {
                	
                    if (!colons.contains(det)) {
                    	
                        throw new FichierColonieSyntaxeException(
                            "Erreur à la ligne " + numLigne + ": le colon '" + det + "' n'est pas défini dans la section des colons."
                        );
                    }
                }
                //verifier que une relation n'est pas en double(les relations sont bilatérals)
                
                String colon1 = detesteParams[0];
                
                String colon2 = detesteParams[1];
                
                String relationColonsSens1 = colon1+','+colon2;
                
                String relationColonsSens2 = colon2+','+colon1;

                // Vérifier et ajouter la relation
                
                if (relationsDeteste.contains(relationColonsSens1)||relationsDeteste.contains(relationColonsSens2)) {
                	
                    throw new FichierColonieParametresException(
                        "Erreur à la ligne " + numLigne + ": la relation entre '" + colon1 + "' et '" + colon2 +
                        "' est déjà définie."
                    );
                    
                }
                relationsDeteste.add(relationColonsSens1);
                
                relationsDeteste.add(relationColonsSens2 );
                    
                } else if (ligne.startsWith("preferences(")) {

                    preferencesSection = true;
                    derniereLignePref=numLigne;
                    
                    if (!ressourcesSection || !colonSection) {//verifie que la section preference est en dernier
                    	
                   	 throw new FichierColonieSectionException(
                        		
                                "Erreur à la ligne " + numLigne + ": l'ordre des section doit etre le suivant : Les colons puis les ressources puis les relation de detestations si ils existent et enfin les preferences."
                            );
                       
                   }
                    
                    if(ressources.size() != colons.size()) {//verifier que le nombre de colons est egale au nombre de ressource
                  		 
                        throw new FichierColonieParametresException(
                       		 
                            "Erreur à la ligne " + derniereLigneRessource + ": le nombre de ressources doit être égal au nombre de colons."
                            
                        );
                    }
                  
                    
        
                    String[] preferenceParams = verifierEtNettoyerParametres(ligne, numLigne);
                    
                    //verifier que on a le bon nombre de préferences
                    if (preferenceParams.length != ressources.size() + 1) {
                    	
                        throw new FichierColonieParametresException(
                            "Erreur à la ligne " + numLigne + ": dans une ligne de preference, on doit definir le nom du colon suivie de toutes les"
                            		+ "ressources de la colonie dans l'ordre decroissant de préference"
                            		
                        );
                        
                    }
                    //verifier que le colon est defini
                    if (!colons.contains(preferenceParams[0])) {
                    	
                        throw new FichierColonieSyntaxeException(
                        		
                            "Erreur à la ligne " + numLigne + ": le colon '" + preferenceParams[0] + "' n'est pas défini."
                        );
                    }
                    
                    //verifier que les preferences sont définies
                    for (int i = 1; i < preferenceParams.length; i++) {
                    	
                        if (!ressources.contains(preferenceParams[i])) {
                        	
                            throw new FichierColonieSyntaxeException(
                                "Erreur à la ligne " + numLigne +
                                ": la ressource '" + preferenceParams[i] + "' n'est pas définie."
                                
                            );
                            
                        }
                        
                    }
                    
                 // Vérifier que les ressources de ce colon ne sont pas dupliquées
                    
                    Set<String> uniquePreferences = new HashSet<>();
                    
                    for (int i = 1; i < preferenceParams.length; i++) {
                    	
                        if (!uniquePreferences.add(preferenceParams[i])) {
                        	
                            throw new FichierColonieParametresException(
                            		
                                "Erreur à la ligne " + numLigne + ": le colon '" + preferenceParams[0] + 
                                " a une ressource " + preferenceParams[i] + " en double dans ses préférences."
                            );
                            
                        }
                        
                    }
                    //vérifier que nous avon une ligne de préferences par colon
                    if(verifierPreference.contains(preferenceParams[0])) {
                    	
                    	throw new FichierColonieParametresException(
                                "Erreur à la ligne " + numLigne +
                                " les préferences du colon "+preferenceParams[0]+ " sont deja definies"
                            );
                    	
                    }
                    
                    verifierPreference.add(preferenceParams[0]);
                    
                  }
                	
                 else {
                	 
                    throw new FichierColonieSectionException(
                        "Erreur à la ligne " + numLigne +
                        ": les lignes doivent commencer par 'colon', 'ressource', 'deteste' ou 'preferences'."
                    );
                    
                }
               
            }

            // Vérification des sections obligatoires
            
            if (!colonSection) {
            	
                throw new FichierColonieSectionException(
                		
                    "Erreur à la ligne 1: la section des colons est obligatoire."
                );
                
            }
            if (!ressourcesSection) {
            	
                throw new FichierColonieSectionException(
                    "Erreur à la ligne " + (derniereLigneColon+1) + ": la section des ressources est obligatoire."
                );
                
            }
            
            int ligneErreur = detestesSection ? derniereLigneDeteste : derniereLigneRessource;//chercher la ligne d'erreur, si la section deteste est definie la ligne d'erreur sera apres la section deteste
            
            if (!preferencesSection) {
                
                throw new FichierColonieSectionException(
                    "Erreur à la ligne " + (ligneErreur+1) + ": la section des préférences est obligatoire."
                );
                
            }
            
            //verif que on a une pref par colon
            
            if(verifierPreference.size()!=colons.size()) {
            	
            	throw new FichierColonieSectionException(
                        "Erreur à la ligne " + (derniereLignePref+1) +" on doit avoir une configuration de preference par colon");
            }
            
        }
        
    }
    
    /**
     * Extrait les paramètres d'une ligne en supprimant les parenthèses et les virgules.
     * 
     * @param ligne La ligne contenant les paramètres à extraire.
     * @return Un tableau de chaînes de caractères représentant les paramètres.
     */
    private static String[] extraireParametres(String ligne) {
    	
    	ligne=ligne.trim(); //enlevez les espace inutile au debut et a la fin de la ligne
    	
    	ligne = ligne.substring(0, ligne.length() - 1);//enleve le derniere point
    	
    	String[] parametres = ligne.split("\\(")[1].replace(")", "").split(",");//separer la chaine, selon la premiere parenthese ouvrante, puis supprime la parenthese fermante et  utilise le separateur ","
    	
    	 for (int i = 0; i < parametres.length; i++) {
    		 
             parametres[i] = parametres[i].trim();}
    	 
    	 return parametres;
    }
    
    /**
     * Vérifie la syntaxe et nettoie les paramètres d'une ligne de fichier.
     * @param ligne La ligne contenant les paramètres à vérifier.
     * @param numLigne Le numéro de la ligne dans le fichier.
     * @return Un tableau de chaînes de caractères représentant les paramètres nettoyés.
     * @throws FichierColonieSyntaxeException Si une erreur syntaxique se produit.
     */
    
    private static String[] verifierEtNettoyerParametres(String ligne, int numLigne) // Fonction pour vérifier la syntaxe d'une ligne et nettoyer les paramètres d'une ligne 
            throws FichierColonieSyntaxeException {
    	
        boolean virguleFin = false;//boolean pour savoir si on une virgule avant la derniere parentese
        
        ligne=ligne.trim();
        
        // Vérifier que la ligne se termine par un point
        
        if (!ligne.endsWith(".")) {
        	
            throw new FichierColonieSyntaxeException(
            		
                "Erreur à la ligne " + numLigne + ": les lignes doivent se terminer par un point."
            );
            
        }
       
        int indexPremiereParenthese = ligne.indexOf('(');
        
        int indexDerniereParenthese = ligne.lastIndexOf(')');
        
        int indexDernierPoint = ligne.lastIndexOf('.');
        
        //verifie que il y'a bien une parenthese a la fin
        
        if ( indexDerniereParenthese == -1 ) {
        	
            throw new FichierColonieSyntaxeException(
                "Erreur à la ligne " + numLigne + ": la ligne doit contenir une parenthese fermante avant le dernier point."
            );
            
        }   
        
        //Verifier que nous n'avons pas des charactere entre la derniere parenthese fermante et le point finale
        
        if (indexDerniereParenthese != -1 && indexDernierPoint != -1) {
        	
            String apresDerniereParenthese = ligne.substring(indexDerniereParenthese + 1, indexDernierPoint).trim();
            
            if (!apresDerniereParenthese.isEmpty()) {
            	
                throw new FichierColonieSyntaxeException(
                    "Erreur à la ligne " + numLigne +  " on ne doit rien ecrire entre la derniere parenthese et le point finale"
                );
                
            }
        }
        
        ligne = ligne.substring(0, ligne.length() - 1); // Supprimer le point final
        
        // Extraire les paramètres entre parenthèses
        
        String parametresBruts = ligne.substring(indexPremiereParenthese + 1, indexDerniereParenthese).trim();

        // Vérifier s'il y a une virgule en fin de liste
        
        if (parametresBruts.endsWith(",")) {
        	
            virguleFin = true;
            
        }
        // Découper les paramètres
        String[] parametres = parametresBruts.split(",");
        
        // Si une virgule etait  présente à la fin, ajouter un paramètre vide 
        if (virguleFin) {
        	
            // Créer un nouveau tableau avec une taille augmentée
        	
            String[] nouveauTableau = new String[parametres.length + 1];

            // Copier les éléments existants dans le nouveau tableau
            
            System.arraycopy(parametres, 0, nouveauTableau, 0, parametres.length);
            
            nouveauTableau[parametres.length] = ""; 

            // Mettre à jour le tableau original
            
            parametres = nouveauTableau;
        }
        
        // Vérifier et nettoyer chaque paramètre
        
        for (int i = 0; i < parametres.length; i++) {
        	
            parametres[i] = parametres[i].trim(); // Enlever les espaces au début et à la fin
            
    
        }

        

        return parametres;
    }
    
    /**
     * Lit un fichier de colonie et crée un objet Colonie avec les informations extraites.
     * 
     * @param f Le fichier à lire.
     * @return Une instance de Colonie contenant les colons, ressources, relations et préférences.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture du fichier.
     */
    
    public static Colonie lireColonie(File f) throws  IOException {
    	
        Colonie col = new Colonie();
        
        Colon colonCourant = null; // Le colon courant qu'on va traiter
        
        List<String> mots = new ArrayList<>(); // Liste temporaire pour les paramètres
        
        try (BufferedReader bufr = new BufferedReader(new FileReader(f))) {
        	
        	String ligne;
        	
        
        	while ((ligne = bufr.readLine()) != null) {
        		
        	  ligne=ligne.trim();//enlever les espace au debut et a la fin
        	  
        	  if (ligne.startsWith("colon(")) {
        		  
        		  mots=Arrays.asList(extraireParametres(ligne));
        		  
        		  String colonNom = mots.get(0); // Le nom du colon
        		  
                  colonCourant = new Colon(colonNom);
                  
                  col.getColonie().add(colonCourant); // Ajoute le colon à la colonie
                  
                  col.getRelations().put(colonCourant, new ArrayList<>()); // Initialise les relations
                  
        	  }else if (ligne.startsWith("ressource(")) {
        		  
                  // Détection de la section des ressources
        		  
                  mots = Arrays.asList(extraireParametres(ligne));
                  
                  String ressourceNom = mots.get(0);
                  
                  col.getRessourceDispo().add(ressourceNom); // Ajoute la ressource disponible
                  
              }else if (ligne.startsWith("deteste(")) {
            	  
                  // Détection de la section des colons qui se détestent
            	  
                  mots = Arrays.asList(extraireParametres(ligne));
                  
                  String colon1Nom = mots.get(0);
                  
                  String colon2Nom = mots.get(1);
                  
                  try {
                	  
                   col.ajouterRelation(col.trouverColonParNom(colon1Nom),col.trouverColonParNom(colon2Nom)); // Ajoute la relation de haine
                   
                  } catch (ColonException e) {
                	  
                	  System.out.println( e.getMessage());
                }
                                    
              }else if (ligne.startsWith("preferences(")) {
            	  
                  // Détection de la section des préférences
            	  
                  mots = Arrays.asList(extraireParametres(ligne));
                  
                  String colonNom = mots.get(0);
                  
                  String[]preferences=new String[mots.size()];
                
                  for(int i=0;i<mots.size();i++) {
                	  
                	  preferences[i]=mots.get(i);
                	  
                  }
                  
                  try {
                	  
                      Colon colon = col.trouverColonParNom(colonNom); // Trouve le colon par son nom
                      
                      colon.setPreferences(preferences, col.getRessourceDispo()); // Assigne les préférences du colon
                      
                  } catch (ColonException e) {
                	  
                      System.out.println( e.getMessage());
                      
                  } 
        }
        
        }
        	
    }
		return col;
    }
}