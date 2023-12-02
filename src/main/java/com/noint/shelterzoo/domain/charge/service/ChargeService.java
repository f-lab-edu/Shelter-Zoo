package com.noint.shelterzoo.domain.charge.service;

import com.noint.shelterzoo.domain.charge.dto.req.ChargeMoneyRequestDTO;
import com.noint.shelterzoo.domain.charge.enums.ChargeExceptionEnum;
import com.noint.shelterzoo.domain.charge.exception.ChargeException;
import com.noint.shelterzoo.domain.charge.repository.ChargeRepository;
import com.noint.shelterzoo.domain.charge.vo.req.ChargeLogRequestVO;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogInsertRequestVO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
import com.noint.shelterzoo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargeService {
    private final ChargeRepository chargeRepository;
    private final UserService userService;
    private final MoneyLogService moneyLogService;

    @Transactional
    public void chargeMoney(Long userSeq, ChargeMoneyRequestDTO request) {
        if (isDuplicateChargeId(request.getChargeId())) {
            log.warn("충전 실패 - ChargeId 중복, params : {userSeq : {}, request : {}}", userSeq, request);
            throw new ChargeException(ChargeExceptionEnum.DUPLICATE_CHARGE_ID);
        }
        BigDecimal baseUserMoney = userService.getUserMoneyForUpdate(userSeq);
        BigDecimal updateMoney = baseUserMoney.add(request.getChargeAmount());
        ChargeLogRequestVO chargeLogRequest = ChargeLogRequestVO.create(userSeq, updateMoney, request);
        userService.updateUserMoney(userSeq, updateMoney);
        addChargeLog(chargeLogRequest);
        moneyLogService.addMoneyLogByCharge(
                MoneyLogInsertRequestVO.create(userSeq, MoneyTypeEnum.DEPOSIT, request.getChargeAmount(), updateMoney, MoneyUpdatePurposeEnum.CHARGE, chargeLogRequest.getSeq())
        );
    }

    private boolean isDuplicateChargeId(String chargeId) {
        return chargeRepository.countChargeId(chargeId) > 0;
    }

    private void addChargeLog(ChargeLogRequestVO request) {
        chargeRepository.addChargeLog(request);
    }
}
