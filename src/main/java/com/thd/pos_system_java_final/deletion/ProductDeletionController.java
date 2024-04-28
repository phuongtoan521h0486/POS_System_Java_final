package com.thd.pos_system_java_final.deletion;

import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import com.thd.pos_system_java_final.services.CartService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductDeletionController extends DeletionController {

    private CartService cartService;
    private OrderDetailRepository orderDetailRepository;
    private ProductRepository productRepository;

    @Override
    protected void deleteEntity(int id) {
        if (!cartService.isInOrder(id) && !orderDetailRepository.existsByProductId(id)) {
            productRepository.deleteById(id);
        }

    }

    @Override
    protected String redirectToLink() {
        return "redirect:/product";
    }

}
