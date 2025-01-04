package colonie;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;


/**
 * La classe Colonie représente une colonie de colons, gère les relations entre eux, l'affectation des ressources, et calcule la jalousie dans la colonie.
 * Elle permet de manipuler des colons, leurs relations, ainsi que l'affectation et l'échange de ressources.
 * 
 * @author Zakaria Fayssal
 */

public class Colonie {
    
    private List<Colon> colonie; // Liste de Colon représentant la colonie
    
    private Map<Colon,List<Colon>> relations;//map représentant les relations des colons, en clé le colon et en valeurs la liste des colons "ennemis" de celui-ci
    
    private List<String> ressourcesDispo;//Liste des ressources disponibles au départ dans la colonie
    
    /**
     * Constructeur de la colonie.
     * Crée une colonie avec un nombre spécifié de colons. Les colons sont nommés par les lettres de l'alphabet.
     * Les ressources sont assignées aux colons selon un ordre.
     * 
     * @param n Le nombre de colons dans la colonie
     */
    
    public Colonie(int n) {
    	//initialiser les attributs
        this.colonie = new ArrayList<>();
        
        this.relations = new HashMap<>();
        
        this.ressourcesDispo = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
        	
            String name = String.valueOf((char) ('A' + i)); // ajoute les colons avec pour nom les lettres de l'alphabet
            
            Colon colonCourant = new Colon(name);//Colon courrant
            
            colonie.add(colonCourant);
            
            relations.put(colonCourant, new ArrayList<>()); // initialise les relations
            
            ressourcesDispo.add(String.valueOf(i+1));  //ajouter la valeur en String de i en ressource
            
        }
    }
    
    /**
     * Constructeur par défaut de la colonie.
     * Crée une colonie vide.
     */
    
    public Colonie() {
    	
    	this.colonie = new ArrayList<>();
    	
        this.relations = new HashMap<>();
        
        this.ressourcesDispo = new ArrayList<>();
        
    }
    
    /**
     * Ajoute une relation entre deux colons.
     * 
     * @param c1 Le premier colon
     * @param c2 Le deuxième colon
     * @throws ColonException Si la relation existe déjà ou si les colons sont identiques
     */
    
    public void ajouterRelation(Colon c1, Colon c2) throws ColonRelationsException {

        if (relations.get(c1).contains(c2)) {
        	
        	throw new ColonRelationsException("Erreur : relation deja existante entre "+c1.getNom()+" et "+c2.getNom());
        }
        
        if(c1.equals(c2)) {
        	throw new ColonRelationsException("Erreur : un meme colon ne peu avoir une relation avec lui meme");
        }
        
        	relations.get(c1).add(c2);
            relations.get(c2).add(c1);
    }
    
    /**
     * Trouve un colon par son nom.
     * 
     * @param nom Le nom du colon à trouver
     * @return Le colon correspondant au nom
     * @throws ColonAbsentException Si le colon n'existe pas dans la colonie
     */
    
    public Colon trouverColonParNom(String nom) throws ColonAbsentException { //trouver un colon de part son nom
    	
        for (Colon colon : colonie) {
        	
            if (colon.getNom().equalsIgnoreCase(nom)) {//verifier que le colon est present en verifiant que les nom correspondent (en ignorant la case)
            	
                return colon;
            }
            
        }
        
        throw new ColonAbsentException("Erreur : le colon avec le nom " + nom + " n'existe pas dans la colonie.");
        
    }
    
    /**
     * Affecte les ressources disponibles aux colons en fonction de leurs préférences. (affectation naive)
     * Les colons reçoivent la première ressource disponible dans leur liste de préférences.
     */
    
    public void affecterRessources() {
    	
        for (Colon colon : colonie) {
        	
            for (String pref : colon.getPreferences()) {
            	
                if (ressourcesDispo.contains(pref)) {
                	
                    colon.setRessource(pref);
                    
                    ressourcesDispo.remove(pref); // retirer la ressource de la liste des disponibles
                    
                    break; // Sortir apres avoir trouve une ressource
                    
                }
                
            }
            
        }
        
    }
    
    /**
     * Effectue un échange de ressources entre deux colons.
     * 
     * @param col1 Le premier colon
     * @param col2 Le deuxième colon
     * @throws ColonException Si l'un des colons n'est pas présent dans la colonie ou si les colons sont identiques
     */
    
    public void echangerRessources(Colon col1,Colon col2) throws ColonException{ //echange une ressource entre 2 colons(aven en parametre des colons)
    
    	if(!colonie.contains(col1)) {
    		
    		throw new ColonAbsentException("Erreur : le colon "+col1.getNom()+" n'est pas present dans la colonie");
    		
    	}else if(!colonie.contains(col2)) {
    		
    		throw new ColonAbsentException("Erreur :le colon "+col2.getNom()+" n'est pas present dans la colonie");
    		
    	}
    	
    	if(col1.equals(col2)) {
    		
    		throw new ColonException("Erreur :la ressource du colon "+col1.getNom()+" est echanger avec lui meme");
    		
    	}
        String ressource1 = col1.getRessource();
        
        String ressource2 = col2.getRessource();

        col1.setRessource(ressource2);
        
        col2.setRessource(ressource1);
      
    }
    
    /**
     * Calcule le nombre de colons jaloux dans la colonie.
     * Un colon est jaloux s'il est dans la liste des ennemis d'un autre colon et qu'il désire la ressource de ce colon.
     * 
     * @return Le nombre de colons jaloux dans la colonie
     */
    
    public int calculerColonsJaloux() { // calcul le cout en jalousie dans la colonie
    	
        int jalouxCount = 0;
        
        for (Colon colon : colonie) {
        	
            for (Colon ennemi : relations.get(colon)) {
            	
                    if (colon.estPotentiellementJalou(ennemi)) {//si le colon est jalou de 1 de ses ennemi, on augmente le compteur de jaloux
                    	
                    jalouxCount++;
                    
                    break; 
                    
                }
                    
            }
            
        }
        
        return jalouxCount;
        
    }
    
    /**
     * Minimiser la jalousie dans la colonie en effectuant des échanges de ressources entre colons.
     * Les échanges sont effectués pendant un nombre maximal d'itérations, jusqu'à ce que la jalousie soit minimisée.
     * 
     * @param iterationsMax Le nombre maximal d'itérations à effectuer
     * @throws ColonException Si un problème survient lors de l'échange de ressources
     */
    
    public void minimiserJalousie(int iterationsMax) throws ColonException {
        
    	affecterRessources(); //affecation initiale
    	
        int coutActuel = calculerColonsJaloux(); //cout actuel de la colonie
      
        
        int echangesSansAmelioration = 0; //Compteur du nombre d'echanges sans amelioration du cout
        
        for (int iteration = 0; iteration < iterationsMax; iteration++) {
        	
            // Calculer les paires qui donnent le meilleurs gain possible
        	
            List<int[]> meilleuresPaires = new ArrayList<>();
            
            int meilleurGain = 0;//initialiser le meilleur gain

            for (int i = 0; i < colonie.size(); i++) { //parcourir tout les couples de colons possibles
            	
                for (int j = i + 1; j < colonie.size(); j++) {
                	
                    Colon colon1 = colonie.get(i);
                    
                    Colon colon2 = colonie.get(j);
                    
                    // Simuler un échange
                    
                    echangerRessources(colon1, colon2);
                    
                    int nouveauCout = calculerColonsJaloux();//cout apres le nouvelle echange
                    
                    int gain = coutActuel - nouveauCout;//calculer le gain de l'echange
                    
                    if (gain > meilleurGain) { //regarder si le gain trouvé est superieur au meilleur gain
                    	
                        meilleuresPaires.clear(); //supprimer les anciennes "meilleures paires" car une nouvelle meilleur paire a ete trouvé
                        
                        meilleuresPaires.add(new int[]{i, j});
                        
                        meilleurGain = gain; //affecter le meilleur gain au gain trouvé
                        
                    } else if (gain == meilleurGain) {//si la paire possede un gain egale au meilleure gain, l'ajouter dans la liste 
                    	
                        meilleuresPaires.add(new int[]{i, j});
                        
                    }

                    // Annuler l'échange
                    
                    echangerRessources(colon1, colon2);
                    
                }
            }

            // Parcourir les meilleures paire et faire des echanges en verifiant qu'un gain a ete trouve
            
            int i=0;
            
            while (i < meilleuresPaires.size()) {
            	
                int[] meilleurPair = meilleuresPaires.get(i);
                
                Colon colon1 = colonie.get(meilleurPair[0]);
                
                Colon colon2 = colonie.get(meilleurPair[1]);

                // Effectuer l'échange et vérifier si le coût diminue
                
                echangerRessources(colon1, colon2);
                
                int nouveauCout = calculerColonsJaloux();
                
                if (nouveauCout < coutActuel) {
                	
                    coutActuel = nouveauCout; 
                    
                    echangesSansAmelioration = 0;
                    
                } else {
                	
                    // Revenir à l'état précédent si aucun gain n'est obtenu
                	
                    echangerRessources(colon1, colon2);
                    
                    echangesSansAmelioration++;
                }
                
                i++;
            }

            
           if (echangesSansAmelioration>10) {// si le programme stagne , on fait des echanges entre des colons qui soit font maintiennent le meme cout soit l'ameliore afin de ne pas rester bloquer
        	   
        	   	reduireStagnation();
                
                coutActuel = calculerColonsJaloux(); //recalculer le cout actuel
                
                echangesSansAmelioration = 0;//"reset" le nombre d'echanges sans ameliorations
            }
           
           if(echangesSansAmelioration>colonie.size()*5) { //si le programme stagne trop, on sort plus tot
        	
        	   return;
           }
        }

 
    }
    
    /**
     * Effectue des échanges de ressources entre des colons qui soit font stagner le coux soit l'ameliore pour tenter de réduire le nombre de colons jaloux.
     * 
     * @throws ColonException Si un problème survient lors de l'échange de ressources
     */
    
    private void reduireStagnation() throws ColonException {
    
    	int coutActuel=calculerColonsJaloux(); //calcul du cout actuel

        for (int i = 0; i < colonie.size(); i++) {//parcourir les couples de colons
        	
            for (int j = i + 1; j < colonie.size(); j++) {
            	
                Colon colon1 = colonie.get(i);
                
                Colon colon2 = colonie.get(j);
                
        		this.echangerRessources(colon1, colon2);
        			
        		int nouveauCout=calculerColonsJaloux();//calculer le cout apres echange
        			
        		if(nouveauCout>coutActuel) {//si le nouveau cout est superieur au cout actuel on annule l'echange
        				
        				this.echangerRessources(colon1,colon2);
        		}
        	}
            
        }
    }
    
    /**
     * Vérifie si tous les colons ont des préférences définies.
     * 
     * @throws PreferencesException Si un ou plusieurs colons n'ont pas de préférences définies
     */
    
    public void verifierPreferences() throws  ColonPreferencesException { 

    	StringBuilder colonsSansPref=new StringBuilder();
    	
    	boolean colonSanPref=false;// boolean pour verifier si il y'a un colon sans preferences
    	
        for (Colon colon : this.colonie) {
        	
            if (colon.getPreferences().isEmpty()) {
            	
                colonsSansPref.append("\nLe colon "+colon.getNom()+" ne possedent pas de preferences\n");
                
                colonSanPref=true;
            }  
        }
        
        if(colonSanPref==true) {
        	
        	throw new  ColonPreferencesException("Des colons n'ont pas de preferences, affectation imposible"+colonsSansPref.toString());
        }
    }
    
    /**
     * Affiche l'affectation des ressources à chaque colon.
     * 
     * @return Une chaîne de caractères représentant l'affectation des ressources
     */
    
    public String afficherAffectation() {
    	
    	StringBuilder build =new StringBuilder();
    	
    	build.append("Voici les colons suivi de leur ressources : \n");
   
        for (Colon colon : colonie) {
        	
        	build.append(colon.getNom() + ":" + colon.getRessource()+"\n");
          
        }
        
        return build.toString();
        
    }

    /**
     * Affiche les ressources disponibles dans la colonie.
     * 
     * @return Une chaîne de caractères représentant les ressources disponibles
     */
    
    public String afficherRessourceDisponible() {
    	
        StringBuilder build = new StringBuilder("Voici les ressources disponibles dans la colonie :\n");

        build.append("[");
      
        for (int i = 0; i < ressourcesDispo.size(); i++) {
            build.append(ressourcesDispo.get(i));
            
            if (i < ressourcesDispo.size() - 1) {
                build.append(", ");
            }
        }

        // Ajout de la fermeture du tableau
        build.append("]");
        
        return build.toString();
    }
    
    /**
     * Affiche les colons de la colonie.
     * 
     * @return Une chaîne de caractères représentant les colons
     */
    
    public String afficherColons() {
        StringBuilder build = new StringBuilder("Voici la liste des colons:\n");

        build.append("[");
      
        for (int i = 0; i < colonie.size(); i++) {
            build.append(colonie.get(i).getNom());
            
            if (i < colonie.size() - 1) {
                build.append(", ");
            }
        }

        // Ajout de la fermeture du tableau
        build.append("]");
        
        return build.toString();
    }
    
    /**
     * Affiche les preference des colons de la colonie.
     * 
     * @return Une chaîne de caractères représentant les preference de chaque colons
     */
    
    public String afficherPreferences() {
    	StringBuilder build=new StringBuilder("Voici les colons suivis de leurs preference par ordre decroissant :\n");
    	for(Colon col : colonie) {
    		build.append(col.afficherPreferences()+"\n");
    	}
    	return build.toString();
    }
    
    /**
     * Affiche les relations des colons.
     * 
     * @return Une chaîne de caractères représentant les relation de chaque colons
     */
    
    public String afficherRelations() {
        StringBuilder build = new StringBuilder("Voici les colons suivis des colons qu'ils n'aiment pas :\n");

        for (Colon colon : colonie) {
            build.append(colon.getNom()).append(" n'aime pas : ");
            
            List<Colon> ennemis = relations.get(colon); 
            if (ennemis != null && !ennemis.isEmpty()) {
                for (int i = 0; i < ennemis.size(); i++) {
                    build.append(ennemis.get(i).getNom());
                    if (i < ennemis.size() - 1) {
                        build.append(", ");
                    }
                }
            } else {
                build.append("personne");
            }
            build.append("\n");
        }

        return build.toString();
    }
    
    /**
     * Affiche les colons jaloux.
     * 
     * @return Une chaîne de caractères représentant les colons et les colons auquel ils ressentent de la jalousie 
     */
    
    public String afficherJaloux() {
    	
    	StringBuilder build=new StringBuilder("Voici les colons qui sont jaloux d'autres colons :\n");
    	
    	String retour=null;
    	
    	boolean jalouxCol=false;
    	
        for (Colon colon : colonie) {
        	
            for (Colon ennemi : relations.get(colon)) {            	
            	
                    if (colon.estPotentiellementJalou(ennemi)) {
                    	
                    	build.append("le colon "+colon.getNom()+ " est jaloux de "+ennemi.getNom()+"\n");
                    	
                    	jalouxCol=true;
                    	
                    	 break; 
                     
                    }
            }
        }
        
        if (jalouxCol==false) {
        	
        	retour ="Aucun colon est jaloux";
        	
        }
        
        else {
        	
        	retour=build.toString();
        	
        }
        
        return retour;
    }
   
    /**
     * Retourne la liste des colons présents dans la colonie.
     * 
     * @return La liste des colons de la colonie
     */
    
    public List<Colon> getColonie() {
        return colonie;
    }

    /**
     * Retourne la liste des ressources disponibles dans la colonie.
     * 
     * @return La liste des ressources disponibles
     */
    public List<String> getRessourceDispo() {
        return this.ressourcesDispo;
    }

    /**
     * Définit les ressources disponibles dans la colonie.
     * 
     * @param ressourcesDispo La nouvelle liste des ressources disponibles
     */
    
    public void setRessourcesDispo(List<String> ressourcesDispo) {
        this.ressourcesDispo = ressourcesDispo;
    }

    /**
     * Définit les relations entre les colons.
     * 
     * @param relations La nouvelle map de relations entre les colons
     */
    
    public void setRelations(Map<Colon, List<Colon>> relations) {
        this.relations = relations;
    }

    /**
     * Retourne la map des relations entre les colons.
     * 
     * @return La map des relations, où chaque colon est associé à une liste d'ennemis
     */
    public Map<Colon, List<Colon>> getRelations() {
        return relations;
    }

    
}
