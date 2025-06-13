package web.mvc.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import web.mvc.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("select m from Member m where m.id=?1")
    Member duplicateCheck(String id);
    Boolean existsById(String id);
    Member findById(String id);

}
