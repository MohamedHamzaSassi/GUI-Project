package org.example.teleporti.Services.User;

import org.example.teleporti.Entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// La classe ServiceUser implémente l'interface IServiceUser et fournit des fonctionnalités pour gérer les entités User.
public class ServiceUser implements IServiceUser {

    private Statement ste; // Objet Statement utilisé pour exécuter les requêtes SQL
    int size = 0; // Variable pour stocker le nombre d'utilisateurs

    // Le constructeur initialise l'objet Statement en utilisant la connexion de base de données fournie.
    public ServiceUser(Connection con) {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Méthode permettant de vider toutes les données de la table "users".
    public void truncate() {
        String req = "truncate table users"; // Requête SQL pour vider la table "users"
        try {
            ste.executeUpdate(req); // Exécution de la requête
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Méthode pour ajouter un nouvel utilisateur dans la base de données.
    @Override
    public boolean ajout(User newUser) {
        String req = "insert into users (nom, prenom, age, email, password, type, governerat, ville, addresse, telephone, creation_date, update_date) values ('" +
                newUser.getNom() + "', '" +
                newUser.getPrenom() + "', '" +
                newUser.getAge() + "', '" +
                newUser.getEmail() + "', '" +
                newUser.getMotDePasse() + "', '" +
                newUser.getType() + "', '" +
                newUser.getGovernerat() + "', '" +
                newUser.getVille() + "', '" +
                newUser.getAddresse() + "', '" +
                newUser.getTelephone() + "', '" +
                newUser.getCreationDate() + "', '" +
                newUser.getUpdateDate() +
                "')";
        try {
            if (getUserByEmailAndPassword(newUser.getEmail(), newUser.getMotDePasse()) != null) { // Vérifie si l'utilisateur existe déjà
                System.out.println("L'utilisateur existe déjà");
                return false;
            } else {
                ste.executeUpdate(req); // Exécution de la requête pour insérer un utilisateur
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Méthode pour afficher tous les utilisateurs dans la base de données.
    @Override
    public void afficher() {
        String req = "select id, nom, prenom, age, email, password, type, creation_date, update_date from users";
        try {
            if (getSize() == 0) { // Vérifie si la table "users" est vide
                System.out.println("Aucun utilisateur trouvé");
            } else {
                ResultSet res = ste.executeQuery(req); // Exécution de la requête et récupération des résultats
                while (res.next()) { // Parcours de chaque ligne du jeu de résultats
                    System.out.println("ID: " + res.getInt("id") +
                            "\nNom: " + res.getString("nom") +
                            "\nPrenom: " + res.getString("prenom") +
                            "\nAge: " + res.getInt("age") +
                            "\nEmail: " + res.getString("email") +
                            "\nMot de passe: " + res.getString("password") +
                            "\nType: " + res.getString("type") +
                            "\nDate de création: " + res.getString("creation_date") +
                            "\nDate de modification: " + res.getString("update_date")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<User> afficherList() {
        List<User> users = new ArrayList<>();
        String req = "select * from users";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                users.add(new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    // Méthode pour modifier les informations d'un utilisateur dans la base de données.
    @Override
    public void modifier(User user) {
        String req = "update users set nom = '" +
                user.getNom() + "', prenom = '" +
                user.getPrenom() + "', age= '" +
                user.getAge() + "', email = '" +
                user.getEmail() + "', password = '" +
                user.getMotDePasse() + "', type = '" +
                user.getType() + "', governerat = '" +
                user.getGovernerat() + "', ville = '" +
                user.getVille() + "', addresse = '" +
                user.getAddresse() + "', telephone = '" +
                user.getTelephone() + "', creation_date = '" +
                user.getCreationDate() + "', update_date = '" +
                user.getUpdateDate() + "' where id = " +
                user.getId();
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param nom
     * @return
     */
    @Override
    public List<User> rechercher(String nom) {
        String req = "select * from users where nom like '%" + nom + "%'";
        List<User> users = new ArrayList<>();
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                users.add(new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    /**
     * Cette méthode permet de supprimer un utilisateur de la base de données.
     *
     * @param user User
     */
    @Override
    public void supprimer(User user) {
        String req = "delete from users where id = " + user.getId();
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cette méthode permet de compter le nombre d'utilisateurs en fonction de leur type.
     *
     * @param type String
     * @return int
     */
    @Override
    public int countByType(String type) {
        String req = "select count(*) from users where type = '" + type + "'";
        try {
            ResultSet result = ste.executeQuery(req);
            while (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * @param gouvernerat
     * @return
     */
    @Override
    public int countByGovernerat(String gouvernerat) {
        String req = "select count(*) from users where gouvernerat = '" + gouvernerat + "'";
        try {
            ResultSet result = ste.executeQuery(req);
            while (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // Méthode pour obtenir le nombre total d'utilisateurs dans la base de données.
    public int getSize() {
        String req = "select count(*) from users";
        try {
            ResultSet result = ste.executeQuery(req);
            while (result.next()) {
                size = result.getInt(1);
            }
            return size;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // Méthode pour récupérer un utilisateur par son email et son mot de passe.
    public User getUserByEmailAndPassword(String email, String password) {
        String req = "select * from users where email='" + email + "' and password='" + password + "'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {
        String req = "select * from users where email='" + email + "'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param prenom
     * @param nom
     * @return
     */
    @Override
    public User getUserByPrenomAndNom(String prenom, String nom) {
        String req = "select * from users where prenom='" + prenom + "' and nom='" + nom + "'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        String req = "select * from users where id=" + id;
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllChauffeurs() {
        List<User> users = new ArrayList<>();
        String req = "select * from users where type = 'chauffeur'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                users.add(new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> getAllClients() {
        String req = "select * from users where type = 'client'";
        List<User> users = new ArrayList<>();
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                users.add(new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public List<String> getAllGovernerats() {
        String req = "select distinct(governerat) from users";
        List<String> governerats = new ArrayList<>();
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                governerats.add(res.getString("governerat"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return governerats;
    }

    public void createUsersTableInDatabase() {
        String schema = "create table users (" +
                "id int(11) primary key auto_increment," +
                "nom varchar(20)," +
                "prenom varchar(20)," +
                "age int(11)," +
                "email varchar(50)," +
                "password varchar(25)," +
                "type varchar(20)," +
                "governerat varchar(50)," +
                "ville varchar(50)," +
                "addresse varchar(255)," +
                "telephone varchar(8)," +
                "token varchar(50)," +
                "creation_date datetime," +
                "update_date datetime" +
                ")";
        try {
            boolean res = ste.execute(schema);
            if (res) {
                System.out.println("Table created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
