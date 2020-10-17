package com.example;

import static com.library.Baz.baz;

public class Foo {
    public void foo() {
        System.out.println("foo");
        baz();
        bar();
        new Qux().qux();
        bar();
    }

    public void bar() {
        System.out.println("bar");
    }
}
