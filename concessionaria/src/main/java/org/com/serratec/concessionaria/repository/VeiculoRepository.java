package org.com.serratec.concessionaria.repository;

import org.com.serratec.concessionaria.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {


    @Query(value = "SELECT * FROM veiculos WHERE unaccent(lower(marca)) LIKE unaccent(lower(concat('%', :marca, '%')))", nativeQuery = true)
    List<Veiculo> findByMarcaIgnoreCaseAndAccent(@Param("marca") String marca);

    @Query(value = "SELECT * FROM veiculos WHERE unaccent(lower(modelo)) LIKE unaccent(lower(concat('%', :modelo, '%')))", nativeQuery = true)
    List<Veiculo> findByModeloIgnoreCaseAndAccent(@Param("modelo") String modelo);
    @Query(value = "SELECT COUNT(*) > 0 FROM veiculos WHERE lower(placa) = lower(:placa)", nativeQuery = true)
    boolean existsByPlacaIgnoreCase(@Param("placa") String placa);

    @Query(value = "SELECT * FROM veiculos WHERE lower(placa) = lower(:placa)", nativeQuery = true)
    Optional<Veiculo> findByPlacaIgnoreCase(@Param("placa") String placa);
}