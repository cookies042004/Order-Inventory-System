package com.company.order_inventory_system.common.model;

import java.util.List;

public class TeamMember {

    private String name;

    private String role;

    private String image;

    private List<ModuleLink> modules;

    public TeamMember() {
    }

    public TeamMember(
            String name,
            String role,
            String image,
            List<ModuleLink> modules
    ) {
        this.name = name;
        this.role = role;
        this.image = image;
        this.modules = modules;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public List<ModuleLink> getModules() {
        return modules;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setImage(String image) {
        this.image = image;
    }
}