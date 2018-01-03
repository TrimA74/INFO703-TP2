package fr.usmb.m1isc.compilation.tp1;
import java_cup.runtime.Symbol;

import java.util.ArrayList;

public class GenerateurASM {
    private Arbre a;
    private String codeASM;
    private String dataASM;
    private ArrayList<String> variables;
    private Integer boolCPT;

    @Override
    public String toString() {
        return codeASM;
    }

    public void generateDataASM (){
        this.dataASM = "DATA SEGMENT\n";

        for (String var: variables) {
            dataASM += "\t " + var + " DD\n";

        }

        this.dataASM += "DATA ENDS\n";

    }

    public GenerateurASM(Arbre a) {
        this.variables = new ArrayList<String>();
        this.a = a;
        this.boolCPT=0;
        this.codeASM = "CODE SEGMENT\n";
        generateur(a);
        this.codeASM += "CODE ENDS\n";
        generateDataASM();
        codeASM = dataASM + codeASM;
        System.out.println(codeASM);

    }

    public void generateur(Arbre arbre){
        switch (arbre.getSymbol()){
            case  sym.SEMI:
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }
                break;
            case sym.LET:

                /// on ajoute la nouvelle variable
                variables.add(arbre.getLeft().getValue());
                // on va calculer la partie droite
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }
                // on pop le résultat de la partie droite et on le mov dans notre nouvelle variable
                codeASM += "pop eax\n";
                codeASM += "mov " + arbre.getLeft().getValue() + ", " + "eax\n";
                break;
            case sym.MUL:
                ArithmeticOperation("mul",arbre);
                break;
            case sym.PLUS:
                ArithmeticOperation("add",arbre);
                break;
            case sym.MOINS:
                ArithmeticOperation("sub",arbre);
                break;
            case sym.DIV:
                ArithmeticOperation("div",arbre);
                break;
            case sym.ENTIER:
                codeASM += "mov eax,"+ arbre.getValue() + "\n";
                codeASM += "push eax\n";
                break;
            case sym.OUTPUT:
                codeASM += "mov eax,"+arbre.getValue()+ "\n";
                codeASM += "out eax" + "\n";
                break;
            case sym.IDENT:
                codeASM += "mov eax," + arbre.getValue() + "\n";
                codeASM += "push eax\n";
                break;
            case sym.MOD:
                // on va calculer la partie gauche
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                // on va calculer la partie droite
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }
                codeASM += "pop eax\n"; // droite n
                codeASM += "pop ebx\n"; // gauche a
                // a mod n = a - ((a/n) * n ))
                codeASM += "mov ecx, ebx\n"; // a

                codeASM += "div ecx, eax\n"; // a/n
                codeASM += "mul ecx, eax\n"; //  (a/n) * n )
                codeASM += "sub ebx, ecx\n";
                codeASM +="push ebx\n";
                break;
            case sym.MOINS_UNAIRE:
                // on va calculer la partie gauche
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                codeASM += "pop eax\n";
                codeASM += "mul eax, -1\n";
                codeASM += "push exa\n";
                break;
            case sym.IF:
                int tmpCPT = boolCPT;
                boolCPT++;
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                codeASM += "pop eax\n";
                codeASM += "jz else_"+tmpCPT+"\n";
                if(arbre.getRight() != null){
                    if(arbre.getRight().getLeft() != null){
                        generateur(arbre.getRight().getLeft());
                    }
                }
                codeASM += "jmp fin_"+tmpCPT+"\n";
                codeASM += "else_"+tmpCPT+":\n";
                if(arbre.getRight() != null){
                    if(arbre.getRight().getRight() != null){
                        generateur(arbre.getRight().getRight());
                    }
                }

                codeASM += "fin_"+tmpCPT+":\n";

                break;
            case sym.WHILE:
                codeASM += "debut_while_"+boolCPT+":\n";
                tmpCPT = boolCPT;
                boolCPT++;
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                codeASM += "pop eax\n";
                codeASM += "jz fin_"+tmpCPT+"\n";
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }
                codeASM += "jmp debut_while_"+tmpCPT+"\n";
                codeASM += "fin_"+tmpCPT+":\n";
                break;
            case sym.INF:
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                // on va calculer la partie droite
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }
                codeASM += "pop eax\n"; // droite
                codeASM += "pop ebx\n"; // gauche
                codeASM += "sub ebx, eax\n";
                boolTest("jl");


                break;
            case sym.INFE:
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                // on va calculer la partie droite
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }

                codeASM += "pop eax\n"; // droite
                codeASM += "pop ebx\n"; // gauche
                codeASM += "sub ebx, eax\n";
                boolTest("jle");

                break;
            case sym.OR:
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                // on va calculer la partie droite
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }

                codeASM += "pop eax\n";
                codeASM += "pop ebx\n";
                codeASM += "add eax,ebx\n";
                boolTest("jg");
                break;
            case sym.AND:
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                // on va calculer la partie droite
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }
                codeASM += "pop eax\n";
                codeASM += "pop ebx\n";
                codeASM += "add eax,ebx\n";
                codeASM += "sub eax,1\n";
                boolTest("jg");
                break;
            case sym.DEGAL:
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                // on va calculer la partie droite
                if(arbre.getRight() != null){
                    generateur(arbre.getRight());
                }
                codeASM += "pop eax\n";
                codeASM += "pop ebx\n";
                codeASM += "sub ebx,eax\n";
                boolTest("jz");
                break;
            case sym.NOT:
                if(arbre.getLeft() != null){
                    generateur(arbre.getLeft());
                }
                codeASM += "pop eax\n";
                boolTest("jz");
                break;

        }
    }

    public String getCodeASM() {
        return codeASM;
    }

    public void ArithmeticOperation (String ope, Arbre arbre) {
        // on va calculer la partie gauche
        if(arbre.getLeft() != null){
            generateur(arbre.getLeft());
        }
        // on va calculer la partie droite
        if(arbre.getRight() != null){
            generateur(arbre.getRight());
        }
        codeASM += "pop eax\n";
        codeASM += "pop ebx\n";
        codeASM += ope + " ebx, eax\n";

        codeASM += "push ebx\n";
    }

    public void boolTest(String opeVrai){
        codeASM += opeVrai +" vrai_" + boolCPT + "\n"; // si inférieur 0 on jump
        codeASM += "push 0\n";
        codeASM += "jmp fin_" + boolCPT + " \n";
        codeASM += "vrai_" + boolCPT + ":\npush 1\n";
        codeASM += "fin_" + boolCPT + ":\n";
        boolCPT++;
    }

}
