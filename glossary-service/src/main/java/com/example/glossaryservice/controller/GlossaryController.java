package com.example.glossaryservice.controller;

import com.example.glossaryservice.domain.Definition;
import com.example.glossaryservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GlossaryController {

    @Autowired
    ServiceLayer serviceLayer;

    // URL: /glossary/term/{term}
    @RequestMapping(value = "/glossary/term/{term}", method = RequestMethod.GET)
    public List<Definition> getDefinitionsByTerm(@PathVariable @Valid String term) {
        return serviceLayer.getDefinitions(term);
    }

    // URL: /glossary
//    @RequestMapping(value = "/glossary", method = RequestMethod.POST)
//    public ResponseEntity<?> addDefinitionNewWay(@RequestBody Definition defn) {
//        Definition definition = serviceLayer.addDefinition(defn);
//        return new ResponseEntity<>(definition, HttpStatus.CREATED);
//    }

    // URL: /glossary
    @RequestMapping(value = "/glossary", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Definition addDefinition(@RequestBody @Valid Definition defn) {
         return serviceLayer.addDefinition(defn);
    }

//    public ResponseEntity<?> addItem(@RequestBody Item item) {
//        Item savedItem = itemBusinessService.addAnItem(item);
//        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
//    }

}
