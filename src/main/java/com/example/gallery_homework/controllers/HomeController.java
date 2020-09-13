package com.example.gallery_homework.controllers;

import com.example.gallery_homework.models.Photo;
import com.example.gallery_homework.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final PhotoRepository photoRepository;

    @Autowired
    public HomeController(PhotoRepository repository){
        this.photoRepository = repository;
    }

    @GetMapping("/")
    public String Home(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String Index(Model model){
        Iterable<Photo> photoList = photoRepository.findAll();
        model.addAttribute("photos", photoList);
        model.addAttribute("pageTitle", "Main");
        return "index";
    }
}
