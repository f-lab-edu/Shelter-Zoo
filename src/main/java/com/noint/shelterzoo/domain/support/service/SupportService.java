package com.noint.shelterzoo.domain.support.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyType;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogAddRequestVO;
import com.noint.shelterzoo.domain.support.dto.req.DonateRequestDTO;
import com.noint.shelterzoo.domain.support.dto.res.SupportPetListResponseDTO;
import com.noint.shelterzoo.domain.support.enums.SupportExceptionBody;
import com.noint.shelterzoo.domain.support.exception.SupportException;
import com.noint.shelterzoo.domain.support.repository.SupportRepository;
import com.noint.shelterzoo.domain.support.vo.req.AddSupportLogRequestVO;
import com.noint.shelterzoo.domain.support.vo.req.SupportPetListRequestVO;
import com.noint.shelterzoo.domain.support.vo.req.UpdateSupportPetMoneyRequestVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportMoneysResponseVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportPetListResponseVO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurpose;
import com.noint.shelterzoo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;
    private final UserService userService;
    private final MoneyLogService moneyLogService;

    @Transactional(readOnly = true)
    public PageInfo<SupportPetListResponseDTO> getSupportPetList(PageParam request) {
        PageInfo<SupportPetListResponseVO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> supportRepository.getSupportPetList(SupportPetListRequestVO.create(request)));
        return SupportPetListResponseDTO.create(pageInfo);
    }

    @Transactional
    public void doDonate(Long userSeq, DonateRequestDTO request) {
        SupportMoneysResponseVO moneysAboutSupportPet = supportRepository.getMoneysAboutSupport(request.getSupportPetSeq());
        checkPetDonateAble(moneysAboutSupportPet, request.getDonateAmount());

        BigDecimal baseUserMoney = userService.getUserMoneyForUpdate(userSeq);
        BigDecimal updatedUserMoney = baseUserMoney.subtract(request.getDonateAmount());
        checkUserDonateAble(updatedUserMoney);

        executeDonate(userSeq, request, moneysAboutSupportPet, updatedUserMoney);
    }

    private void checkPetDonateAble(SupportMoneysResponseVO moneysAboutSupportPet, BigDecimal donateAmount) {
        boolean isAbleDonate = moneysAboutSupportPet.getRequiredMoney().compareTo(moneysAboutSupportPet.getSupportedMoney()) > 0;
        if (!isAbleDonate) {
            log.warn("후원 실패 : 후원 금액 달성");
            throw new SupportException(SupportExceptionBody.DISABLED_DONATE_BY_GOAL);
        }
        boolean isAbleAmount = donateAmount.add(moneysAboutSupportPet.getSupportedMoney()).compareTo(moneysAboutSupportPet.getRequiredMoney()) < 1;
        if (!isAbleAmount) {
            log.warn("후원 실패 : 후원 금액 초과");
            throw new SupportException(SupportExceptionBody.OVER_REMAINING_AMOUNT);
        }
    }

    private void checkUserDonateAble(BigDecimal updatedUserMoney) {
        if (updatedUserMoney.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("후원 실패 : 유저 재화 부족");
            throw new SupportException(SupportExceptionBody.RACK_OF_MONEY);
        }
    }

    private void executeDonate(Long userSeq, DonateRequestDTO request, SupportMoneysResponseVO moneysAboutSupportPet, BigDecimal updatedUserMoney) {
        supportRepository.updateSupportedMoney(UpdateSupportPetMoneyRequestVO.create(moneysAboutSupportPet, request));
        userService.updateUserMoney(userSeq, updatedUserMoney);
        AddSupportLogRequestVO addLogRequest = AddSupportLogRequestVO.create(userSeq, request);
        supportRepository.addSupportLog(addLogRequest);
        moneyLogService.addMoneyLogBySupport(
                MoneyLogAddRequestVO.create(userSeq, MoneyType.WITHDRAWAL, request.getDonateAmount().multiply(BigDecimal.valueOf(-1)), updatedUserMoney, MoneyUpdatePurpose.SUPPORT, addLogRequest.getSeq())
        );
    }
}
