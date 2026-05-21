package com.company.order_inventory_system.common.model;

import java.util.List;

public class FormField {

    private String name;
    private String placeholder;
    private String type; // text, number, email, datetime-local, select
    private boolean required;
    private List<String> options; // for select options

    public FormField() {
    }

    public FormField(String name, String placeholder, String type, boolean required) {
        this.name = name;
        this.placeholder = placeholder;
        this.type = type;
        this.required = required;
    }

    public FormField(String name, String placeholder, String type, boolean required, List<String> options) {
        this.name = name;
        this.placeholder = placeholder;
        this.type = type;
        this.required = required;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
