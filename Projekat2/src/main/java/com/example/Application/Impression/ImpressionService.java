package com.example.Application.Impression;

import com.example.Application.ExceptionClasses.NotFoundException;
import com.example.Application.Member.Member;
import com.example.Application.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImpressionService {

    @Autowired
    ImpressionRepository impressionRepository;
    @Autowired
    MemberRepository memberRepository;

    public List<ImpressionDTO> GetImpressionsByBook(Integer bookId) {

        List<Tuple> dbImpressions = impressionRepository.findImpressionsByBook(bookId);

        List<ImpressionDTO> impressionDTOs = new ArrayList<>();

        for (Tuple dbImpression : dbImpressions) {

            Integer memberId = (Integer)dbImpression.get("id");
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(()->new NotFoundException("member", memberId));

            ImpressionDTO impression = new ImpressionDTO((String)dbImpression.get("impression"),
                                                         (Integer)dbImpression.get("rate"),
                                                          member);

            impressionDTOs.add(impression);
        }

        return impressionDTOs;
    }

    public void AddImpression(Impression impression) {

        impressionRepository.save(impression);
    }
}
