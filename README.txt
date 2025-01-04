Projet : Simulation d'une colonie spatiale



Classe contenant la méthode main (String[]args) : colonie.Main

Algorithme de minimisation :
J'ai implémenter un autre algorithme que celui proposé par l'énoncé ,
dans un premier temps, l'algorithme effectue une affectation  naïve des ressources et initialise le cout actuel qui va etre le cout en jalousie de la colonie a un moment t, il va aussi
initialiser un compteur qui représente le nombre d'échanges effectués sans que l'on trouve d'amélioration en cout. Ensuite, il va en k
itérations faire les opérations suivantes :

L'algorithme va voir les couples qui donnent le meilleur échange possible, ce sont les couples qui après échange obtiennent le gain le plus optimale.
Ce gain est calculé en faisant la différence entre le cout avant échange et le cout après échange.

Pour ce faire, on va initialiser une liste contenant les meilleures paires, c'est-à-dire les paires qui donnent le meilleur gain car on peut en
avoir plusieurs,  et on va initialiser le meilleur gain à 0. On va ensuite parcourir tous les couples possibles de la colonie et simuler l'échange
entre les colons, si leurs gains est supérieur au gain actuel, cela veut dire qu'on a trouvé un couple de colons qui donne un gain plus optimal que
le meilleur gain précédent, et on va donc supprimer tous les couples de la liste des meilleures paires et on va y ajouter la nouvelle paire,
on va aussi affecter le meilleur gain au nouveau meilleur gain trouvé. Si le gain est égal au meilleur gain, on ajoute la paire à la liste des meilleurs
paires. 
Ensuite, on annule l'échange, car on ne fait pour l'instant que de simuler les meilleurs échanges.

Quand on a fini de parcourir les meilleures paires, on parcourt la liste des meilleures paires et fait un échange, si le cout après échange 
est inférieur au cout actuel de la colonie, on affecte le cout actuel au nouveau cout après échange et on "reset" le compteur d'échanges sans améliorations.
Sinon, on annule l'échange et on augmente de 1 le compteur d'échanges sans amélioration.

Ensuite, si le programme stagne, c'est-à-dire qu'on obtient un nombre d'échanges sans améliorations supérieur à 10 (nombre choisi un peu arbitrairement),
On va parcourir la colonie et faire des échanges. Si on obtient un nouveau cout  inférieur ou égale au cout actuel, on choisit de tout de même garder les échanges
qui ne donnent pas de gain strictement positif, comme ça notre algorithme parcourt de nouvelles configurations et cela diminuera les chances de stagnation.
Si le gain est strictement négatif, on annule l'échange. On réinitialise ensuite le compteur d'échange sans améliorations.

Enfin, si le nombre d'échanges sans amélioration est trop élevé (ici, on a choisi une valeur égale à la taille de la colonie  multipliée par 5), on sort prématurément.
de l'algorithme.


Fonctionnalités  implémentées :


-Choix entre une interface graphique et un affichage classique dans le terminal pour la simulation de la colonie

-Créer une colonie manuellement ou la lire à partir d'un fichier

-Ajouter des relations entre des colons

-Ajouter les préférences d'un colon

-Vérification de la validité des préférences des colons

-Affectation naïve des ressources

-Pouvoir échanger les ressources entre 2 colons

-Calcul et affichage du cout en jalousie de la colonie

-Si on a un argument en ligne de commande, le programme récupère le fichier à l'emplacement indiqué par l'argument de la ligne de commande et vérifie

que le fichier soit conforme à la syntaxe de la consigne, et si tout est bon, on génère une colonie à partir du fichier.

-Trouver une configuration de la colonie où le cout en jalousie est minimal 

-Pouvoir sauvergarder la solution actuelle dans un fichier

-Test unitaires sur les classes Colon, Colonnie,ParserColonie et SauvegarderColonie.

Fonctionnalités  manquantes :
Aucune

Énonce :
Après chaque mission de ravitaillement, le commandant d’une colonie
Spatiale doit répartir des ressources critiques entre les colons. Parmi
ces ressources, chaque colon doit recevoir une de ces ressources critiques
(e.g., Un équipement ou une ration de vivres essentiels). Pour maintenir
l’harmonie dans la colonie, le commandant demande à chaque membre de
lui soumettre ses preferences sur les ressources allouees et doit tenter
de les respecter au mieux. En plus de ces preferences, il doit tenir compte
des relations entre les colons. Certains s’entendent mal, et une
mauvaise repartition des ressources pourrait entraıner des conflits ou
mettre en danger la survie de la colonie