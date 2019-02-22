package zb.aplication.repository;

import zb.aplication.domain.Articulo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Articulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

}
