package model;

import java.util.Objects;

public class Vertex {
    private static int idCounter = 0;
    private final int id;
    private String label;

    public Vertex(String label) {
        this.id = idCounter++;
        this.label = label;
    }

    public Vertex() {
        this.id = idCounter++;
        this.label = "Vertex " + id;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label; // Возвращаем только метку для удобства отображения в ComboBox
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return id == vertex.id && Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }
}
