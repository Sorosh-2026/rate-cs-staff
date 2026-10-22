package ca.sfu.ratecsstaff.controller;

import ca.sfu.ratecsstaff.RoleType;
import ca.sfu.ratecsstaff.StaffRating;
import ca.sfu.ratecsstaff.repository.StaffRatingRepository;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class StaffRatingController {

    private final StaffRatingRepository repository;

    public StaffRatingController(StaffRatingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("ratings", repository.findAll());
        return "index";
    }

    @GetMapping("/ratings/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<StaffRating> rating = repository.findById(id);

        if (rating.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("rating", rating.get());
        return "detail";
    }

    @GetMapping("/ratings/new")
    public String createForm(Model model) {
        model.addAttribute("rating", new StaffRating());
        model.addAttribute("roles", RoleType.values());
        return "form";
    }

    @PostMapping("/ratings")
    public String create(
            @Valid @ModelAttribute("rating") StaffRating rating,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("roles", RoleType.values());
            return "form";
        }

        if (repository.existsByEmail(rating.getEmail())) {
            result.rejectValue(
                "email", "duplicate",
                "This email is already being used."
            );
            model.addAttribute("roles", RoleType.values());
            return "form";
        }

        try {
            StaffRating saved = repository.save(rating);
            return "redirect:/ratings/" + saved.getId();
        } catch (DataIntegrityViolationException exception) {
            result.rejectValue(
                "email", "duplicate",
                "This email is already being used."
            );
            model.addAttribute("roles", RoleType.values());
            return "form";
        }
    }

    @GetMapping("/ratings/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<StaffRating> rating = repository.findById(id);

        if (rating.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("rating", rating.get());
        model.addAttribute("roles", RoleType.values());
        return "form";
    }

    @PostMapping("/ratings/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("rating") StaffRating formRating,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<StaffRating> existing = repository.findById(id);

        if (existing.isEmpty()) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            formRating.setName(formRating.getName());
            model.addAttribute("roles", RoleType.values());
            return "form";
        }

        StaffRating rating = existing.get();

        if (repository.existsByEmail(formRating.getEmail())
                && !rating.getEmail().equalsIgnoreCase(
                    formRating.getEmail())) {
            result.rejectValue(
                "email", "duplicate",
                "This email is already being used."
            );
            model.addAttribute("roles", RoleType.values());
            return "form";
        }

        rating.setName(formRating.getName());
        rating.setEmail(formRating.getEmail());
        rating.setRoleType(formRating.getRoleType());
        rating.setClarity(formRating.getClarity());
        rating.setNiceness(formRating.getNiceness());
        rating.setKnowledgeableScore(
            formRating.getKnowledgeableScore()
        );
        rating.setComment(formRating.getComment());

        try {
            repository.save(rating);
            return "redirect:/ratings/" + id;
        } catch (DataIntegrityViolationException exception) {
            result.rejectValue(
                "email", "duplicate",
                "This email is already being used."
            );
            model.addAttribute("roles", RoleType.values());
            return "form";
        }
    }

    @PostMapping("/ratings/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }

        return "redirect:/";
    }
}
