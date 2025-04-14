package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.FoodOrderRepository;

import org.springframework.stereotype.Service;

import com.example.demo.commands.CancelBookingCommand;
import com.example.demo.commands.CommandManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final CommandManager commandManager;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                         FoodOrderRepository foodOrderRepository,
                         CommandManager commandManager) {
        this.bookingRepository = bookingRepository;
        this.foodOrderRepository = foodOrderRepository;
        this.commandManager = commandManager;
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    public List<String> getBookedSeatsForEvent(Long eventId) {
    return bookingRepository.findByEventId(eventId)
            .stream()
            .filter(booking -> booking.getSeatNumbers() != null)
            .flatMap(booking -> Arrays.stream(booking.getSeatNumbers().split(",")))
            .collect(Collectors.toList());
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        CancelBookingCommand command = new CancelBookingCommand(
            booking, 
            bookingRepository,
            foodOrderRepository
        );
        commandManager.executeCommand(command);
    }
}