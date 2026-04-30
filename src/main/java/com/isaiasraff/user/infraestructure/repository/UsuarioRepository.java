package com.isaiasraff.user.infraestructure.repository;

import com.isaiasraff.user.infraestructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Object> findByEmail(String email);

    @Transactional
    void deleteByEmail (String email);

}
