package utez.edu.mx.adoptame.e1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.DetailUserinfo;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;

import java.util.Optional;

@Repository
public interface DetailUserInfoRepository extends JpaRepository<DetailUserinfo, Long> {

    Optional <DetailUserinfo> findDetailUserinfoByUser(UserAdoptame user);
}
