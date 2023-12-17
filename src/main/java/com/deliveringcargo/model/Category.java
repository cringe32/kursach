package com.deliveringcargo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Cargos> cargos;

    public Category(String name) {
        this.name = name;
        cargos = new ArrayList<>();
    }

    public void addCargo(Cargos cargo) {
        cargos.add(cargo);
        cargo.setCategory(this);
    }

    public void removeCargo(Cargos cargo) {
        cargos.remove(cargo);
        cargo.setCategory(null);
    }
}
