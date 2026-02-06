package ru.otus.crm.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {
    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.setAddress(address);
        this.setPhones(phones);
    }

    public void setAddress(Address address) {
        if (address == null) {
            this.address = null;
        } else {
            this.address = address.clone();
            this.address.setClient(this);
        }
    }

    public void setPhones(List<Phone> phones) {
        if (phones == null) {
            this.phones = new ArrayList<>();
        } else {
            this.phones = phones.stream()
                    .map(Phone::clone)
                    .collect(Collectors.toList());

            this.phones.forEach(phone -> phone.setClient(this));
        }
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    @Override
    public String toString() {
        return String.format("Client{id=%s, name=%s, addressId=%s, phones=%s}",
                this.id, this.name,
                (this.address != null) ? this.address.getId() : null,
                (this.phones != null) ? this.phones : null);
    }
}
