package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HoKhau;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HoKhau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoKhauRepository extends JpaRepository<HoKhau, Long> {}
