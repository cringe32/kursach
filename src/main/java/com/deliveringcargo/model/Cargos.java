package com.deliveringcargo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cargos {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    private int count;
    private String photo;
    private int price;
    private String description;
    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Ordering> orderings;
    public Cargos(String name, String photo, int price, String description,Category category) {
        this.name = name;
        this.category = category;
        this.photo = photo;
        this.price = price;
        this.description = description;
        this.count = 0;
    }
}
