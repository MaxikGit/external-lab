package com.epam.esm.order.service;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.certificate.repository.CertificateRepository;
import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.mapper.OrderDtoMapper;
import com.epam.esm.order.model.OrderCertificateModel;
import com.epam.esm.order.model.OrderModel;
import com.epam.esm.order.repository.OrderRepository;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;

    @Override
    public PagedModel<OrderDto> findAll(String sortField, String sortDirection, Integer offset, Integer size) {
        PagedModel<OrderModel> orders = orderRepository.findAll(sortField, sortDirection, offset, size);
        return OrderDtoMapper.INSTANCE.toDto(orders);
    }

    @Override
    public OrderDto findById(int id) {
        return orderRepository.findById(id)
                .map(OrderDtoMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public PagedModel<OrderDto> findByUser(Integer userId, String sortField,
                                           String sortDirection, int offset, int size) {
        if (userId == null){
            return findAll(sortField, sortDirection, offset, size);
        }
        PagedModel<OrderModel> orders = orderRepository
                .findByUser(userId, sortField, sortDirection, offset, size);
        return OrderDtoMapper.INSTANCE.toDto(orders);
    }

    @Override
    public OrderDto placeOrder(Integer userId, Set<InvoiceDto> invoiceDtos) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId));
        Map<GiftCertificateModel, Integer> certificates = getCertificatesByInvoices(invoiceDtos);
        OrderModel order = createOrder(user, certificates);
        orderRepository.save(order);
        return OrderDtoMapper.INSTANCE.toDto(order);
    }

    private Map<GiftCertificateModel, Integer> getCertificatesByInvoices(Set<InvoiceDto> invoiceDtos) {
        Set<Integer> ids = invoiceDtos.stream().map(InvoiceDto::getProductId).collect(Collectors.toSet());
        Map<Integer, GiftCertificateModel> certificates = certificateRepository
                .findByIds(ids)
                .stream().collect(Collectors.toMap(GiftCertificateModel::getId, Function.identity()));
        if (invoiceDtos.size() != certificates.size()) {
            certificates.keySet().forEach(ids::remove);
            ids.stream().findAny().ifPresent(id -> {
                throw new ResourceNotFoundException(id);
            });
        }
        return invoiceDtos.stream()
                .collect(Collectors.toMap(x -> certificates.get(x.getProductId()), InvoiceDto::getQuantity));
    }

    private OrderModel createOrder(UserModel user, Map<GiftCertificateModel, Integer> certificates) {
        BigDecimal totalCost = calculateOrderCost(certificates);
        OrderModel order = new OrderModel(totalCost, user);
        List<OrderCertificateModel> orderCertificateModels = certificates.entrySet().stream()
                .map(x -> new OrderCertificateModel(x.getValue(), order, x.getKey()))
                .toList();
        order.setOrderCertificateModels(orderCertificateModels);
        return order;
    }

    private BigDecimal calculateOrderCost(Map<GiftCertificateModel, Integer> certificates) {
        return certificates.entrySet().stream()
                .map(x -> x.getKey().getPrice()
                        .multiply(BigDecimal.valueOf(x.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void delete(int id) {
        checkIfExist(id);
        orderRepository.delete(id);
    }

    private void checkIfExist(int id) {
        if (!orderRepository.existsById(id))
            throw new ResourceNotFoundException(id);
    }
}
