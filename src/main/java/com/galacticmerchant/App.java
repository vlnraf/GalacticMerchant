package com.galacticmerchant;


public final class App {
    private App() {}

    public static void main(String[] args) {
        QueryDispatcher queryDispatcher = new QueryDispatcher();
        queryDispatcher.runParser(args[0]);
    }
}
