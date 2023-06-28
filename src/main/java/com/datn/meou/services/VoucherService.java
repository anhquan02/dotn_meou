package com.datn.meou.services;

import com.datn.meou.entity.Voucher;
import com.datn.meou.repository.VoucherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    public Page<Voucher> getAllVouchers(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }

    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid voucher Id: " + id));
    }
    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public void updateVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }
    public Page<Voucher> searchVouchersByName(String name, Pageable pageable) {
        return voucherRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}
