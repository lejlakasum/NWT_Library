package com.example.demo.Member;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MemberRepository extends CrudRepository<Member,Integer> {
    List<Member> findAll();
    Member findById(ID id);
    void deleteById(ID id);
}
