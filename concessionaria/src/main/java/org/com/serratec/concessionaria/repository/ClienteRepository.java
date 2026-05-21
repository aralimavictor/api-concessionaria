package org.com.serratec.concessionaria.repository;

import org.com.serratec.concessionaria.entity.Cliente;
import org.com.serratec.concessionaria.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    // Busca clientes pelo nome ignorando maiúsculas/minúsculas e acentuação.
// Utiliza nativeQuery para executar SQL puro do PostgreSQL, necessário porque
// o Hibernate 6 não reconhece a função unaccent() no JPQL padrão.
// A função unaccent() remove acentos e lower() converte para minúsculas,
// permitindo buscas como "joao", "João" ou "JOAO" retornarem o mesmo resultado.
// O concat('%', :nome, '%') faz a busca por qualquer parte do nome (contém).
// Requer a extensão unaccent instalada no PostgreSQL:
// CREATE EXTENSION IF NOT EXISTS unaccent;
// SELECT unaccent('João');  -> deve retornar 'joao'.
    
    @Query(value = "SELECT * FROM clientes WHERE unaccent(lower(nome)) LIKE unaccent(lower(concat('%', :nome, '%')))", nativeQuery = true)
    List<Cliente> findByNomeIgnoreCaseAndAccent(@Param("nome") String nome);
}
