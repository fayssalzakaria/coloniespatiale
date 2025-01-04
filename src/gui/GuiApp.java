package gui;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;

import colonie.Colonie;

import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import javafx.stage.Stage;

import utils.FichierColonieException;

import utils.ParserColonie;


/**
 * Classe GuiApp.
 * Cette classe représente une application graphique JavaFX pour la gestion d'une colonie. 
 * Elle permet l'initialisation de la colonie, l'affichage de ses informations, la gestion des relations, 
 * des préférences, ainsi que la résolution automatique des affectations et la sauvegarde des données.
 * 

 * @author Zakaria Fayssal

 */

public class GuiApp extends Application{
	
	/**
     * Méthode principale de l'application JavaFX.
     * Elle initialise la scène et affiche une interface utilisateur 
     * pour gérer une colonie.
     * Cette colonie est soit lue à partir d'un fichier si un argument
     * en ligne de commande est fourni (correspondant au chemin vers le fichier), 
     * soit saisie manuellement.
     * @param stagePrincipal la fenêtre principale de l'application.
     */
	
    @Override
    public void start(Stage stagePrincipal) {
    	
        // Vérifie si un argument en ligne de commande est passé (chemin vers un fichier).
        if (getParameters().getRaw().isEmpty()) { // Si aucun argument n'est passé, on crée l'interface pour la saisie manuelle
            
            VBox vbox = new VBox();
            vbox.setSpacing(10);  // Espace entre les éléments dans la VBox

            // Label pour demander le nombre de colons
            Label labelNombreColons = new Label("Entrez le nombre de colons (entre 1 et 26) : ");
            
            // Champ de texte pour la saisie du nombre de colons
            TextField champTexte = new TextField();
            
            // Bouton pour valider la saisie
            Button boutonValider = new Button("Valider");
    
            // Label pour afficher les erreurs (si le nombre de colons est incorrect)
            
            Label labelErreur = new Label();
            
            labelErreur.setTextFill(Color.RED);
    
            // Zone de texte pour afficher les informations sur la colonie créée
            
            TextArea zoneColonie = new TextArea();
            
            zoneColonie.setEditable(false);  // Désactive l'édition dans cette zone
            
            zoneColonie.setWrapText(true);   // Active le retour à la ligne dans le texte
    
            // Bouton pour accéder au menu principal après la création de la colonie
            
            Button boutonMenuPrincipal = new Button("Aller au menu principal");
            
            boutonMenuPrincipal.setVisible(false); // Initialement caché
    
            // Action à effectuer lors du clic sur le bouton "Valider"
            boutonValider.setOnAction(e -> { 
                try {
                    int nbColons = Integer.parseInt(champTexte.getText());  // Récupère le nombre de colons saisi

                    // Vérifie si le nombre de colons est dans la plage valide (1 à 26)
                    if (nbColons < 1 || nbColons > 26) {
                        labelErreur.setText("Erreur : Le nombre de colons doit être entre 1 et 26.");
                    } else {
                        // Masque le message d'erreur si la saisie est correcte
                        labelErreur.setVisible(false);
                        
                        // Crée une nouvelle colonie avec le nombre de colons saisi
                        Colonie maColonie = new Colonie(nbColons);
                        
                        // Affiche les informations de la colonie dans la zone de texte
                        zoneColonie.setText("Colonie créée avec succès !\n" + maColonie.afficherColons()
                        + "\n" + maColonie.afficherRessourceDisponible());
                        
                        // Action à effectuer lors du clic sur le bouton "Menu Principal"
                        boutonMenuPrincipal.setOnAction(action ->  GuiColonieManuelle.afficherMenuPrincipal(stagePrincipal, maColonie));
                        
                        // Cache le bouton "Valider" et rend le bouton "Menu Principal" visible
                        boutonValider.setVisible(false);
                        boutonMenuPrincipal.setVisible(true);
                    }
                } catch (NumberFormatException ex) {
                    // Si la saisie n'est pas un entier valide, affiche un message d'erreur
                    labelErreur.setText("Erreur : Veuillez entrer un entier valide.");
                }
            });

            // Ajoute tous les éléments à la VBox
            vbox.getChildren().addAll(labelNombreColons, champTexte, boutonValider, zoneColonie, boutonMenuPrincipal, labelErreur);

            // Crée la scène avec la VBox et l'affiche dans la fenêtre principale
            
            Scene scene = new Scene(vbox, 600, 600);
            
            stagePrincipal.setTitle("Initialisation de la colonie");
            
            stagePrincipal.setScene(scene);
            
            stagePrincipal.show();
        
        } else { // Si des arguments sont passés en ligne de commande, on traite la lecture d'un fichier

            VBox vbox = new VBox();
            vbox.setSpacing(10);  // Espace entre les éléments dans la VBox
            
            // Label pour afficher le message de vérification du fichier
            
            Label messageVerification = new Label("Vérification du fichier contenant la colonie");
            
            // Label pour afficher les erreurs (si un problème survient avec le fichier)
            
            Label labelErreur = new Label();
            
            labelErreur.setTextFill(Color.RED);

            // Zone de texte pour afficher les informations sur le fichier vérifié
            
            TextArea infoColonie = new TextArea();
            
            infoColonie.setEditable(false); // Désactive l'édition dans cette zone
            
            infoColonie.setWrapText(true);  // Active le retour à la ligne dans le texte

            // Bouton pour passer au menu de résolution automatique
            Button boutonMenuAffectation = new Button("Passer au menu de résolution automatique");
            boutonMenuAffectation.setVisible(false);  // Initialement caché

            try {
                // Récupère le fichier passé en argument
                File fichier = new File(getParameters().getRaw().get(0));

                // Vérifie la validité du fichier
                ParserColonie.verifierFichier(fichier);

                // Lit le contenu du fichier et crée la colonie
                Colonie maColonie = ParserColonie.lireColonie(fichier);
                
                // Affiche les informations concernant la colonie dans la zone de texte
                infoColonie.setText("Fichier vérifié avec succès, voici les informations concernant la colonie :\n" +
                        maColonie.afficherColons() + "\n" +
                        maColonie.afficherRessourceDisponible() + "\n" +
                        maColonie.afficherRelations() + "\n" +
                        maColonie.afficherPreferences());

                // Action à effectuer lors du clic sur le bouton "Passer au menu de résolution automatique"
                boutonMenuAffectation.setOnAction(even -> {
                	GuiColonieFichier.afficherMenuPrincipalResolution(stagePrincipal, maColonie);
                });

                // Rend le bouton "Passer au menu de résolution automatique" visible
                boutonMenuAffectation.setVisible(true);

            } catch (FileNotFoundException e) {
                // Si le fichier n'est pas trouvé, affiche un message d'erreur
                labelErreur.setText("Fichier non trouvé, vérifier son nom");
            } catch (IOException e) {
                // Si une erreur se produit lors de la lecture du fichier, affiche un message d'erreur
                labelErreur.setText(e.getMessage());
            } catch (FichierColonieException e) {
                // Si une erreur spécifique au fichier de colonie survient, affiche un message d'erreur
                labelErreur.setText(e.getMessage());
            }

            // Ajoute tous les éléments à la VBox
            vbox.getChildren().addAll(messageVerification, infoColonie, boutonMenuAffectation, labelErreur);

            // Crée la scène avec la VBox et l'affiche dans la fenêtre principale
            Scene scene = new Scene(vbox, 1080, 720);
            stagePrincipal.setTitle("Vérification du fichier");
            stagePrincipal.setScene(scene);
            stagePrincipal.show();
        }
    }



		
    
            
    

}

