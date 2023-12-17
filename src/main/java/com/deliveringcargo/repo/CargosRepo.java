package com.deliveringcargo.repo;

import com.deliveringcargo.model.Cargos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargosRepo extends JpaRepository<Cargos, Long> {
    List<Cargos> findAllByNameContainingAndCategory_Name(String name, String categoryName);
}
