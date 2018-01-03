package fr.usmb.m1isc.compilation.tp1;

/**
 * Created by florian on 10/11/2017.
 */
public class Arbre {
    private Arbre left;
    private Arbre right;
    private String value;

    public Integer getSymbol() {
        return symbol;
    }

    private Integer symbol;

    public Arbre getLeft() {
        return left;
    }

    public void setLeft(Arbre left) {
        this.left = left;
    }

    public Arbre getRight() {
        return right;
    }

    public void setRight(Arbre right) {
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Arbre(Arbre left, Arbre right, String value, Integer symbol) {

        this.left = left;
        this.right = right;
        this.value = value;
        this.symbol = symbol;
    }
    public static void parcoursProfondeur (Arbre a){
        System.out.print("(");

        System.out.print(a.value);
        if(a.left != null){
            parcoursProfondeur(a.left);
        }
        if(a.right != null){
            parcoursProfondeur(a.right);
        }
        System.out.print(")");
    }

    @Override
    public String toString() {
        parcoursProfondeur(this);
        return "";
    }
}
