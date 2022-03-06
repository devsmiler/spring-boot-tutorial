package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void joinTest() throws Exception {
        //given
        Member m = new Member();
        m.setName("kim");

        //when
        Long saveId = memberService.join(m);

        //then
        em.flush();
        assertEquals(m, memberRepository.findOne(saveId));
    }

    @Test
    public void duplicateMemberTest() throws Exception {
        //given
        Member m1 = new Member();
        m1.setName("kim");

        Member m2 = new Member();
        m2.setName("kim");

        //when
        memberService.join(m1);
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            memberService.join(m2);
        });

        //then
        assertEquals("Already Exist member", thrown.getMessage());

    }


}