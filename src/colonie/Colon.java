package colonie;

import java.util.*;

/**
 * Représente un colon dans une colonie.
 * Le colon a un nom, une liste de préférences pour les ressources et une ressource attribuée.
 * Il peut être jaloux d'un autre colon en fonction de l'ordre de ses préférences.

 * @author Zakaria Fayssal

 */
public class Colon {
	
    private String nom; //nom du colom
    private List<String> preferences;//Liste de string contenant les préférences des colons
    private String ressource;//ressource du colon

    /**
     * Constructeur de la classe Colon.
     * 
     * @param nom Le nom du colon.
     */
    public Colon(String nom) {
        this.nom = nom;
        this.preferences = new ArrayList<>();
    }

    /**
     * Retourne le nom du colon.
     * 
     * @return Le nom du colon.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Met à jour les préférences du colon en fonction d'un tableau de mots et vérifie leur validité
     * par rapport aux ressources disponibles.
     * 
     * @param mots Un tableau de mots représentant les préférences du colon, où le premier élément est son nom.
     * @param ressourcesDispo La liste des ressources disponibles dans la colonie.
     * @throws PreferencesException Si une ressource n'est pas valide ou si des erreurs sont trouvées.
     */
    public void setPreferences(String[] mots, List<String> ressourcesDispo) throws  ColonPreferencesException {
        List<String> mesPreferences = new ArrayList<>();
        
        for (int i = 1; i < mots.length; i++) {
            if (!ressourcesDispo.contains(mots[i])) {
                throw new ColonPreferencesException("Erreur : La ressource \"" + mots[i] + "\" n'est pas disponible dans la colonie.");
            }
            if (mesPreferences.contains(mots[i])) {
                throw new ColonPreferencesException("Erreur : la préférence " + mots[i] + " est en double dans votre liste");
            }
            mesPreferences.add(mots[i]);
        }

        if (mesPreferences.size() != ressourcesDispo.size()) {
            throw new  ColonPreferencesException("Erreur : Vous devez fournir une préférence pour chaque ressource.");
        }

        this.preferences = mesPreferences;
    }

    /**
     * Affiche les préférences du colon dans un ordre strict.
     * 
     * @return Une chaîne de caractères représentant les préférences du colon.
     */
    public String afficherPreferences() {
        StringBuilder build = new StringBuilder();
        build.append("Voici les préférences du colon " + nom + "\n");
        for (String i : preferences) {
            build.append(i + ">");
        }
        build.deleteCharAt(build.length() - 1); // Enlève le dernier ">"
        return build.toString();
    }

    /**
     * Retourne la ressource attribuée au colon.
     * 
     * @return La ressource du colon.
     */
    public String getRessource() {
        return ressource;
    }

    /**
     * Vérifie si ce colon pourrait être jaloux d'un autre colon en fonction de l'ordre de leurs préférences.
     * 
     * @param autre Un autre colon à comparer.
     * @return true si ce colon pourrait être jaloux de l'autre colon, sinon false.
     */
    public boolean estPotentiellementJalou(Colon autre) {
        if (this.getPreferences().indexOf(autre.getRessource()) < this.preferences.indexOf(ressource)) {
            return true;
        }
        return false;
    }

    /**
     * Met à jour la ressource attribuée au colon.
     * 
     * @param ressource La nouvelle ressource à attribuer au colon.
     */
    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    /**
     * Retourne la liste des préférences du colon.
     * 
     * @return La liste des préférences.
     */
    public List<String> getPreferences() {
        return preferences;
    }

    /**
     * Redéfinition de la méthode equals pour comparer deux colons par leur nom.
     * 
     * @param obj L'objet à comparer avec ce colon.
     * @return true si les deux colons ont le même nom, sinon false.
     */
    @Override
    public boolean equals(Object obj) {
        // Vérification de l'égalité avec soi-même
        if (this == obj) {
            return true;
        }
        // Vérification si l'objet est de type colon
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Colon colon = (Colon) obj;
        // Vérification si le nom des deux colons est le même
        if (this.nom.equals(colon.getNom())) {
            return true;
        }
        return false;
    }

	public void setPreferences(List<String> preferences) {
		this.preferences=preferences;
		
	}
}
