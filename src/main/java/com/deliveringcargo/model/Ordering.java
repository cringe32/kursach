package com.deliveringcargo.model;

import com.deliveringcargo.model.enums.StatusOrdering;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ordering {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users client;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cargos cargo;
    private String address;
    @Enumerated(EnumType.STRING)
    private StatusOrdering statusOrdering;

    public Ordering( Users client, Cargos cargo, String address) {
        this.client = client;
        this.cargo = cargo;
        this.address = address;
        this.statusOrdering = StatusOrdering.WAITING;
    }
}
