package project.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.backend.dto.userDto.SocialUserRequest;
import project.backend.dto.userDto.UserReq;
import project.backend.dto.userDto.UserResp;
import project.backend.enums.BankType;
import project.backend.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "user")
public class User extends  BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private SharingUser sharingUser;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private WaitingUser waitingUser;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FAQ> faqs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inquiry> inquiries = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "bank", nullable = false)
    @Enumerated(EnumType.STRING)
    private BankType bank;

    //계좌번호
    @Column(name = "account", nullable = false)
    private String account;

    //예금주
    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_share_room", nullable = false, columnDefinition = "boolean default false")
    private boolean isShareRoom;

    //비지니스 로직
    public void update(String username, String password, String nickname, String account, String accountHolder, BankType bank) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.account = account;
        this.accountHolder = accountHolder;
        this.bank = bank;
    }


    //비지니스 로직

    public void updatePassword(String password) {
        this.password = password;
    }

    public void checkShareRoom() {
        this.isShareRoom = true;
    }

    public void leaveShareRoom() {
        this.isShareRoom = false;
    }



    public static User from(SocialUserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .nickname(userRequest.getNickname())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .build();
    }

    public static User from(UserResp response) {
        return User.builder()
                .id(response.getUserId())
                .username(response.getUsername())
                .password(response.getPassword())
                .email(response.getEmail())
                .nickname(response.getNickname())
                .phoneNumber(response.getPhoneNumber())
                .bank(response.getBank())
                .account(response.getAccount())
                .accountHolder(response.getAccountHolder())
                .role(response.getRole())
                .isShareRoom(response.isShareRoom())
                .build();
    }

    public static User from(UserReq request) {
        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .account(request.getAccount())
                .accountHolder(request.getAccountHolder())
                .bank(request.getBank())
                .role(request.getRole())
                .build();
    }




}
