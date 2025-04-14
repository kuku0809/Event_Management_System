package com.example.demo.service.payment.validators;

import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;

public class PaymentValidatorChain {
    public static PaymentValidator createDefaultChain(BookingService bookingService, UserService userService) {
        AmountValidator amountValidator = new AmountValidator();
        amountValidator
            .linkWith(new BookingValidator(bookingService))
            .linkWith(new UserValidator(userService))
            .linkWith(new CardPaymentValidator())
            .linkWith(new UpiPaymentValidator());
        return amountValidator;
    }
}