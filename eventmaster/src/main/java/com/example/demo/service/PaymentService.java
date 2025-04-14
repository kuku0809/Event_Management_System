package com.example.demo.service;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.model.*;
import com.example.demo.repository.PaymentRepository;
//import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;
    private final UserService userService; // Add this

    public PaymentService(PaymentRepository paymentRepository, 
                        BookingService bookingService,
                        UserService userService) {
        this.paymentRepository = paymentRepository;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    public Payment processPayment(PaymentRequest paymentRequest) {
        if (paymentRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }

        Booking booking = bookingService.getBookingById(paymentRequest.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        User user = userService.getUserById(paymentRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setBooking(booking);
        payment.setUser(user); // Set the user
        payment.setPaymentDate(LocalDateTime.now());

        String method = paymentRequest.getPaymentMethod().toUpperCase();

        switch (method) {
            case "CARD":
                if (paymentRequest.getCardNumber() == null || paymentRequest.getCardHolderName() == null || !isValidCardPassword(paymentRequest.getCardPassword())) {
                    throw new IllegalArgumentException("Invalid card credentials");
                }
                payment.setPaymentMethod(Payment.PaymentMethod.CARD);
                break;

            case "UPI":
                if (!isValidUpiPin(paymentRequest.getUpiPin())) {
                    throw new IllegalArgumentException("Invalid upi credentials");
                }
                payment.setPaymentMethod(Payment.PaymentMethod.UPI);
                break;

            case "CASH":
                payment.setPaymentMethod(Payment.PaymentMethod.CASH);
                break;

            default:
                throw new IllegalArgumentException("Unsupported payment method");
        }

        payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
        return paymentRepository.save(payment);
    }

    private boolean isValidCardPassword(String password) {
        return password != null && password.length() >= 4;
    }

    
    private boolean isValidUpiPin(String pin) {
        return pin != null && pin.length() == 4;
    }

}

 