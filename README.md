# MCPlugin18
Minecraft plugin for 1.18.2 and jdk17

 * /warp list - Permet de lister les TP enregistr�s
 * /warp create NOM_DU_TP - Permet d'enregistrer un TP
 * /warp delete NOM_DU_TP - Permet de supprimer un TP
 * /warp setgroup NOM_DU_TP NOM_DU_GROUP - Permet d'affecter un groupe � un TP
 * /warp removegroup NOM_DU_TP - Permet de d�lier le group d'un TP, �a ne supprime pas le groupe, mais le TP est ouvert � tous
 * /warpgroup list - Permet de lister les groupes enregistr�s
 * /warpgroup list NOM_DU_GROUP - Permet de lister les joueurs dans un groupe
 * /warpgroup create NOM_DU_GROUP - Permet de cr�er un groupe vide, affecter un groupe vide � un TP permet de bloquer le TP � tous
 * /warpgroup delete NOM_DU_GROUP - Permet de supprimer un groupe, si un TP est li� � ce groupe, le TP ne fonctionnera plus
 * /warpgroup add NOM_DU_GROUP NOM_DU_JOUEUR - Permet d'ajouter un joueur au groupe, seul les joueurs dans le groupe pourront se t�l�porter
 * /warpgroup remove NOM_DU_GROUP NOM_DU_JOUEUR - Permet de supprimer un joueur du groupe


 * /inv - Permet d'ouvrir l'inventaire 0
 * /inv 1 - Permet d'ouvrir l'inventaire 1, vous pouvez mettre n'importe quel nombre
 * /inv list - Permet de lister les inventaires personnels deja cr��s
 * /inv share - Permet d'ouvrir l'inventaire partag� avec les autres joueurs
 
 Run as maven build, goal: package