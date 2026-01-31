package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address clone() {
        return new Address(this.id, this.street);
    }

    @Override
    public String toString() {
        return String.format("Address{id=%s, street=%s, clientId=%s}",
                this.id, this.street,
                (this.client != null) ? this.client.getId() : null);
    }
}
