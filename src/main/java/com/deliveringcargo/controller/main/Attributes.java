package com.deliveringcargo.controller.main;

import com.deliveringcargo.model.Cargos;
import com.deliveringcargo.model.Category;
import com.deliveringcargo.model.Ordering;
import com.deliveringcargo.model.Users;
import com.deliveringcargo.model.enums.Role;
import org.springframework.ui.Model;

import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected void AddAttributesUsers(Model model) {
        AddAttributes(model);
        model.addAttribute("users", usersRepo.findAll());
        model.addAttribute("roles", Role.values());
    }

    protected void AddAttributesReviews(Model model) {
        AddAttributes(model);
        model.addAttribute("reviews", reviewsRepo.findAll());
    }

    protected void AddAttributesCargoAdd(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
    }

    protected void AddAttributesCargoEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("cargo", cargosRepo.getReferenceById(id));
    }

    protected void AddAttributesCargosMy(Model model) {
        AddAttributes(model);
        Users user = getUser();
        if (user.getRole() == Role.MANAGER) {
            List<Ordering> orderings = orderingRepo.findAll();
            model.addAttribute("orderings", orderings);
        } else {
            model.addAttribute("orderings", user.getOrderings());
        }
    }

    protected void AddAttributesCargo(Model model, Long id) {
        AddAttributes(model);
        Cargos cargo = cargosRepo.getReferenceById(id);
        model.addAttribute("cargo", cargo);
    }

    protected void AddAttributesIndex(Model model) {
        AddAttributes(model);
        model.addAttribute("cargos", cargosRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
    }

    protected void AddAttributesCategory(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
    }

    protected void AddAttributesSearch(Model model, String name, Long categoryId) {
        AddAttributes(model);
        Category category = categoryRepo.getReferenceById(categoryId);
        model.addAttribute("cargos", cargosRepo.findAllByNameContainingAndCategory_Name(name, category.getName()));
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("selectedCId", categoryId);
        model.addAttribute("name", name);
    }

    protected void AddAttributesStats(Model model) {
        AddAttributes(model);
        model.addAttribute("cargos", cargosRepo.findAll());
    }
}
