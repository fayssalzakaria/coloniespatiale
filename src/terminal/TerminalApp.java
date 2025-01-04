package terminal;

import java.io.File;



import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.InputMismatchException;

import java.util.Scanner;

import colonie.ChoixInvalideException;

import colonie.Colon;


import colonie.ColonException;

import colonie.Colonie;

import colonie.ColonPreferencesException;

import utils.FichierColonieException;

import utils.ParserColonie;
import utils.SauvegarderColonie;

/**
 * La classe TerminalApp permet de gérer l'interface terminal de l'application.
 * Elle offre des fonctionnalités pour configurer une colonie de colons, affecter des ressources,
 * gérer les préférences et minimiser la jalousie au sein de la colonie.
 * 

 * @author Zakaria Fayssal

 */

public class TerminalApp {

    /**
     * Point d'entrée pour exécuter l'application.
     * 
     * @param args les arguments passés via la ligne de commande. Si un fichier est fourni comme argument, 
     *             l'application traite ce fichier pour initialiser une colonie. Sinon, une configuration manuelle est lancée.
     */
	
    public static void start(String[] args) {
    	
        Scanner scan = new Scanner(System.in);

        // Si aucun fichier n'est fourni en argument, on passe en mode manuel
        if (args.length == 0) {
        	
            int nbColons = -1;

            // Demande du nombre de colons
            
            do {
            	
                System.out.print("Entrez le nombre de colons (entre 1 et 26) : ");
                
                try {
                	
                    nbColons = scan.nextInt();
                    
                    scan.nextLine(); 
                    
                    if (nbColons < 1 || nbColons > 26) {
                    	
                        throw new ChoixInvalideException("Erreur : Le nombre de colons doit être entre 1 et 26.");
                    }
                    
                } catch (InputMismatchException e) {
                	
                    System.out.println("Erreur : Veuillez entrer un entier valide.");
                    
                    scan.nextLine();
                    
                    nbColons = -1;
                    
                } catch (ChoixInvalideException e) {
                	
                    System.out.println(e.getMessage());
                    
                    nbColons = -1;
                    
                }
                
            } while (nbColons < 1 || nbColons > 26);

            Colonie maCol = new Colonie(nbColons);
            System.out.println("Colonie creer avec succes !\n"+maCol.afficherColons()+"\n"+maCol.afficherRessourceDisponible());
            boolean configurer = false;

            // Menu principal pour la configuration
            
            do {
            	
                System.out.println("\nMenu principal :");
                
                System.out.println("1 - Ajouter une relation entre deux colons");
                
                System.out.println("2 - Ajouter les préférences d'un colon");
                
                System.out.println("3 - Fin");
                
                System.out.print("Votre choix : ");

                try {
                	
                    int choix = scan.nextInt();
                    
                    scan.nextLine();
                    
                    switch (choix) {
                    
                        case 1:
                        	
                            // Ajout d'une relation entre deux colons
                        	
                            try {
                            	
                                System.out.print("Entrez le nom du premier colon : ");
                                
                                String nom1 = scan.nextLine().trim();
                                
                                Colon col1 = maCol.trouverColonParNom(nom1);
                                
                                System.out.print("Entrez le nom du second colon : ");
                                
                                String nom2 = scan.nextLine().trim();
                                
                                Colon col2 = maCol.trouverColonParNom(nom2);

                                maCol.ajouterRelation(col1, col2);
                                
                                System.out.println("Relation ajoutée entre le colon " + nom1 + " et le colon " + nom2);
                                
                            } catch (ColonException e) {
                            	
                                System.out.println(e.getMessage());
                                
                            }
                            
                            break;

                        case 2:
                        	
                            // Ajout des préférences d'un colon
                        	
                            System.out.println("Entrez le nom du colon et ses préférences (ex : A 1 2 3)");
                            
                            String phrase = scan.nextLine().trim();
                            
                            String[] mots = phrase.split("\\s+");//separer les entrer de l'utilisateur avec en sepateur 1 ou plusieur espaces
                            
                            String nom = mots[0];

                            try {
                            	
                                Colon colon = maCol.trouverColonParNom(nom);
                                
                                colon.setPreferences(mots, maCol.getRessourceDispo());
                                
                                System.out.println(colon.afficherPreferences());
                                
                            } catch (ColonException e) {
                            	
                                System.out.println(e.getMessage());
                                
                            } 
                            
                            break;

                        case 3:
                        	
                            System.out.println("Vérification des préférences :");
                            
                            try {
                            	
                            	maCol.verifierPreferences();
                                
                            	System.out.println(maCol.afficherPreferences());
                                
                                configurer = true;
                                
                            } catch (ColonPreferencesException e) {
                            	
                                System.out.println(e.getMessage());
                                
                            }
                            
                            break;

                        default:
                        	
                            throw new ChoixInvalideException("Choix incorrect. Veuillez choisir un entier entre 1 et 3.");
                            
                    }
                    
                } catch (InputMismatchException e) {
                	
                    System.out.println("Saisissez un entier valide");
                    
                    scan.nextLine();
                    
                } catch (ChoixInvalideException e) {
                	
                    System.out.println(e.getMessage());
                    
                }
                
            } while (!configurer);

            // Affectation initiale des ressources
            
            maCol.affecterRessources();
            
            System.out.println("Affectation naive des ressources effectuée avec succès, voici les affectations :"
                    + "\n" + maCol.afficherAffectation() );

            // Menu secondaire pour l'affectation
            
            boolean fin = false;
            
            do {
            	
                System.out.println("\nMenu affectation :");
                
                System.out.println("1 - Échanger les ressources de deux colons");
                
                System.out.println("2 - Afficher le nombre de colons jaloux");
                
                System.out.println("3 - Fin");
                
                System.out.print("Votre choix : ");

                try {
                	
                    int choix = scan.nextInt();
                    
                    scan.nextLine();
                    
                    switch (choix) {
                    
                        case 1:
                        	
                            // Échange de ressources entre deux colons
                        	
                            try {
                                System.out.print("Nom du premier colon : ");
                                
                                String nomEch1 = scan.nextLine().trim();
                                
                                Colon col1 = maCol.trouverColonParNom(nomEch1);
                                
                                System.out.print("Nom du second colon : ");
                                
                                String nomEch2 = scan.nextLine().trim();
                                
                                Colon col2 = maCol.trouverColonParNom(nomEch2);

                                maCol.echangerRessources(col1, col2);
                                
                                System.out.println("Échange effectué entre " + nomEch1 + " et " + nomEch2);
                                
                                System.out.println("Voici les affectations :"
                                        + "\n" +maCol.afficherAffectation());
                                
                            } catch (ColonException e) {
                            	
                                System.out.println(e.getMessage());
                                
                            }
                            
                            break;

                        case 2:
                        	
                            // Calcul du nombre de colons jaloux
                        	
                            int jaloux = maCol.calculerColonsJaloux();
                            
                            System.out.println("Voici les affectations :"
                                    + "\n" + maCol.afficherAffectation() 
                                    + "\nCoût en jalousie : " + jaloux
                                    + "\n" + maCol.afficherJaloux());
                            
                            
                            break;

                        case 3:
                        	
                            System.out.println("Voici les affectations :"
                                    + "\n"+maCol.afficherAffectation());
                            
                            System.out.println("Fin du programme.");
                            
                            fin = true;
                            
                            break;

                        default:
                        	
                            throw new ChoixInvalideException("Choix incorrect. Veuillez choisir un entier entre 1 et 3.");
                            
                    }
                    
                } catch (InputMismatchException e) {
                	
                    System.out.println("Saisissez un entier valide");
                    
                    scan.nextLine();
                    
                } catch (ChoixInvalideException e) {
                	
                    System.out.println(e.getMessage());
                    
                }
                
            } while (!fin);

            scan.close();
            
        } else {
        	
            // Gestion du fichier colonie via argument
        	
            String cheminFichier = args[0];
            
            Colonie maCol = null;
            
            try {
            	
                File f = new File(cheminFichier);
                
                ParserColonie.verifierFichier(f);
                
                maCol = ParserColonie.lireColonie(f);
                
                System.out.println("Fichier vérifié avec succès, voici les informations concernant la colonie :\n" +
                        maCol.afficherColons() + "\n" +
                        maCol.afficherRessourceDisponible() + "\n" +
                        maCol.afficherRelations() + "\n" +
                        maCol.afficherPreferences());

                boolean fin = false;
                
                do {
                	
                    System.out.println("\nMenu fichier colonie :");
                    
                    System.out.println("1 - Résolution automatique");
                    
                    System.out.println("2 - Sauvegarder la solution actuelle");
                    
                    System.out.println("3 - Fin");
                    
                    System.out.print("Votre choix : ");

                    try {
                    	
                        int choix = scan.nextInt();
                        
                        scan.nextLine();
                        
                        switch (choix) {
                        
                            case 1:
                            	
                                // Résolution automatique
                            	
                                try {
                                	
                                    maCol.affecterRessources();
                                    
                                    System.out.println("Affectation naive des ressources effectuée avec succès, voici les affectations :"
                    	                    + "\n" + maCol.afficherAffectation() 
                    	                    + "\nCoût en jalousie : " + maCol.calculerColonsJaloux() 
                    	                    + "\n" + maCol.afficherJaloux());
                                    
                                    maCol.minimiserJalousie(maCol.getColonie().size()*10);
                                    
                                    System.out.println("Minimisation du nombre de colons jaloux effectuée avec succès, voici les affectations :"
                    	                    + "\n" + maCol.afficherAffectation()
                    	                    + "\nCoût en jalousie : " + maCol.calculerColonsJaloux() 
                    	                    + "\n" + maCol.afficherJaloux());
                                    
                                } catch (ColonException e) {
                                	
                                    System.out.println(e.getMessage());
                                    
                                }
                                
                                break;

                            case 2:
                            	
                                // Sauvegarde de la colonie
                            	
                                try {                      

                                     System.out.print("Entrez le nom du fichier dans lequelle vous voulez sauvegarder la solution : ");
                                     
                                     String fichierNom = scan.nextLine(); // Lecture du nom du fichier entré par l'utilisateur

                                     File fichierCol = new File(fichierNom); // Création du fichier avec le nom donné
                                     
                                    SauvegarderColonie.sauvegarderColonie(maCol, fichierCol);
                                    
                                    System.out.print("Solution sauvergadé avec succés !");
                                    
                                } catch (FileNotFoundException e) {
                                	
                                    System.out.println("Le fichier n'a pas été trouvé. Vérifiez le chemin spécifié.");
                                    
                                }catch (IOException e) {
                                	
                                    System.out.println(e.getMessage());
                                    
                                }
                                
                                break;

                            case 3:
                            	
                                // Fin du programme
                            	
                                System.out.println("Fin du programme.");
                                
                                fin = true;
                                
                                break;

                            default:
                            	
                                throw new ChoixInvalideException("Choix incorrect. Veuillez choisir un entier entre 1 et 3.");
                        }
                        
                    } catch (InputMismatchException e) {
                    	
                        System.out.println("Saisissez un entier valide");
                        
                        scan.nextLine();
                        
                    } catch (ChoixInvalideException e) {
                    	
                        System.out.println(e.getMessage());
                        
                    }
                    
                } while (!fin);

                scan.close();
                
            } catch (FileNotFoundException e) {
            	
                System.out.println("Erreur : Fichier inexistant.");
                
            } catch (IOException e) {
            	
                System.out.println(e.getMessage());
                
            }catch(FichierColonieException e) {
            	
    			System.out.println(e.getMessage());
    			
    		}
        }
    }
}
