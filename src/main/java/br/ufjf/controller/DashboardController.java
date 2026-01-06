package br.ufjf.controller;

import br.ufjf.model.User;

public interface DashboardController<T extends User> {
    void setUser(T user);
}
