package com.example.glossaryservice.controller;

import com.example.glossaryservice.domain.Definition;
import com.example.glossaryservice.exception.NotAllowedDefinition;
import com.example.glossaryservice.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GlossaryController.class)
// @ImportAutoConfiguration(RefreshAutoConfiguration.class)
class GlossaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    // variables used for testing purposes
    private List<Definition> definitions;
    private Definition defnToSave;
    private Definition savedDefn;
    private Definition forbiddenDefn;

    @BeforeEach
    void setUp() throws Exception {
        // Standard set up method, for instantiating test objects

        definitions = new ArrayList<>();
        Definition def = new Definition();
        def.setId(11);
        def.setTerm("April");
        def.setDefinition("the fourth month of the year");
        definitions.add(def);

        def = new Definition();
        def.setId(12);
        def.setTerm("April");
        def.setDefinition("the second month in Spring");
        definitions.add(def);

        when(service.getDefinitions("April")).thenReturn(definitions);

        defnToSave = new Definition();
        defnToSave.setTerm("January");
        defnToSave.setDefinition("the first month of the year");

        savedDefn = new Definition();
        savedDefn.setId(13);
        savedDefn.setTerm("January");
        savedDefn.setDefinition("the first month of the year");

        when(service.addDefinition(defnToSave)).thenReturn(savedDefn);

        forbiddenDefn = new Definition();
        forbiddenDefn.setTerm("bad defn");
        forbiddenDefn.setDefinition("a jerk");

        when(service.addDefinition(forbiddenDefn)).thenThrow(new NotAllowedDefinition());
    }

    // testing GET /glossary/term/{term}
    // testing status is 200 OK
    @Test
    void shouldReturnAllDefinitionsForATerm() throws Exception {
        // Convert Java objects to JSON
        String outputJson = mapper.writeValueAsString(definitions);

        mockMvc.perform(get("/glossary/term/{term}", "April").contentType(MediaType.APPLICATION_JSON)) // perform the get request
             .andDo(print())    // print result to console
             .andExpect(status().isOk())    // Assert status code is 200
             .andExpect(content().json(outputJson));    // Assert output is as expected
    }

    @Test
    void shouldReturnEmptyArrayWhenThereAreNoDefnsForTerm() throws Exception {
        String outputJson = mapper.writeValueAsString(new ArrayList());

        mockMvc.perform(get("/glossary/term/{term}", "May").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    // testing POST /glossary
    @Test
    void shouldReturnAllowedDefn() throws Exception {
        String outputJson = mapper.writeValueAsString(savedDefn);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/glossary")
                .content(mapper.writeValueAsString(defnToSave))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    void shouldReturnUserErrorForForbiddenDefn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/glossary")
                .content(mapper.writeValueAsString(forbiddenDefn))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("may not be used")));
    }
}