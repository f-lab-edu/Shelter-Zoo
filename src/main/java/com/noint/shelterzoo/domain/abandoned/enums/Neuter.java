package com.noint.shelterzoo.domain.abandoned.enums;

import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Neuter {
    ALL("전체", "A"),
    TRUE("중성화", "Y"),
    FALSE("비중성화", "N"),
    UNKNOWN("미상", "U"),
    ;
    private final String fullText;
    private final String initial;

    public static Neuter findEnumByFullText(String fullText) {
        return Arrays.stream(Neuter.values())
                .filter(e -> fullText.equals(e.getFullText()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionBody.UNKNOWN_NEUTER_ID));
    }

    public static Neuter findEnumByInitial(String initial) {
        return Arrays.stream(Neuter.values())
                .filter(e -> initial.equals(e.getInitial()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionBody.UNKNOWN_NEUTER_ID));
    }
}
