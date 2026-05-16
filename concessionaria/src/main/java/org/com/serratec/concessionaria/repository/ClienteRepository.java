package org.com.serratec.concessionaria.repository;

import org.com.serratec.concessionaria.entity.Cliente;
import org.com.serratec.concessionaria.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    List<Cliente> findByNome(String nome);

    Optional<Cliente> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
