package org.example.web.dto;

import jakarta.validation.constraints.NotEmpty;

// получает только id от как у объекта Book
public class BookIdToRemove {

    // @NotEmpty Аннотация используется для проверки, что поле или параметр метода не null
    // и имеет длину больше нуля (не пусто)
    @NotEmpty
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
