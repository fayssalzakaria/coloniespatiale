package colonie;

import java.util.InputMismatchException;
import java.util.Scanner;

import gui.GuiApp;
import terminal.TerminalApp;

/**
 * La classe Main est le point d'entrée de l'application de gestion de colonie.
 * Elle permet à l'utilisateur de choisir entre lancer l'application en mode terminal ou avec une interface graphique.

 * @author Zakaria Fayssal

 */
public class Main {
    
    /**
     * Méthode principale du programme. Elle affiche un menu et permet à l'utilisateur de choisir entre
     * exécuter l'application dans le terminal ou avec une interface graphique.
     * 
     * Si l'utilisateur entre un choix valide (1 ou 2), l'application démarre dans le mode choisi.
     * Si un choix invalide est saisi, un message d'erreur est affiché et l'utilisateur doit recommencer.
     * 
     * @param args Arguments de la ligne de commande (non utilisés dans cette classe).
     */
    public static void main(String[] args) {
    	
        Scanner scan = new Scanner(System.in);
        
        boolean choixValide = false; // Indique si le choix de l'utilisateur est valide
        
        System.out.println("Bienvenue dans l'application de gestion de colonie");
        
        while (!choixValide) {
        	
            System.out.println("1 - Lancez l'application dans le terminal");
            
            System.out.println("2 - Lancez l'application dans une interface graphique");
            
            try {
            	
                int choix = scan.nextInt(); // Lit le choix de l'utilisateur
                
                switch (choix) {
                
                    case 1:
                    	
                        TerminalApp.start(args); // Lancement dans le terminal
                        
                        choixValide = true; // Sortir de la boucle
                        
                        break;
                        
                    case 2:
                    	
                        GuiApp.launch(GuiApp.class, args); // Lancement du programme dans l'interface graphique
                        
                        choixValide = true; 
                        
                        break;
                        
                    default:
                    	
                        throw new ChoixInvalideException("Choix incorrect. Veuillez choisir un entier entre 1 et 2.");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Saisissez un entier valide.");
                scan.nextLine(); 
            } catch (ChoixInvalideException e) {
                System.out.println(e.getMessage());
            }
        }
        
        scan.close(); 
    }
}
