package com.example.glossaryservice.service;

import com.example.glossaryservice.domain.Definition;
import com.example.glossaryservice.exception.NotAllowedDefinition;
import com.example.glossaryservice.util.feign.DefinitionClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ServiceLayer service;
    DefinitionClient client;

    @BeforeEach
    void setUp() throws Exception{
        setUpDefinitionClientMock();
        service = new ServiceLayer(client);
    }


    @Test
    void getDefinitions() {
        List<Definition> fromService = service.getDefinitions("April");
        assertEquals(fromService.size(), 2);

        Definition def1 = new Definition();
        def1.setId(10);
        def1.setTerm("April");
        def1.setDefinition("A fourth month in the calendar");

        assertEquals(fromService.get(0), def1);

    }

    @Test
    void addDefinition() {

        Definition defInput = new Definition();
        defInput.setTerm("October");
        defInput.setDefinition("Tenth month of a calendar year");
        Definition fromService = client.addDefinition(defInput);

        assertEquals(fromService.getId(), 111);
        assertFalse(fromService.equals(defInput));
        assertFalse(fromService.getId().equals(222));
    }


    //this test is to check that the exception is thrown
    @Test
    void prohibitedDefinitionIsRejected(){
        //assert
        assertThrows(NotAllowedDefinition.class, () ->{
            //Arrange
            Definition word = new Definition();
            word.setTerm("a term");
            word.setDefinition("this is to test exception, Jerk!");
            //act
            service.addDefinition(word);
        });
    }




    @Test
    void ifForbiddenWordIsUsed(){
        //Arrange
        Definition word1 = new Definition();
        word1.setTerm("some term");
        word1.setDefinition("this is some darn test!");
        //Act
        Boolean def = service.checkIfDefinitionIsClean(word1.getDefinition());
        //Assert
        assertFalse(def.booleanValue());
    }

    @Test
    void ifAllowedWordIsUsed(){
        //Arrange
        Definition word = new Definition();
        word.setTerm("Abraham Lincoln");
        word.setDefinition("When Abraham Lincoln was elected President in 1860, seven slave states left the Union to form the Confederate States of America");
        //Act
        Boolean def = service.checkIfDefinitionIsClean(word.getDefinition());
        //Assert
        assertTrue(def.booleanValue());
        //assertEquals(true, def.booleanValue());
        //assertFalse(def.booleanValue()); this was sanity check to make sure that in this case test fails

    }

    @Test
    void checksIfForbiddenWordIsCaseSensitive(){
        //Arrange
        Definition def = new Definition();
        def.setTerm("Case sensitive");
        def.setDefinition("Check if case sensitive passes: what the hEcK");
        //Act
        Boolean whatIGot = service.checkIfDefinitionIsClean(def.getDefinition());
        //Assert
        assertFalse(whatIGot.booleanValue());
    }

    @Test
    void checksIfForbiddenWordIsAPartOfAnotherWord(){
        //Arrange
        Definition def = new Definition();
        def.setTerm("term");
        def.setDefinition("term with butter");
        //Act
        Boolean whatIGot = service.checkIfDefinitionIsClean(def.getDefinition());
        //Assert
        assertTrue(whatIGot.booleanValue());
        //assertEquals(true, whatIGot.booleanValue());
    }


    public void setUpDefinitionClientMock (){
        client = mock(DefinitionClient.class);

        List<Definition> definitions = new ArrayList<>();

        Definition def = new Definition();

        def.setId(10);
        def.setTerm("April");
        def.setDefinition("A fourth month in the calendar");
        definitions.add(def);

        def = new Definition();
        def.setId(11);
        def.setTerm("April");
        def.setDefinition("Second month of spring.");
        definitions.add(def);

        doReturn(definitions).when(client).getDefinitionsForTerm("April");

        Definition defInput = new Definition();
        defInput.setTerm("October");
        defInput.setDefinition("Tenth month of a calendar year");

        Definition defOutput = new Definition();
        defOutput.setId(111);
        defOutput.setTerm("October");
        defOutput.setDefinition("Tenth month of a calendar year");

        doReturn(defOutput).when(client).addDefinition(defInput);
    }
}