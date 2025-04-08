package com.insurancePolicy.customerSupport.repository;

import com.insurancePolicy.customerSupport.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Integer> {
}