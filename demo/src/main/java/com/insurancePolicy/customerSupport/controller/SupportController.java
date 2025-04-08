package com.insurancePolicy.customerSupport.controller;
import com.insurancePolicy.entity.SupportTicket;
import com.insurancePolicy.customerSupport.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private SupportService supportService;

    @GetMapping("/")
    public String showSupportHome() {
        return "supportHome";
    }

    @GetMapping("/createTicket")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("supportTicket", new SupportTicket());
        return "createTicket";
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute("supportTicket") SupportTicket supportTicket, Model model) {
        supportTicket.setTicketStatus(SupportTicket.TicketStatus.OPEN);
        supportTicket.setCreatedDate(new Date());
        SupportTicket savedTicket = supportService.createTicket(supportTicket);
        model.addAttribute("ticket", savedTicket);
        model.addAttribute("message", "Ticket created successfully! Your Ticket ID is " + savedTicket.getTicketId());
        return "ticketDetailsView";
    }

    @GetMapping("/ticketDetails")
    public String showTicketDetailsForm() {
        return "ticketDetailsForm";
    }

    @PostMapping("/ticketDetails")
    public String getTicketDetails(@RequestParam(value="id") int id, Model model) {
        try {
            SupportTicket ticket = supportService.getTicketDetails(id);
            if (ticket != null) {
                model.addAttribute("ticket", ticket);
                return "ticketDetailsView";
            } else {
                model.addAttribute("error", "Ticket ID not found.");
                return "ticketDetailsForm";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", "An error occurred while fetching ticket details.");
            return "ticketDetailsForm";
        }
    }

    @GetMapping("/resolveTicket")
    public String showResolveTicketForm() {
        return "resolveTicketForm";
    }

    @PostMapping("/resolve")
    public String resolveTicket(@RequestParam(value="id") int id, Model model) {
        try {
            SupportTicket resolvedTicket = supportService.resolveTicket(id);
            model.addAttribute("ticket", resolvedTicket);
            model.addAttribute("message", "Ticket resolved successfully!");
            return "ticketDetailsView";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Ticket ID not found or could not be resolved.");
            return "resolveTicketForm";
        }
    }

    @GetMapping("/getAllTickets")
    public String getAllTickets(Model model) {
        model.addAttribute("tickets", supportService.getAllTickets());
        return "allTicketsView";
    }
}
