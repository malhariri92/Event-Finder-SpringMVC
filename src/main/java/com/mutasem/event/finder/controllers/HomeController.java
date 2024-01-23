package com.mutasem.event.finder.controllers;

import com.mutasem.event.finder.models.Event;
import com.mutasem.event.finder.models.User;
import com.mutasem.event.finder.services.EventService;
import com.mutasem.event.finder.services.SeatGeekService;
import com.mutasem.event.finder.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping()
public class HomeController {
    private SeatGeekService seatGeekService;
    private UserService userService;
    private EventService eventService;
    @Value("${categories}")
    List<String> categories;

    @Autowired
    public HomeController(SeatGeekService seatGeekService, UserService userService, EventService eventService) {
        this.seatGeekService = seatGeekService;
        this.userService = userService;
        this.eventService = eventService;
    }
    @GetMapping("/home")
    public String home(@RequestParam(name = "selectedCategory", required = false) String selectedCategory,
                       @RequestParam(name = "query", required = false) String query, Model model) {
        List<Event> events = seatGeekService.allEvents(query);

        // Filter events based on the selected category
        if (!"All".equals(selectedCategory)) {
            events = seatGeekService.filterEvents(selectedCategory, events);
        }
        model.addAttribute("query", query);
        model.addAttribute("events", events);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", selectedCategory);

        return "home";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable int id, Model model) {
        Event event = seatGeekService.getEventById(id);
        if(event == null) {
            event = eventService.findEventById(id);
        }
        if(event != null) {
            model.addAttribute("event", event);
        }
        return "details";
    }

    @GetMapping("/saveEvent")
    public String saveUserEvent(@RequestParam("eventId") int eventId, HttpSession session,
                                Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        Event event = seatGeekService.getEventById(eventId);
        if(event == null) {
            event = eventService.findEventById(eventId);
        }

        if(user.hasEvent(event.getId())){
            user.getEvents().remove(event);
        }
        else {
            eventService.updateEvent(event);
            user.addEvent(event);
        }

        userService.updateUser(user);
        session.setAttribute("user", user);
        redirectAttributes.addFlashAttribute("eventSaved", true);
        return "redirect:/details/" + eventId;
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
