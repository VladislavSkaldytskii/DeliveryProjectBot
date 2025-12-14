package ua.ivanzaitsev.admin.models.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name = "courier")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
    @SequenceGenerator(name = "clients_seq", sequenceName = "clients_id_seq", allocationSize = 1)
    private Integer id;

    @Column
    @Length(max = 255, message = "Name too long (more than 255 characters)")
    private String name;

    @Column(name = "phone_number")
    @Length(max = 255, message = "Phone number too long (more than 255 characters)")
    private String phoneNumber;

    @Column
    @Length(max = 255, message = "City too long (more than 255 characters)")
    private String city;

    @Column
    @Length(max = 255, message = "Address to long (more than 255 characters)")
    private String address;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_chat_id", referencedColumnName = "chat_id", unique = true)
    private Client client;

    public Courier() {}



    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Courier courier = (Courier) o;
        return active == courier.active &&
            Objects.equals(id, courier.id) &&

            Objects.equals(name, courier.name) &&
            Objects.equals(phoneNumber, courier.phoneNumber) &&
            Objects.equals(city, courier.city) &&
            Objects.equals(address, courier.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  name, phoneNumber, city, address, active);
    }

    @Override
    public String toString() {
        return "Client [id=" + id +

            ", name=" + name +
            ", phoneNumber=" + phoneNumber +
            ", city=" + city +
            ", address=" + address +
            ", active=" + active + "]";
    }

}


