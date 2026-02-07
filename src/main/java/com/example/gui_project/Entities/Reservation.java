package org.example.teleporti.Entities;

import java.util.Objects;

public class Reservation {
    private int id;
    private int trajetId;
    private int passagerId;
    private String status;

    public Reservation(int id, int trajetId, int passagerId, String status) {
        this.id = id;
        this.trajetId = trajetId;
        this.passagerId = passagerId;
        this.status = status;
    }

    public static Reservation empty() {
        return new Reservation(0, 0, 0, "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(int trajetId) {
        this.trajetId = trajetId;
    }

    public int getPassagerId() {
        return passagerId;
    }

    public void setPassagerId(int passagerId) {
        this.passagerId = passagerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return getId() == that.getId() && getTrajetId() == that.getTrajetId() && getPassagerId() == that.getPassagerId() && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTrajetId(), getPassagerId(), getStatus());
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", trajetId=" + trajetId +
                ", passagerId=" + passagerId +
                ", status='" + status + '\'' +
                '}';
    }
}
