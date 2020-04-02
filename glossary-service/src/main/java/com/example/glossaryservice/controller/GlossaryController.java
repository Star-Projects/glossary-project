package com.example.glossaryservice.controller;

import com.example.glossaryservice.domain.Definition;
import com.example.glossaryservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GlossaryController {

    @Autowired
    ServiceLayer serviceLayer;

    // URL: /glossary/term/{term}
    @RequestMapping(value = "/glossary/term/{term}", method = RequestMethod.GET)
    public List<Definition> getDefinitionsByTerm(@PathVariable String term) {
        return serviceLayer.getDefinitions(term);
    }

    // URL: /glossary
    @RequestMapping(value = "/glossary", method = RequestMethod.POST)
    public Definition addDefinition(@RequestBody Definition defn) {
        return serviceLayer.addDefinition(defn);
    }
}
