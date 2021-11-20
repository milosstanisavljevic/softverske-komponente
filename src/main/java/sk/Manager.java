package sk;

import sk.SpecifikacijaSkladista;

public class Manager {

    private static SpecifikacijaSkladista specifikacijaSkladista;

    public static void registerImpl(SpecifikacijaSkladista spec){
        specifikacijaSkladista = spec;
    }
    public static SpecifikacijaSkladista getImpl(){
        return specifikacijaSkladista;
    }
}
