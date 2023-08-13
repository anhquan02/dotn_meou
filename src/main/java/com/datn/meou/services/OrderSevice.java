package com.datn.meou.services;


import com.datn.meou.entity.*;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.repository.*;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderSevice {

    private final OrdersRepository ordersRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final AccountService accountService;

    private final TransactionRepository transactionRepository;

    private final ProductItemRepository productItemRepository;

    private final OrderItemRepository orderItemRepository;

    private final SizeRepository sizeRepository;
    private final SoleRepository soleRepository;
    private final InsoleRepository insoleRepository;
    private final ColorRepository colorRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public Page<OrderDTO> findAll(OrderDTO dto, Pageable pageable) {
        Page<OrderDTO> pages = this.ordersRepository.findAll(dto, pageable);
        for (OrderDTO orderDTO : pages) {
            BigDecimal a1 = orderDTO.getTotalPrice().setScale(1, BigDecimal.ROUND_HALF_UP);
            orderDTO.setTotalPrice(a1);
            if (!DataUtil.isNullObject(orderDTO.getTypeOrder())) {
                if (orderDTO.getTypeOrder() == 1) {
                    orderDTO.setTypeOrders("Đặt tại Quầy");
                } else {
                    orderDTO.setTypeOrders("Đặt Online");
                }
            }
        }
        return pages;
    }

    public Transaction changeStatusByOrder(Long idOrder, Long idStatus, String note) {
        Account account = this.accountService.getCurrentUser();
        Optional<Orders> orders = this.ordersRepository.findById(idOrder);
        if (orders.isPresent()) {
            Orders orders1 = orders.get();
            orders1.setStatusId(idStatus);
            this.ordersRepository.save(orders1);
            Transaction transaction = Transaction
                    .builder()
                    .orderId(idOrder)
                    .accountId(account.getId())
                    .note(note)
                    .totalPrice(orders1.getTotalPrice())
                    .build();
            this.transactionRepository.save(transaction);
            return transaction;
        }
        throw new BadRequestException("Không tìm thấy id của đơn hàng này");
    }


    public ResponseEntity<?> createOrderByCounterSale(OrderDTO orderDTO, List<ProductItemDTO> productItemDTOS) {
        Account account = this.accountService.getCurrentUser();
        String codeOrder = getCodeForOrder("MEOU1_");

        if (DataUtil.isNullObject(orderDTO.getPaymentMethod())) {
            throw new BadRequestException("Phải nhập phương thức thanh toán");
        }

        if (DataUtil.isNullObject(orderDTO.getPhoneCustomer())) {
            throw new BadRequestException("Phải nhập số điện thoại khách hàng");
        }
        if (DataUtil.isNullObject(orderDTO.getNameCustomer())) {
            throw new BadRequestException("Phải nhập tên khách hàng");
        }

        if (DataUtil.isNullObject(orderDTO.getAddressCustomer())) {
            throw new BadRequestException("Phải nhập địa chỉ khách hàng");
        }

        if (DataUtil.isNullObject(orderDTO.getEmailCustomer())) {
            throw new BadRequestException("Phải nhập email khách hàng");
        }

        Orders orders = Orders
                .builder()
                .accountId(account.getId())
                .note(orderDTO.getNote())
                .paymentMethod(orderDTO.getPaymentMethod())
                .typeOrder(1)
                .code(codeOrder)
                .statusId(1L)
                .voucherId(orderDTO.getVoucherId())
                .addressCustomer(orderDTO.getAddressCustomer())
                .emailCustomer(orderDTO.getEmailCustomer())
                .nameCustomer(orderDTO.getNameCustomer())
                .phoneCustomer(orderDTO.getPhoneCustomer())
                .build();
        Orders ordersNew = this.ordersRepository.save(orders);
        BigDecimal totalPrice = new BigDecimal(0);
        List<OrderItem> orderItems = new ArrayList<>();
        for (ProductItemDTO dto : productItemDTOS) {
            Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatus(dto.getId(), true);
            if (productItem.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của sản phẩm chi tiết này");
            }
            ProductItem item = productItem.get();
            Optional<Size> size = this.sizeRepository.findByIdAndStatus(item.getSizeId(), true);
            if (size.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của size sản phẩm này");
            }

            Optional<Color> color = this.colorRepository.findByIdAndStatus(item.getColorId(), true);
            if (color.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của màu sản phẩm này");
            }
            Optional<Sole> sole = this.soleRepository.findByIdAndStatus(item.getSoleId(), true);
            if (sole.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của đế sản phẩm này");
            }
            Optional<Insole> insole = this.insoleRepository.findByIdAndStatus(item.getInsoleId(), true);
            if (insole.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của dây giày sản phẩm này");
            }
            Optional<Product> product = this.productRepository.findByIdAndStatus(item.getProductId(), true);
            if (product.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của sản phẩm này");
            }
            Optional<Brand> brand = this.brandRepository.findByIdAndStatus(product.get().getBrandId(), true);
            if (brand.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của thương hiệu sản phẩm này");
            }

            if (DataUtil.isNullObject(dto.getQuantity())) {
                throw new BadRequestException("Đề nghị nhập số lượng cần mua");
            }
            if (dto.getQuantity() <= 0) {
                throw new BadRequestException("Số lượng đã hết");
            }
            if (dto.getQuantity() > productItem.get().getQuantity()) {
                throw new BadRequestException("Vượt số lượng trong kho");
            }
            BigDecimal sumProductItem = item.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
            totalPrice = totalPrice.add(sumProductItem);
            OrderItem orderItem = OrderItem
                    .builder()
                    .orderId(ordersNew.getId())
                    .quantityOrder(dto.getQuantity())
                    .productItemId(dto.getId())
                    .priceSell(item.getPrice())
                    .sizeProduct(size.get().getName())
                    .brandProduct(brand.get().getName())
                    .colorProduct(color.get().getName())
                    .insoleProduct(insole.get().getName())
                    .soleProduct(sole.get().getName())
                    .build();
            orderItems.add(orderItem);

        }
        this.orderItemRepository.saveAll(orderItems);
        ordersNew.setTotalPrice(totalPrice);
        this.ordersRepository.save(ordersNew);

        return ResponseUtil.ok("Thêm mới thành công");
    }

    private String getCodeForOrder(String code) {
        List<Orders> ordersCheck = this.ordersRepository.findByCodeContainingOrderByIdDesc(code);
        String codeOld = ordersCheck.get(0).getCode();
        String code1 = codeOld.substring(0, 6);
        String code2 = codeOld.substring(6);
        Integer numberUp = Integer.parseInt(code2);
        numberUp = numberUp + 1;
        String numberNew = numberUp.toString();
        return code1 + numberNew;


    }


}
