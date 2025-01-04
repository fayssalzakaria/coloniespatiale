package gui;

import colonie.ChoixInvalideException;
import colonie.Colon;
import colonie.ColonException;
import colonie.ColonPreferencesException;
import colonie.Colonie;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Classe GuiColonieManuelle.
 * Cette classe contient les methode qui permettent d'afficher les scene en rapport avec la saisie manuelle de la colonie

 * @author Zakaria Fayssal

 */

public class GuiColonieManuelle {
	
	  /**
     * Affiche le menu principal pour gérer les relations et préférences des colons.
     * Ce menu permet à l'utilisateur de choisir différentes options pour manipuler la colonie.
     * 
     * @param primaryStage le conteneur principal de l'application.
     * @param maCol la colonie sur laquelle effectuer les opérations.
     */
	
    public static void afficherMenuPrincipal(Stage primaryStage, Colonie maCol) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Label label = new Label("\nMenu principal :" + "\n1 - Ajouter une relation entre deux colons\n" + "\n2 - Ajouter les préférences d'un colon\n" + "\n3 - Fin\n" + "\nVotre choix : \n");

        TextField textField = new TextField();//zone de saisie pour l'utilisateur
        
        Button boutonValider = new Button("Valider");//bouton de validation pour l'utilisateur
        
        Label labelErreur = new Label();//label pour afficher les erreurs
        
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
                            // Afficher l'écran pour ajouter une relation
                            afficherSceneAjoutRelation(primaryStage, maCol);
                            break;

                        case 2:
                            // Afficher l'écran pour ajouter des préférences
                            afficherSceneAjoutPrefs(primaryStage, maCol);
                            break;

                        case 3:
                            // Afficher l'ecran pour verifier les préférences existantes
                            afficherSceneVerifPref(primaryStage, maCol);
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
        vbox.getChildren().addAll(label, textField, boutonValider, labelErreur);

        Scene sceneMenu = new Scene(vbox, 600, 600);
        
        primaryStage.setTitle("Menu Principal");
        
        primaryStage.setScene(sceneMenu);
        
        primaryStage.show();
    }
    
    /**
     * Affiche le second menu d'affectation avec les choix d'options pour l'utilisateur.
     * Permet à l'utilisateur de choisir entre échanger des ressources, afficher le nombre de colons jaloux,
     * ou de finir le programme.
     * 
     * @param primaryStage La scène principale de l'application.
     * @param maCol L'objet de type Colonie contenant les colons et leurs relations.
     */
    
    public static void afficherSecondMenu(Stage primaryStage, Colonie maCol) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Label label = new Label("\nMenu affectation :" + "\n1 - Échanger les ressources de deux colons\n" + "\n2 - Afficher le nombre de colons jaloux\n" + "\n3 - Fin\n" + "\nVotre choix : \n");

        TextField textField = new TextField();//zone de saisie pour l'utilisateur
        
        Button boutonValider = new Button("Valider");//bouton de validation
        
        Label labelErreur = new Label();//label qui affiche les erreurs
        
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
                            // Afficher l'écran pour échanger des ressources
                            afficherSceneEchangeRess(primaryStage, maCol);
                            break;

                        case 2:
                            // Afficher l'écran pour les colons jaloux
                            afficherSceneColonsJaloux(primaryStage, maCol);
                            break;

                        case 3:
                            // Terminer le programme
                            afficherSceneFinProgramme(primaryStage);
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
        vbox.getChildren().addAll(label, textField, boutonValider, labelErreur);

        Scene sceneMenu = new Scene(vbox, 600, 600);
        primaryStage.setTitle("Menu Affectation");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
    }
    
    /**
     * Affiche une scène permettant d'ajouter une relation entre deux colons spécifiés par leur nom.
     * Permet de saisir les noms des deux colons et de les associer.
     * Affiche un message de validation ou d'erreur en fonction de l'opération.
     * 
     * @param primaryStage La scène principale de l'application.
     * @param maCol L'objet de type Colonie contenant les colons et leurs relations.
     */
    
    public static void afficherSceneAjoutRelation(Stage primaryStage, Colonie maCol) {
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);
        
        Label labelCol1 = new Label("Nom du premier colon :");
        
        TextField nom1Field = new TextField();//zone de saisie du premier colon
        
        vbox.getChildren().addAll(labelCol1, nom1Field);       
        
        Label labelCol2 = new Label("Nom du second colon :");
        
        TextField nom2Field = new TextField();//zone de saisie du deuxieme colon
        
        vbox.getChildren().addAll(labelCol2, nom2Field);
        
        Label validationLabel = new Label();//label confirmant la resussite de la saisie de la relation
        
        validationLabel.setTextFill(Color.GREEN);
        
        vbox.getChildren().add(validationLabel);

        Label erreurLabel = new Label();//label affichant les erreurs
        
        erreurLabel.setTextFill(Color.RED);
        
        vbox.getChildren().add(erreurLabel);
        
        Button retour = new Button("Retour");//bouton de retour au menu principal
        
        retour.setVisible(false); // Le bouton retour est caché tant qu'une relation n'est pas ajoutée
        
        retour.setOnAction(event -> {
            afficherMenuPrincipal(primaryStage, maCol);
        });
        
        // Action pour ajouter la relation entre deux colons
        
        Button  ajouterRelationBoutton = new Button("Ajouter la relation");
        
        //action du bouton "ajouterBoutton"
        ajouterRelationBoutton.setOnAction(action -> {
            String nom1 = nom1Field.getText().trim(); // Récupère le nom du premier colon
            String nom2 = nom2Field.getText().trim(); // Récupère le nom du second colon

            try {
                // Recherche des colons par leur nom
            	
                Colon col1 = maCol.trouverColonParNom(nom1);
                
                Colon col2 = maCol.trouverColonParNom(nom2);

                // Ajoute la relation entre les deux colons
                maCol.ajouterRelation(col1, col2);
                
                validationLabel.setText("Relation ajoutée entre le colon " + nom1 + " et le colon " + nom2);//confirme l'ajout reussit
                
                ajouterRelationBoutton.setVisible(false); // Cache le bouton d'ajout une fois la relation ajoutée
                
                retour.setVisible(true); // Affiche le bouton de Retour
                
            } catch (ColonException e) {
            	
            	ajouterRelationBoutton.setVisible(false); // Cache le bouton d'ajout en cas d'erreur
            	
                retour.setVisible(true); //affiche le bouton de retour au menu principal
                
                erreurLabel.setText(e.getMessage()); // Affiche l'erreur
            }
        });

        vbox.getChildren().addAll( ajouterRelationBoutton, retour);//ajout des bouton a la vbox

        Scene scene = new Scene(vbox, 600, 600);
        
        primaryStage.setTitle("Ajouter une relation");
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    /**
     * Affiche une scène permettant d'ajouter les préférences d'un colon.
     * Permet à l'utilisateur d'entrer un nom de colon suivi des ressources qu'il préfère.
     * Affiche un message de validation ou d'erreur selon le cas.
     * 
     * @param primaryStage La scène principale de l'application.
     * @param maCol L'objet de type Colonie contenant les colons et leurs préférences.
     */
    
    public static void afficherSceneAjoutPrefs(Stage primaryStage, Colonie maCol) {
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);

        Label label = new Label("Entrer le nom du colon suivi de ses préférences (ex : A 1 2 3) :");
        
        TextField preferencesField = new TextField();//zone de saisie des preferences pour l'utilisateur
        
        Label preferencesLabel = new Label();//label qui affiche les preferences saisies si elle sont valide
        
        preferencesLabel.setTextFill(Color.GREEN);
        
        Label erreurLabel = new Label();//label affichant les erreurs
        
        erreurLabel.setTextFill(Color.RED);
        
        vbox.getChildren().addAll(label, preferencesField, preferencesLabel, erreurLabel);
        
        Button retour = new Button("Retour"); //boutond e retour au menu principal
        
        retour.setVisible(false); // Le bouton Retour est caché tant que les préférences ne sont pas ajoutées
        
        retour.setOnAction(action -> afficherMenuPrincipal(primaryStage, maCol));
       
        // Bouton pour ajouter les preferences
        Button ajouterPrefsBoutton = new Button("Ajouter les préférences");
        
        ajouterPrefsBoutton.setOnAction(action -> {
            String phrase = preferencesField.getText().trim(); // Récupère la phrase entrée par l'utilisateur
            
            String[] mots = phrase.split("\\s+");  // Sépare les mots, même s'il y a plusieurs espaces
            
            String nom = mots[0];  // Le nom du colon est le premier mot
            
            try {
                Colon colon = maCol.trouverColonParNom(nom); // Recherche le colon par son nom
                
                colon.setPreferences(mots, maCol.getRessourceDispo()); // Associe les préférences au colon
                
                preferencesLabel.setText("Les préférences du colon " + nom + " ont été bien prises en compte\n" + colon.afficherPreferences());
                
                ajouterPrefsBoutton.setVisible(false); // Cache le bouton d'ajout une fois l'ajout effectué
                
                retour.setVisible(true); // Affiche le bouton de retour au menu principal
                
            } catch (ColonException e) {
            	
            	ajouterPrefsBoutton.setVisible(false); // Cache le bouton d'ajout en cas d'erreur
                
                erreurLabel.setText(e.getMessage()); // Affiche l'erreur
                
                retour.setVisible(true); // Affiche le bouton Retour
            } 
        });

        vbox.getChildren().addAll(ajouterPrefsBoutton, retour);

        Scene scene = new Scene(vbox, 600, 600);
        
        primaryStage.setTitle("Ajouter les préférences");
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    /**
     * Affiche une fenetre permettant de verifier les preferences des colons et d'afficher l'affectation des ressources.
     * Verifie les preferences et affecte les ressources en fonction.
     * Affiche les resultats dans des zones de texte et permet de revenir au menu d'affectation.
     * 
     * @param primaryStage La scene principale de l'application.
     * @param maCol L'objet de type Colonie contenant les colons et leurs preferences.
     */
    
    public static void afficherSceneVerifPref(Stage primaryStage, Colonie maCol) {
        
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);

        Label preferencesLabel = new Label("Cliquez en dessous pour verifier les preferences des colons :\n");
        
        TextArea preferencesTextArea = new TextArea();//zone de textes affichant les preferences des colons
        
        preferencesTextArea.setEditable(false); // zone de texte non modfiable
        
        preferencesTextArea.setWrapText(true); //retour a la ligne automatique
        
        TextArea affectTextArea = new TextArea();//zone de texte affichant les affectations
               
        affectTextArea.setEditable(false); // zone de texte non modfiable

        Label validationPrefLabel = new Label(); //zone de texte quii confirme la validation des preferences
        
        validationPrefLabel.setTextFill(Color.GREEN);
        
        Label validationAffectLabel = new Label(); //zone de texte quii confirme la validation des affectations
        
        validationAffectLabel.setTextFill(Color.GREEN);
        
        Button secondMenuBoutton = new Button("Aller au second menu");//bouton pour allez au second menu
        
        secondMenuBoutton.setVisible(false);//cacher au depart ce bouton
        
        secondMenuBoutton.setOnAction(action->afficherSecondMenu(primaryStage, maCol));//action du bouton(afficher le second meu)
    
        Button verifierPrefsBoutton = new Button("Verifier les preferences");//boutt on de verification des preferences saisis
        
        Button retour = new Button("Retour");//bouton pour retourner au menu principal
        
        retour.setVisible(false);
        
        retour.setOnAction(action->afficherMenuPrincipal(primaryStage, maCol));//action du bouton "retour"
        //action du bouton de verification
        verifierPrefsBoutton.setOnAction(action -> {
            
            try {
                
                maCol.verifierPreferences(); //verifier les preferences
                
                validationPrefLabel.setText("Les preferences ont bien ete verifiees.");
                
                preferencesTextArea.setText(maCol.afficherPreferences());  

                maCol.affecterRessources(); //affectation naive des ressources
                
                validationAffectLabel.setText("Les ressources ont bien ete affectees.");
                
                affectTextArea.setText(maCol.afficherAffectation());  
                
                verifierPrefsBoutton.setVisible(false);//cacher le bouton de verification
                
                secondMenuBoutton.setVisible(true);//afficher le bouton pour allez au second menu

            } catch (ColonPreferencesException e) {
                
                preferencesTextArea.setStyle("-fx-text-fill: red; "); // si il y a une erreur, on affiche l'erreur en rouge
                
                preferencesTextArea.setText(e.getMessage());
                
                verifierPrefsBoutton.setVisible(false); //cacher le bouton de verification
                
                retour.setVisible(true); //afficher le bouton de retour au menu principal
                
            }
            
        });
        //ajouter les composants a la vbox
        vbox.getChildren().addAll(preferencesLabel,verifierPrefsBoutton, validationPrefLabel,preferencesTextArea,validationAffectLabel,affectTextArea, secondMenuBoutton,retour );

        Scene scene = new Scene(vbox, 600, 600);
        
        primaryStage.setTitle("Verification des preferences");
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    /**
     * Affiche une fenetre permettant d'echanger les ressources entre deux colons specifies par leur nom.
     * Permet de saisir les noms des colons, de proceder a l'echange de ressources et affiche un message de validation
     * ou d'erreur en fonction du resultat de l'operation.
     * 
     * @param primaryStage La scene principale de l'application.
     * @param maCol L'objet de type Colonie contenant les colons et leurs ressources.
     */
    
    public static void afficherSceneEchangeRess(Stage primaryStage, Colonie maCol) {
        
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);

        Label labelCol1 = new Label("Nom du premier colon :");
        
        TextField nomCol1Field = new TextField();//zone de saisie du premier colon
        
        vbox.getChildren().addAll(labelCol1, nomCol1Field);

        Label labelCol2 = new Label("Nom du second colon :");
        
        TextField nomCol2Field = new TextField(); //zone de saisie du deuxieme colon
        
        vbox.getChildren().addAll(labelCol2, nomCol2Field);

        Label validationLabel = new Label(); //label qui confirme l'echange des ressources
        
        validationLabel.setTextFill(Color.GREEN);
        
        Label erreurLabel = new Label();//label affichant les erreurs
        
        erreurLabel.setTextFill(Color.RED);
        
        vbox.getChildren().addAll(validationLabel, erreurLabel);
        
        Button retour = new Button("Retour");//bouton de retour au second menu
        
        retour.setVisible(false);//cacher le bouton de retour
        
        retour.setOnAction(action->afficherSecondMenu(primaryStage, maCol));//action du bouton retour
        
        Button echangerBoutton = new Button("Echanger les ressources");//bouton d'echange des ressources
        //action du bouton d'echange
        echangerBoutton.setOnAction(action -> {
            
            String nom1 = nomCol1Field.getText().trim();
            
            String nom2 = nomCol2Field.getText().trim();

            try {
                
                Colon colon1 = maCol.trouverColonParNom(nom1);//trouver le premier colon
                
                Colon colon2 = maCol.trouverColonParNom(nom2);//trouver le deuxieme colon

                maCol.echangerRessources(colon1, colon2);//echanger les ressources
                
                validationLabel.setText("Echange effectue entre " + nom1 + " et " + nom2);
                
                echangerBoutton.setVisible(false);//cacher le bouton d'echange
                
                retour.setVisible(true); //afficher le bouton de retour
                
            } catch (ColonException e) {
                
                erreurLabel.setText(e.getMessage());
                
                echangerBoutton.setVisible(false);//cacher le bouton d'echange
                
                retour.setVisible(true); //afficher le bouton de retour
            }
            
        });

        vbox.getChildren().addAll(echangerBoutton, retour);

        Scene scene = new Scene(vbox, 600, 600);
        
        primaryStage.setTitle("Echanger des ressources");
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    /**
     * Affiche une fenetre affichant le nombre de colons jaloux et une liste des colons jaloux.
     * Affiche le nombre de colons jaloux et les details associes, et permet de revenir au menu d'affectation.
     * 
     * @param primaryStage La scene principale de l'application.
     * @param maCol L'objet de type Colonie contenant les colons.
     */
    
    public static void afficherSceneColonsJaloux(Stage primaryStage, Colonie maCol) {
        
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);
        
        TextArea resultTextArea = new TextArea();  //text area pour afficher le nombre de colon jaloux ainsi que les colons jaloux
        
        resultTextArea.setEditable(false);  //zone non modifiable
        
        resultTextArea.setWrapText(true); //retour a la ligne automatique
        
        resultTextArea.setText("Nombre de colons jaloux :"+"\n"+maCol.calculerColonsJaloux() + " colons jaloux."+"\n"+maCol.afficherJaloux());
        
        vbox.getChildren().add(resultTextArea);
        
        Button retourButton = new Button("Retour");//bouton de retour au second menu
        
        retourButton.setOnAction(e -> afficherSecondMenu(primaryStage, maCol));

        vbox.getChildren().add(retourButton);

        Scene scene = new Scene(vbox, 600, 600);
        
        primaryStage.setTitle("Colons Jaloux");
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    /**
     * Affiche une fenetre indiquant que le programme est termine et offre un bouton pour fermer l'application.
     * 
     * @param primaryStage La scene principale de l'application.
     */
    
    public static void afficherSceneFinProgramme(Stage primaryStage) {
        
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);

        Label label = new Label("Programme termine. Merci d'avoir utilise l'application !");
        
        vbox.getChildren().add(label);

        Button fermerBoutton = new Button("Fermer");//bouton pour fermer l'application
        
        fermerBoutton.setOnAction(e -> primaryStage.close());

        vbox.getChildren().add(fermerBoutton);

        Scene scene = new Scene(vbox, 600, 600);
                
        primaryStage.setTitle("Fin du Programme");
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
}
