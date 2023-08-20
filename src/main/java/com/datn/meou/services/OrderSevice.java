package com.datn.meou.services;


import com.datn.meou.entity.*;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ChangeStatus;
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

    private final TransactionStatusRepository transactionStatusRepository;

    private final ImageRepository imageRepository;

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

    public TransactionStatus changeStatusByOrder(ChangeStatus changeStatus) {
        Account account = this.accountService.getCurrentUser();
        Optional<Orders> orders = this.ordersRepository.findById(changeStatus.getIdOrder());
        if (orders.isPresent()) {
            Orders orders1 = orders.get();
            orders1.setStatusId(changeStatus.getIdStatus());
            this.ordersRepository.save(orders1);
            TransactionStatus transactionStatus = TransactionStatus
                    .builder()
                    .orderId(changeStatus.getIdOrder())
                    .accountId(account.getId())
                    .note(changeStatus.getNote())
                    .statusId(changeStatus.getIdStatus())
                    .build();
            this.transactionStatusRepository.save(transactionStatus);
            return transactionStatus;
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
                .statusId(5L)
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
            Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatusGreaterThan(dto.getId(), 0);
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
            Optional<Product> product = this.productRepository.findByIdAndStatusGreaterThan(item.getProductId(), 0);
            if (product.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của sản phẩm này");
            }
            Optional<Brand> brand = this.brandRepository.findByIdAndStatus(item.getBrandId(), true);
            if (brand.isEmpty()) {
                throw new BadRequestException("Không tìm thấy id của thương hiệu sản phẩm này");
            }

            List<Image> images = this.imageRepository.findAllByProductItemId(productItem.get().getId());
            if (DataUtil.isNullObject(images)) {
                throw new BadRequestException("Không tìm thấy ảnh của sản phẩm này");
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
                    .nameProduct(item.getName())
                    .sizeProduct(size.get().getName())
                    .brandProduct(brand.get().getName())
                    .colorProduct(color.get().getName())
                    .insoleProduct(insole.get().getName())
                    .soleProduct(sole.get().getName())
                    .image(images.get(0).getName())
                    .build();
            orderItems.add(orderItem);
            item.setQuantity(item.getQuantity() - dto.getQuantity());
            this.productItemRepository.save(item);
        }
        this.orderItemRepository.saveAll(orderItems);
        ordersNew.setTotalPrice(totalPrice);
        this.ordersRepository.save(ordersNew);
        this.transactionRepository
                .save(Transaction
                        .builder()
                        .orderId(ordersNew.getId())
                        .accountId(account.getId())
                        .totalPrice(ordersNew.getTotalPrice())
                        .build());
        List<TransactionStatus> transactionStatuses = new ArrayList<>();
        for (Long i = 1L; i < 5L; i++) {
            TransactionStatus transactionStatus = TransactionStatus
                    .builder()
                    .accountId(account.getId())
                    .orderId(ordersNew.getId())
                    .statusId(i)
                    .build();
            transactionStatuses.add(transactionStatus);
        }
        this.transactionStatusRepository.saveAll(transactionStatuses);

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
