package project.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.config.SmsUtil;
import project.backend.dto.userDto.*;
import project.backend.entity.User;
import project.backend.exception.SmsCertificationNumberMismatchException;
import project.backend.exception.UserNotFoundException;
import project.backend.repository.SmsCertificationDao;
import project.backend.repository.UserRepository;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final SmsUtil smsUtil;
    private final BCryptPasswordEncoder encoder;
    private final SmsCertificationDao smsCertificationDao;

    /**
     * 회원가입
     */
    @Transactional
    public UserResp createUser(UserReq userRequest) {
        //password 인코딩
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));
        User user = User.from(userRequest);
        User savedUser = userRepository.save(user);

        return UserResp.from(savedUser);
    }

    public UserResp getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserResp.from(user);
    }

    public String getUsername(String name, String phoneNumber) {
        User user = userRepository.findByNameAndPhoneNumber(name, phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        return user.getUsername();
    }

    /**
     * 비밀번호 찾기
     */
    public UserResp findUserForPasswordReset(String name, String username, String email) {
        User user = userRepository.findByNameAndUsernameAndEmail(name, username, email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        return UserResp.from(user);

    }

    @Transactional
    public void updateUser(Long userId, UserSimpleReq userSimpleRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        //user 정보 수정
        user.update(userSimpleRequest.getUsername(), encoder.encode(userSimpleRequest.getPassword()), userSimpleRequest.getNickname(), userSimpleRequest.getAccount(), userSimpleRequest.getAccountHolder(), userSimpleRequest.getBank());
    }

    @Transactional
    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.updatePassword(encoder.encode(newPassword));
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //user 삭제
        userRepository.delete(user);
    }

    /**
     * 인증 번호 전송
     */
    @Transactional
    public void sendSmsToFindEmail(VerifyCodeReq verifyCodeRequest) {
        String name = verifyCodeRequest.getName();
        String phoneNum = verifyCodeRequest.getPhoneNumber().replaceAll("-", "");

        userRepository.findByNameAndPhoneNumber(name, phoneNum)
                .orElseThrow(() -> new UserNotFoundException("회원이 존재하지 않습니다."));

        String verificationCode = UUID.randomUUID().toString().substring(0, 6); // 무작위 인증 코드 생성
        log.info("verificationCode={}", verificationCode);
        smsUtil.sendOne(phoneNum, verificationCode);
        //생성된 인증번호를 Redis에 저장
        smsCertificationDao.createSmsCertification(phoneNum,verificationCode);
    }

    /**
     * 인증 번호 확인
     */
    public void verifySms(CheckCodeRequest checkCodeRequest) {
        if (!isVerify(checkCodeRequest)) {
            throw new SmsCertificationNumberMismatchException("인증번호가 일치하지 않습니다.");
        } else {
            smsCertificationDao.removeSmsCertification(checkCodeRequest.getPhoneNumber());
        }
    }

    private boolean isVerify(CheckCodeRequest findUsernameRequest) {
        return smsCertificationDao.hasKey(findUsernameRequest.getPhoneNumber()) &&
                smsCertificationDao.getSmsCertification(findUsernameRequest.getPhoneNumber())
                        .equals(findUsernameRequest.getCertificationNumber());
    }

}