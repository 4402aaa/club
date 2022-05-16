package org.zerock.club.entity;


import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
/*
한명의 클럽 회원이 여러개의 권한을 가진다는 전제
member와 memberRole 관계는 1:N의 관계지만 memberRole자체가 핵심적인 역할을 하지는 못하기 때문에 별도의 엔티티 보다는 @ElementCollection이용해서 별도의 pk생성 없이 구성
*/
public class ClubMember extends BaseEntity {

    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)//Lazy : 지연로딩(필요한 시점에 연관된 데이터를 불러올때
                                              //Eager : 즉시로딩(데이터를 조회할 때 연관된 데이터까지 한번에 불러온다)
    private Set<ClubMemberRole> roleSet;//ClubMemberRole 타입값을 처리하기 위해서

    public void addMemberRole(ClubMemberRole clubMemberRole) {
        roleSet.add(clubMemberRole);
    }

}
