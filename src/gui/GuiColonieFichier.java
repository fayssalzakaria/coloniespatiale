package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import colonie.ChoixInvalideException;
import colonie.ColonException;
import colonie.Colonie;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.SauvegarderColonie;

/**
 * Classe GuiColonieFichie.
 * Cette classe contient les methode qui permettent d'afficher les scene en rapport avec une colonie génerer depuis un fichier

 * @author Zakaria Fayssal

 */
public class GuiColonieFichier {

	/**
     * Affiche le menu principal pour la résolution automatique, accessible depuis un fichier.
     * Permet à l'utilisateur de choisir parmi différentes options liées à la résolution automatique de la colonie.
     * 
     * @param primaryStage le conteneur principal de l'application.
     * @param maCol la colonie sur laquelle effectuer les opérations.
     */
    public static void afficherMenuPrincipalResolution(Stage primaryStage, Colonie maCol) {
    	
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);

        Label menu = new Label("\nMenu principal :" + "\n1 - Résolution automatique\n" + "\n2 - Sauvegarder la solution actuelle\n" + "\n3 - Fin\n" + "\nVotre choix : \n");

        TextField textField = new TextField();//zone de saisie pour l'utilisateur
        
        Button boutonValider = new Button("Valider"); //bouton de validation
        
        Label labelErreur = new Label();
        
        labelErreur.setTextFill(Color.RED);

        // Action à effectuer lorsque l'utilisateur clique sur "Valider"
        
        boutonValider.setOnAction(action -> {
            try {
                int choix = Integer.parseInt(textField.getText());

                if (choix < 1 || choix > 3) {
                    labelErreur.setText("Erreur : Le nombre doit être entre 1 et 3.");
                } else {
                    switch (choix) {
                        case 1:
                            // Lancer la résolution automatique
                            afficherSceneResolutionAuto(primaryStage, maCol);
                            break;

                        case 2:
                            // Sauvegarder la solution actuelle
                            afficherSceneSauvegarde(primaryStage, maCol);
                            break;

                        case 3:
                            // Terminer le programme
                            GuiColonieManuelle.afficherSceneFinProgramme(primaryStage);
                            break;

                        default:
                            throw new ChoixInvalideException("Choisissez un nombre entre 1 et 3");
                    }
                }

            } catch (ChoixInvalideException e) {
                labelErreur.setText(e.getMessage());
            } catch (NumberFormatException ex) {
                labelErreur.setText("Erreur : Veuillez entrer un nombre valide.");
            }
        });

        // Ajout des composants à la scène
        vbox.getChildren().addAll(menu, textField, boutonValider, labelErreur);

        Scene sceneMenu = new Scene(vbox, 600, 600);
        primaryStage.setTitle("Menu Principal de Résolution Automatique");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
    }
    /**
     * Affiche la scène de sauvegarde des résultats de la colonie dans un fichier.
     * 
     * @param primaryStage le conteneur principal de l'application.
     * @param maCol la colonie à sauvegarder.
     */
    public static void afficherSceneSauvegarde(Stage primaryStage, Colonie maCol) { // Affiche la scène de sauvegarde
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);

        Label instructionLabel = new Label("Entrez le chemin ou le nom du fichier dans lequel vous voulez sauvegarder votre résolution :");
        
        TextField cheminTextField = new TextField(); //reponse de l'utilisateur (saisis du nom du fichier)
        
        Button sauvegarderBoutton = new Button("Sauvegarder");//bouton pour sauvegarder les informations dans le fichier
        
        Button retourBoutton = new Button("Retour");//retour au menu principal
        
        Label statusLabel = new Label(); // Label confirmant que la sauvegarde s'est  bien effectuée
        
        statusLabel.setTextFill(Color.GREEN);
        
        Label erreurLabel = new Label();//message d'erreur si la sauvegarde s'est mal effectuée
        
        erreurLabel.setTextFill(Color.RED);

        // Action du bouton Retour pour revenir au menu principal
        retourBoutton.setOnAction(action -> {
            afficherMenuPrincipalResolution(primaryStage, maCol);
        });
        
        retourBoutton.setVisible(false); // Le bouton retour est caché tant que la sauvegarde n'est pas effectuée
        
        // Action du bouton Sauvegarder pour tenter de sauvegarder la colonie dans un fichier
        sauvegarderBoutton.setOnAction(action -> {
            String chemin = cheminTextField.getText().trim(); // Récupère le texte entré par l'utilisateur

            try {
        
                File fichierCol = new File(chemin);
                
                SauvegarderColonie.sauvegarderColonie(maCol, fichierCol);
                
                statusLabel.setText("La colonie a été sauvegardée avec succès dans le fichier : " + chemin);
                
                sauvegarderBoutton.setVisible(false); // Cache le bouton Sauvegarder une fois l'opération réussie
                
                retourBoutton.setVisible(true); // Affiche le bouton Retour
                
            } catch (FileNotFoundException e) {
            	
                // Si le fichier n'est pas trouvé, affiche un message d'erreur
                erreurLabel.setText("Le fichier n'a pas été trouvé. Vérifiez le chemin spécifié.");
                
                sauvegarderBoutton.setVisible(false);//ne plus afficher le bouton de sauvegarde
                
                retourBoutton.setVisible(true);//afficher le bouton de retour
            } catch (IOException e) {
            	
                // Si une erreur d'entrée/sortie se produit, affiche un message d'erreur
                erreurLabel.setText("Erreur lors de la sauvegarde : " + e.getMessage());
                
                sauvegarderBoutton.setVisible(false);//ne plus afficher le bouton de sauvegarde
                
                retourBoutton.setVisible(true);//afficher le bouton de retour
            }
        });

        // Ajoute les éléments à la VBox
        vbox.getChildren().addAll(instructionLabel, cheminTextField, sauvegarderBoutton, retourBoutton, statusLabel, erreurLabel);

        Scene sauvegarderScene = new Scene(vbox, 600, 400);
        
        primaryStage.setTitle("Sauvegarder la Résolution");
        
        primaryStage.setScene(sauvegarderScene);
        
        primaryStage.show();
    }

    /**
     * Affiche la scène de résolution automatique pour minimiser le coût en jalousie.
     * 
     * @param primaryStage le conteneur principal de l'application.
     * @param maCol la colonie à résoudre.
     */
    
    public static void afficherSceneResolutionAuto(Stage primaryStage, Colonie maCol) { 
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);
        
        Button retour = new Button("Retour");//bouton de retour au menu principal
        
        retour.setVisible(false); // Le bouton Retour est caché tant que la résolution automatique n'est pas effectuée
        
        //action du bouton "Retour"
        retour.setOnAction(action -> {
            afficherMenuPrincipalResolution(primaryStage, maCol);
        });
        
        // Zones de texte pour afficher l'affectation naives des ressources 
        TextArea affectationNaiveTextArea = new TextArea();
        
        affectationNaiveTextArea.setEditable(false); // Ne peut pas être modifié par l'utilisateur
        
        affectationNaiveTextArea.setWrapText(true); // activer le retour a la ligne

        TextArea minimisationTextArea = new TextArea();// Zone de texte pour afficher les information apres la minimisation
        
        minimisationTextArea.setEditable(false); //la zone de texte n'est pas modifiable
        
        minimisationTextArea.setWrapText(true);// retour a la linge automatique
        

        Label erreurLabel = new Label();//label affichant les messages d'erreurs
        
        erreurLabel.setTextFill(Color.RED); 
        
        try {
            // Affectation initiale des ressources et affichage des informations
            maCol.affecterRessources();
            
            affectationNaiveTextArea.setText("Affectation naive des ressources effectuée avec succès, voici les affectations :"
                    + "\n" + maCol.afficherAffectation() 
                    + "\nCoût en jalousie : " + maCol.calculerColonsJaloux() 
                    + "\n" + maCol.afficherJaloux());
            
            // Minimisation du coût en jalousie et affichage des informations
            maCol.minimiserJalousie(maCol.getColonie().size() * 10);
            
            minimisationTextArea.setText("Minimisation du nombre de colons jaloux effectuée avec succès, voici les affectations :"
                    + "\n" + maCol.afficherAffectation()
                    + "\nCoût en jalousie : " + maCol.calculerColonsJaloux() 
                    + "\n" + maCol.afficherJaloux());
            
            retour.setVisible(true); // Affiche le bouton Retour après l'opération
        } catch (ColonException e) {
            erreurLabel.setText(e.getMessage()); // Affiche un message d'erreur si une exception se produit
            retour.setVisible(true);// affiche le bouton de retour au menu si erreur
        }

        vbox.getChildren().addAll(affectationNaiveTextArea, minimisationTextArea, erreurLabel, retour);//ajouter les element a la vbox
        
        Scene resolScene = new Scene(vbox, 600, 600);
        
        primaryStage.setTitle("Menu Principal de résolution automatique");
        
        primaryStage.setScene(resolScene);
        
        primaryStage.show();
    }
}
