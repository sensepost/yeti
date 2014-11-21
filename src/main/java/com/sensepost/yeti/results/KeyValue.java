package com.sensepost.yeti.results;

public class KeyValue {

    private Object key = null;
    private Object value = null;

    public KeyValue(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public String getStrKey() {
        return key.toString();
    }

    public String getStrValue() {
        return value.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (value != null && !value.toString().isEmpty()) {
            sb.append(value.toString().trim());
            if (key != null && !key.toString().isEmpty()) {
                sb.append(" - ");
            }
        }

        if (key != null && !key.toString().isEmpty()) {
            sb.append(key.toString().trim());
        }

        return sb.toString();
    }
}
