package com.datn.meou.services;


import com.datn.meou.entity.*;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ChangeStatus;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.repository.*;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.DateUtil;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.text.ParseException;

@Service
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

    private final EmailService emailService;

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
            TransactionStatus transactionStatus = TransactionStatus.builder().orderId(changeStatus.getIdOrder()).accountId(account.getId()).note(changeStatus.getNote()).statusId(changeStatus.getIdStatus()).build();
            this.transactionStatusRepository.save(transactionStatus);
            return transactionStatus;
        }
        throw new BadRequestException("Không tìm thấy id của đơn hàng này");
    }


    public ResponseEntity<?> createOrderByCounterSale(OrderDTO orderDTO, List<ProductItemDTO> productItemDTOS) {
        Account account = this.accountService.getCurrentUser();
        String codeOrder = getCodeForOrder("MEOU1_");
        Orders orders = Orders.builder()
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
        handleOrderItemForPurchase(productItemDTOS, ordersNew);
        this.ordersRepository.save(ordersNew);
        this.transactionRepository.save(Transaction
                .builder()
                .orderId(ordersNew.getId())
                .accountId(account.getId())
                .totalPrice(ordersNew.getTotalPrice())
                .build());
        List<TransactionStatus> transactionStatuses = new ArrayList<>();
        for (Long i = 1L; i < 6L; i++) {
            TransactionStatus transactionStatus = TransactionStatus.builder()
                    .accountId(account.getId())
                    .orderId(ordersNew.getId())
                    .statusId(i)
                    .build();
            transactionStatuses.add(transactionStatus);
        }
        this.transactionStatusRepository.saveAll(transactionStatuses);

        return ResponseUtil.ok("Thêm mới thành công");
    }


    public ResponseEntity<?> createOrderOnline(OrderDTO orderDTO, List<ProductItemDTO> productItemDTOS) {
        Account account;
        String codeOrder = getCodeForOrder("MEOU2_");
        Transaction transaction = Transaction
                .builder()
                .build();
        TransactionStatus transactionStatus = TransactionStatus.builder().build();
        Orders orders = Orders
                .builder()
                .note(orderDTO.getNote())
                .paymentMethod(orderDTO.getPaymentMethod())
                .typeOrder(2)
                .code(codeOrder)
                .statusId(1L)
                .voucherId(orderDTO.getVoucherId())
                .addressCustomer(orderDTO.getAddressCustomer())
                .emailCustomer(orderDTO.getEmailCustomer())
                .nameCustomer(orderDTO.getNameCustomer())
                .phoneCustomer(orderDTO.getPhoneCustomer())
                .build();
        try {
            account = this.accountService.getCurrentUser();
            if (account != null) {
                orders.setAccountId(account.getId());
                transaction.setAccountId(account.getId());
                transactionStatus.setAccountId(account.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Orders ordersNew = this.ordersRepository.save(orders);
        handleOrderItemForPurchase(productItemDTOS, ordersNew);
        this.ordersRepository.save(ordersNew);
        transaction.setOrderId(ordersNew.getId());
        transaction.setTotalPrice(ordersNew.getTotalPrice());
        this.transactionRepository.save(transaction);

        transactionStatus.setStatusId(1L);
        transactionStatus.setOrderId(ordersNew.getId());
        this.transactionStatusRepository.save(transactionStatus);

        String sub = "Thông báo đơn hàng " + ordersNew.getCode();
        String text1 = generateReportMessage(ordersNew, productItemDTOS);
        if (!DataUtil.isNullObject(orderDTO.getEmailCustomer())) {
            this.emailService.sendEmail(orderDTO.getEmailCustomer(), sub, text1);
        }
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

    private void handleOrderItemForPurchase(List<ProductItemDTO> productItemDTOS, Orders ordersNew) {
        BigDecimal totalPrice = new BigDecimal(0);
        List<OrderItem> orderItems = new ArrayList<>();
        for (ProductItemDTO dto : productItemDTOS) {
            Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatusGreaterThan(dto.getId(), 0);
            if (productItem.isEmpty()) {
                throw new BadRequestException("Cần thêm sản phẩm chi tiết này");
            }
            ProductItem item = productItem.get();
            Optional<Size> size = this.sizeRepository.findByIdAndStatus(item.getSizeId(), true);
            if (size.isEmpty()) {
                throw new BadRequestException("Cần thêm size sản phẩm này");
            }

            Optional<Color> color = this.colorRepository.findByIdAndStatus(item.getColorId(), true);
            if (color.isEmpty()) {
                throw new BadRequestException("Cần thêm màu sản phẩm này");
            }
            Optional<Sole> sole = this.soleRepository.findByIdAndStatus(item.getSoleId(), true);
            if (sole.isEmpty()) {
                throw new BadRequestException("Cần thêm đế sản phẩm này");
            }
            Optional<Insole> insole = this.insoleRepository.findByIdAndStatus(item.getInsoleId(), true);
            if (insole.isEmpty()) {
                throw new BadRequestException("Cần thêm dây giày sản phẩm này");
            }
            Optional<Product> product = this.productRepository.findByIdAndStatusGreaterThan(item.getProductId(), 0);
            if (product.isEmpty()) {
                throw new BadRequestException("Cần thêm sản phẩm này");
            }

            List<Image> images = this.imageRepository.findAllByProductItemId(productItem.get().getId());
            if (images.size() < 1) {
                throw new BadRequestException("Cần thêm ảnh cho sản phẩm này");
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
    }

    private StringBuilder generateCommonHtmlHead() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("<body>")
                .append("<div style=\"max-width: 500px;\">")
                .append("  <h2 style=\"text-align: center;\">Thông tin đơn hàng</h2>")
                .append(" <div style=\"display: flex; max-width: 500px;\">")
                .append("   <div style=\"width: 50%;\">");
    }

    public String generateReportMessage(Orders billSave, List<ProductItemDTO> productBills) {
        StringBuilder text = generateCommonHtmlHead();
        String creatDate = billSave.getCreatedDate().toString();
        String dateMain;
        try {
            Date date = DateUtil.inputFormat.parse(creatDate);
            dateMain = DateUtil.sdtf.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        text.append(" <div style=\"margin-top: 5px;\">")
                .append("   <b style=\"margin-right: 10px;\">Số đơn hàng:" + billSave.getCode() + "</b>")
                .append(" </div>");

        text.append(" <div style=\"margin-top: 5px;\">")
                .append("   <b style=\"margin-right: 10px;\">Ngày:" + dateMain + "</b>")
                .append(" </div>");

        text.append(" <div style=\"margin-top: 5px;\">")
                .append("   <b style=\"margin-right: 10px;\">Tên khách hàng:" + billSave.getNameCustomer() + "</b>")
                .append(" </div>");


        text.append(" <div style=\"margin-top: 5px;\">")
                .append("   <b style=\"margin-right: 10px;\">Số điện thoại:" + billSave.getPhoneCustomer() + "</b>")
                .append(" </div>");
        if (billSave.getEmailCustomer() != null) {
            text.append(" <div style=\"margin-top: 5px;\">")
                    .append("   <b style=\"margin-right: 10px;\">Email:" + billSave.getEmailCustomer() + "</b>")
                    .append(" </div>");
        }
        text.append(" <div style=\"margin-top: 5px;\">")
                .append("   <b style=\"margin-right: 10px;\">Địa chỉ:" + billSave.getAddressCustomer() + "</b>")
                .append(" </div>");

        text.append(" <div style=\"margin-top: 5px;\">")
                .append("   <b style=\"margin-right: 10px;\">Hình thức thanh toán:" + billSave.getPaymentMethod() + "</b>")
                .append(" </div>");


        if (billSave.getNote() != null) {
            text.append(" <div style=\"margin-top: 5px;\">")
                    .append("   <b style=\"margin-right: 10px;\">Lời nhắn(nếu có):" + billSave.getNote() + "</b>")
                    .append(" </div>");
        }
        text.append("</div>");
        text.append("<div style=\"width: 50%;\">")
                .append("    <table style=\"background-color: gray; margin-left: auto;\">")
                .append("   <tr>\n" +
                        "                        <th style=\"background-color: white;\">Tên sản phẩm</th>\n" +
                        "                        <th style=\"background-color: white;\">Số lượng</th>\n" +
                        "                        <th style=\"background-color: white;\">Thành tiền</th>\n" +
                        "                    </tr>");
        for (ProductItemDTO mark : productBills) {
            Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatusGreaterThan(mark.getId(), 0);
            if (productItem.isEmpty()) {
                throw new BadRequestException("Không tìm thấy sản phẩm này");
            } else {
                text.append("<tr>")
                        .append("<td>").append(productItem.get().getName()).append("</td>")
                        .append("<td>").append(mark.getQuantity()).append("</td>")
                        .append("<td>").append((productItem.get().getPrice().multiply(BigDecimal.valueOf(mark.getQuantity())))).append("</td>")
                        .append("</tr>");
            }
        }

        text.append("    <div style=\"text-align: end; margin-top: 5px;\"> Tổng cộng: " + billSave.getTotalPrice() + "</div>");
//        BigDecimal pricePay = billSave.getTotalPrice();
//        String formattedNumber = String.format("%.1f", pricePay);
//        text.append("    <div style=\"text-align: end; margin-top: 5px;\"> Thanh toán: " + formattedNumber + "</div>");
        text.append("  </table>");
        text.append(" </div>");
        text.append(" </div>");
        text.append(" </div>");
        text.append("</body>");

        return text.toString();
    }


}
