package com.sc2002.battle.domain;

import java.util.Objects;

public abstract class AbstractStatusEffect implements StatusEffect {
    private final String name;

    protected AbstractStatusEffect(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    @Override
    public String getName() {
        return name;
    }
}
