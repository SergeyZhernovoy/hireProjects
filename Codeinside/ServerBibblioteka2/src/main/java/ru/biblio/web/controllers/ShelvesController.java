package ru.biblio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.biblio.web.domain.Shelf;
import ru.biblio.web.service.ShelfService;

import java.util.List;

@RestController
@RequestMapping("/shelf")
public class ShelvesController {

    @Autowired
    private ShelfService shelfService;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public void save(@RequestParam String name){
        Shelf shelf = new Shelf();
        shelf.setName(name);
        this.shelfService.saveShelf(shelf);
    }

    @RequestMapping("/all")
    public List<Shelf> getShelves(){
        return this.shelfService.getShelves();
    }


    @RequestMapping("/delete")
    public void delete(@RequestParam Long id){
        this.shelfService.delete(id);
    }

    @RequestMapping("/move-book")
    public void moveBooks(@RequestParam Long shelfFrom, @RequestParam Long shelfTo, @RequestParam Long bookId){

    }


}
