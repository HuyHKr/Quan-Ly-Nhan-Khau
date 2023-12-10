package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NopTien;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NopTien entity.
 */
@Repository
public interface NopTienRepository extends JpaRepository<NopTien, Long> {
    default Optional<NopTien> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NopTien> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NopTien> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select nopTien from NopTien nopTien left join fetch nopTien.hoKhau left join fetch nopTien.khoanThu",
        countQuery = "select count(nopTien) from NopTien nopTien"
    )
    Page<NopTien> findAllWithToOneRelationships(Pageable pageable);

    @Query("select nopTien from NopTien nopTien left join fetch nopTien.hoKhau left join fetch nopTien.khoanThu")
    List<NopTien> findAllWithToOneRelationships();

    @Query("select nopTien from NopTien nopTien left join fetch nopTien.hoKhau left join fetch nopTien.khoanThu where nopTien.id =:id")
    Optional<NopTien> findOneWithToOneRelationships(@Param("id") Long id);
}
