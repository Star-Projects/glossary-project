package com.example.glossaryservice.service;

import com.example.glossaryservice.domain.Definition;
import com.example.glossaryservice.exception.NotAllowedDefinition;
import com.example.glossaryservice.util.feign.DefinitionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLayer {

    private static final String[] FORBIDDEN_WORDS =  {"darn", "drat", "heck", "jerk", "butt"};

    private DefinitionClient definitionClient;

    @Autowired
    public ServiceLayer(DefinitionClient definitionClient){
        this.definitionClient = definitionClient;
    }

    //Get list of definitions by term
    public List<Definition> getDefinitions(String term){
        List<Definition> definitions = definitionClient.getDefinitionsForTerm(term);
        return definitions;
    }

    //add a definition term and definition
    public Definition addDefinition(Definition definition) throws NotAllowedDefinition {
        if (checkIfDefinitionIsClean(definition.getDefinition())) {
            return definitionClient.addDefinition(definition);
        } else {
            throw new NotAllowedDefinition();
        }
    }


    public boolean checkIfDefinitionIsClean(String toCheck) {
        for (String word : FORBIDDEN_WORDS) {
            //if the string toCheck contains any of the forbidden words this method returns false.

            // the pattern represented by the string .*\\b
            // . is ANY CHARACTER
            // * is ZERO OR MORE OF THE PREVIOUS CHARACTER
            // \\b is a word boundary (like a space, a tab, or punctuation)
            if (toCheck.toLowerCase().matches(".*\\b" + word.toLowerCase() + "\\b.*")) {

                return false;
            }
        }
        return true;
    }

}
