package com.noint.shelterzoo.domain.abandoned.enums;

import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PetGender {
    ALL("전체", "A"),
    FEMALE("암컷", "F"),
    MALE("수컷", "M"),
    UNKNOWN("미상", "Q"),
    ;
    private final String fullText;
    private final String initial;

    public static PetGender findEnumByFullText(String fullText) {
        return Arrays.stream(PetGender.values())
                .filter(e -> fullText.equals(e.getFullText()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionBody.UNKNOWN_GENDER_ID));
    }

    public static PetGender findEnumByInitial(String initial) {
        return Arrays.stream(PetGender.values())
                .filter(e -> initial.equals(e.getInitial()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionBody.UNKNOWN_GENDER_ID));
    }
}
