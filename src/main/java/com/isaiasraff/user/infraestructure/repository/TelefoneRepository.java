package com.isaiasraff.user.infraestructure.repository;

import com.isaiasraff.user.infraestructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository <Telefone, Long> {
}
