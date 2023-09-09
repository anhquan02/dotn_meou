package com.datn.meou.services;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Voucher;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.VoucherDTO;
import com.datn.meou.repository.VoucherRepository;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public Page<Voucher> getAllPage(Pageable pageable, String nameVoucher) {
        if (!DataUtil.isNullObject(nameVoucher)) {
            return this.voucherRepository.findAllByStatusAndNameContaining(true, pageable, nameVoucher);
        }
        return this.voucherRepository.findAllByStatus(true, pageable);
    }

    public List<Voucher> getAlls(String nameVoucher) {
        if (!DataUtil.isNullObject(nameVoucher)) {
            return this.voucherRepository.findByStatusAndNameContaining(true, nameVoucher);
        }
        return this.voucherRepository.findByStatus(true);
    }

    public Voucher getById(Long id) {
        Optional<Voucher> optional = this.voucherRepository.findByIdAndStatus(id, true);
        if (optional.isEmpty()) {
            throw new BadRequestException("Không tìm thấy voucher này");
        } else {
            return optional.get();
        }
    }

    public Voucher save(VoucherDTO dto) {
        LocalDateTime currentDateTime = LocalDateTime.now().minusMinutes(2);
        if (dto.getDayStart().isBefore(currentDateTime)) {
            throw new BadRequestException("Ngày hết hạn sớm quá ");
        }
        if (dto.getDayEnd().isBefore(currentDateTime)) {
            throw new BadRequestException("Ngày hết hạn sớm quá ");
        }
        if (dto.getDayEnd().isBefore(dto.getDayStart())) {
            throw new BadRequestException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }
        if (dto.getValue().compareTo(BigDecimal.valueOf(1)) == -1) {
            throw new BadRequestException("Phải luôn lớn hơn 0");
        }
//        ZoneId zoneId = ZoneId.of("GMT+7");
//        ZonedDateTime dateTimeDayStart = ZonedDateTime.of(dto.getDayStart(), zoneId);

        Voucher voucher = this.voucherRepository
                .save(Voucher
                        .builder()
                        .name(dto.getName())
                        .dayEnd(dto.getDayEnd())
                        .dayStart(dto.getDayStart())
                        .value(dto.getValue())
                        .status(true)
                        .build());
        return voucher;

    }

    public Voucher update(VoucherDTO dto) {
        Optional<Voucher> optional = this.voucherRepository.findByIdAndStatus(dto.getId(), true);
        if (optional.isEmpty()) {
            throw new BadRequestException("Không tìm thấy voucher này");
        } else {
            LocalDateTime currentDateTime = LocalDateTime.now().minusMinutes(2);
            if (dto.getDayStart().isBefore(currentDateTime)) {
                throw new BadRequestException("Ngày hết hạn sớm quá ");
            }
            if (dto.getDayEnd().isBefore(currentDateTime)) {
                throw new BadRequestException("Ngày hết hạn sớm quá ");
            }
            if (dto.getDayEnd().isBefore(dto.getDayStart())) {
                throw new BadRequestException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
            }

            Voucher voucher = MapperUtil.map(dto, Voucher.class);
            voucher.setStatus(true);
            Voucher newVoucher = this.voucherRepository.save(voucher);
            return newVoucher;
        }
    }

    public void deleteVoucher(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Voucher voucher = getById(id);
                voucher.setStatus(false);
                this.voucherRepository.save(voucher);
            }
        }
    }

    @Scheduled(cron = "10 * * * * ?")//s m h d m y
    public void UpdateStatusAfterEndDate() {
        List<Voucher> vouchers = getAlls(null);
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Voucher> newVoucher = new ArrayList<>();
        for (Voucher voucher : vouchers) {
            if (voucher.getDayEnd().isBefore(currentDateTime)) {
                voucher.setStatus(false);
                newVoucher.add(voucher);
            }
        }
        this.voucherRepository.saveAll(newVoucher);
    }


}
