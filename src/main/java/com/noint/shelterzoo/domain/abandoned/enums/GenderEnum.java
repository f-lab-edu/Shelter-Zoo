package com.noint.shelterzoo.domain.abandoned.enums;

import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GenderEnum {
    ALL("전체", "A"),
    FEMALE("암컷", "F"),
    MALE("수컷", "M"),
    UNKNOWN("미상", "Q"),
    ;
    private final String fullText;
    private final String initial;

    public static GenderEnum findEnumByFullText(String fullText) {
        return Arrays.stream(GenderEnum.values())
                .filter(e -> fullText.equals(e.getFullText()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionEnum.UNKNOWN_GENDER_ID));
    }

    public static GenderEnum findEnumByInitial(String initial){
        return Arrays.stream(GenderEnum.values())
                .filter(e -> initial.equals(e.getInitial()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionEnum.UNKNOWN_GENDER_ID));
    }
}
