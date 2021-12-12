package com.sineguzov.example.pr2;

public class Main {
    public static void main(String[] args) {
        TestJava tj = new TestJava();
        Long v = 11L;
        if (tj.getId() != 11 || tj.getId() == null) {
            System.out.println("");
        }
    }
}

class TestJava {
    Long id;
    String name;

    public TestJava() {
        this.id = null;
        this.name = "Dima";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
