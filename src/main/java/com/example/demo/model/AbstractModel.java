package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class AbstractModel {
    private UUID userId;

    public AbstractModel(UUID userId) {
        this.userId = userId;
    }
}
