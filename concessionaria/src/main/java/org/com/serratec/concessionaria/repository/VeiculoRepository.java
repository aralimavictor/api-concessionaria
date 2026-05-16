package org.com.serratec.concessionaria.repository;

import org.com.serratec.concessionaria.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

    Optional<Veiculo> findByPlaca(String placa);

    List<Veiculo> findByMarca(String marca);

    List<Veiculo> findByModelo(String modelo);

    boolean existsByPlaca(String placa);

}