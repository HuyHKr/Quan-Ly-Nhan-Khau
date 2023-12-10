package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.KhoanThu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KhoanThu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KhoanThuRepository extends JpaRepository<KhoanThu, Long> {}
