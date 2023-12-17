package com.deliveringcargo.controller;

import com.deliveringcargo.controller.main.Attributes;
import com.deliveringcargo.model.*;
import com.deliveringcargo.model.enums.StatusOrdering;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/cargos")
public class CargosCont extends Attributes {

    @GetMapping("/add")
    public String cargoAdd(Model model) {
        AddAttributesCargoAdd(model);
        return "cargoAdd";
    }

    @GetMapping("/my")
    public String orderingsMy(Model model) {
        AddAttributesCargosMy(model);
        return "cargosMy";
    }

    @GetMapping("/notconf/{id}")
    public String orderingNotConf(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.setStatusOrdering(StatusOrdering.NOT_CONF);
        orderingRepo.save(ordering);
        return "redirect:/cargos/my";
    }

    @GetMapping("/conf/{id}")
    public String orderingConf(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.setStatusOrdering(StatusOrdering.CONF);
        orderingRepo.save(ordering);
        return "redirect:/cargos/my";
    }

    @GetMapping("/done/{id}")
    public String orderingDone(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.setStatusOrdering(StatusOrdering.DONE);
        ordering.getCargo().setCount(ordering.getCargo().getCount() + 1);
        orderingRepo.save(ordering);
        return "redirect:/cargos/my";
    }

    @GetMapping("/edit/{id}")
    public String cargoEdit(Model model, @PathVariable Long id) {
        AddAttributesCargoEdit(model, id);
        return "cargoEdit";
    }

    @GetMapping("/delete/{id}")
    public String cargoDelete(@PathVariable Long id) {
        List<Ordering> orderings = orderingRepo.findAllByCargo_Id(id);
        for (Ordering i : orderings) {
            orderingRepo.deleteById(i.getId());
        }
        cargosRepo.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/ordering/{cargoId}")
    public String cargoOrdering(@RequestParam String address, @PathVariable Long cargoId) {
        Users client = getUser();
        Cargos cargo = cargosRepo.getReferenceById(cargoId);

        Ordering ordering = new Ordering(client, cargo, address);

        orderingRepo.save(ordering);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String cargoAddNew(Model model, @RequestParam String name, @RequestParam Long categoryId, @RequestParam MultipartFile photo, @RequestParam int price, @RequestParam String description) {
        String res = "";
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "cargos/" + uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesCargoAdd(model);
                return "cargoAdd";
            }
        }

        cargosRepo.save(new Cargos(name, res, price, description,categoryRepo.getReferenceById(categoryId)));

        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String cargoEditOld(Model model, @RequestParam String name, @RequestParam Long categoryId, @RequestParam MultipartFile photo, @RequestParam int price, @RequestParam String description, @PathVariable Long id) {
        Cargos cargo = cargosRepo.getReferenceById(id);
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "cargos/" + uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesCargoEdit(model, id);
                return "cargoEdit";
            }
            cargo.setPhoto(res);
        }

        cargo.setName(name);
        cargo.setPrice(price);
        cargo.setDescription(description);
        cargo.setCategory(categoryRepo.getReferenceById(categoryId));

        cargosRepo.save(cargo);

        return "redirect:/";
    }
}
