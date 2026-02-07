package org.example.teleporti.Entities;

import java.sql.Date;
import java.util.Objects;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String email;
    private String motDePasse;
    private String type;
    private String governerat;
    private String ville;
    private String addresse;
    private String telephone;
    private String token = "";
    private Date creationDate = new Date(new java.util.Date().getTime());
    private Date updateDate = new Date(new java.util.Date().getTime());

    public User(
            int id,
            String nom,
            String prenom,
            int age,
            String email,
            String motDePasse,
            String type,
            String governerat,
            String ville,
            String addresse,
            String telephone
    ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
        this.governerat = governerat;
        this.ville = ville;
        this.addresse = addresse;
        this.telephone = telephone;
    }

    public User(
            int id,
            String nom,
            String prenom,
            int age,
            String email,
            String motDePasse,
            String type,
            String governerat,
            String ville,
            String addresse,
            String telephone,
            Date creationDate,
            Date updateDate
    ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
        this.governerat = governerat;
        this.ville = ville;
        this.addresse = addresse;
        this.telephone = telephone;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getGovernerat() {
        return governerat;
    }

    public void setGovernerat(String governerat) {
        this.governerat = governerat;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return getId() == user.getId() && getAge() == user.getAge() && Objects.equals(getNom(), user.getNom()) && Objects.equals(getPrenom(), user.getPrenom()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getMotDePasse(), user.getMotDePasse()) && Objects.equals(getType(), user.getType()) && Objects.equals(getGovernerat(), user.getGovernerat()) && Objects.equals(getVille(), user.getVille()) && Objects.equals(getAddresse(), user.getAddresse()) && Objects.equals(getTelephone(), user.getTelephone()) && Objects.equals(getToken(), user.getToken()) && Objects.equals(getCreationDate(), user.getCreationDate()) && Objects.equals(getUpdateDate(), user.getUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), getPrenom(), getAge(), getEmail(), getMotDePasse(), getType(), getGovernerat(), getVille(), getAddresse(), getTelephone(), getToken(), getCreationDate(), getUpdateDate());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", type='" + type + '\'' +
                ", governerat='" + governerat + '\'' +
                ", ville='" + ville + '\'' +
                ", addresse='" + addresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", token='" + token + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }

    public static User empty() {
        return new User(0, "", "", 0, "", "", "Client", "", "", "", "");
    }
}
