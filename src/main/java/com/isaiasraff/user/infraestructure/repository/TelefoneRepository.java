package com.isaiasraff.user.infraestructure.repository;

import com.isaiasrafael.authservice.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository <Telefone, Long> {
}
