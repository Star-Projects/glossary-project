package com.example.glossaryservice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


public class Definition {

    private Integer id;
    //@notBlank - string length must be greater than 1; ""
    @NotNull(message = "Please enter value for term")
    @Size(max = 20, min = 1, message = "Term must be between 1 and 20 characters")
    private String term;
    @NotBlank(message = "Please enter value for term")
    @Size(max = 20, min = 1, message = "Term must be between 1 and 20 characters")
    private String definition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(term, that.term) &&
                Objects.equals(definition, that.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, term, definition);
    }
}
