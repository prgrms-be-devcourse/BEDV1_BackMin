package com.backmin.domains.member.domain;

import com.backmin.domains.member.vo.Nickname;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member AS m WHERE m.email LIKE %?1%")
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member AS m WHERE m.nickName LIKE %?1%")
    Optional<Member> findByNickName(String nickName);
}
