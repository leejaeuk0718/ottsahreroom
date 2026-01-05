package project.backend.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BankType {

    KAKAO("카카오뱅크"),
    NH("NH농협은행"),
    KB("KB국민은행"),
    SHINHAN("신한은행"),
    WOORI("우리은행"),
    SAEMAEUL("새마을금고"),
    BUSAN("부산은행"),
    IBK("IBK기업은행"),
    TOS("토스뱅크"),
    etc("Other Banks");

    private final String value;

    @JsonCreator
    public static BankType from(String value) {
        return Arrays.stream(BankType.values())
                .filter(bank -> bank.name().equalsIgnoreCase(value) || bank.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 은행 타입입니다: " + value));
    }
}
