package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.CampanaEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface CampanaRepository extends JpaRepository<CampanaEntity, Integer> {
    
    @Query("SELECT c FROM CampanaEntity c WHERE CURRENT_DATE BETWEEN c.diaComienzo AND c.diaFinal")
    public CampanaEntity findCampanaActiva(@Param("hoy") LocalDate hoy);
}
