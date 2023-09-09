package com.datn.meou.controllers;

import com.datn.meou.model.VoucherDTO;
import com.datn.meou.services.VoucherService;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/voucher")
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping()
    private ResponseEntity<?> save(@Valid @RequestBody VoucherDTO dto) {
        return ResponseUtil.ok(this.voucherService.save(dto));
    }

    @PutMapping()
    private ResponseEntity<?> update(@Valid @RequestBody VoucherDTO dto) {
        return ResponseUtil.ok(this.voucherService.update(dto));
    }

    @GetMapping("all-list")
    private ResponseEntity<?> findAllList(@RequestParam(required = false) String nameVoucher) {

        return ResponseUtil.ok(this.voucherService.getAlls(nameVoucher));
    }

    @GetMapping("all-page")
    private ResponseEntity<?> findAllPage(Pageable pageable, @RequestParam(required = false) String nameVoucher) {
        return ResponseUtil.ok(this.voucherService.getAllPage(pageable, nameVoucher));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.append(error.getDefaultMessage()).append(",");
        });
        return ResponseUtil.badRequest(errors.toString());
    }

    @DeleteMapping()
    private ResponseEntity<?> delete(@RequestParam List<Long> ids) {
        this.voucherService.deleteVoucher(ids);
        return ResponseUtil.ok("Xóa thành công");
    }

    @GetMapping("id")
    private ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseUtil.ok(this.voucherService.getById(id));
    }


}
