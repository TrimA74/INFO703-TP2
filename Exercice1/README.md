# Exercice 1

## Evaluateur d'expressions arithmétiques infixées sur les nombres entiers.

### Analyse lexicale
Source pour l'analyseur lexical (JFlex) : *[src/main/jflex/AnalyseurLexical.flex](src/main/jflex/AnalyseurLexical.flex)*

On reconnait les différents mots du langage (lexème) : 
- opérateurs arithmétiques : +, -, /, *
- les parenthèses ouvrantes et fermante : (, )
- les entiers : [0-9]+
- les espaces (espaces, tabulation, passage à la ligne) : \s
- le lexème indiquant la fin d'une expression : ;


### Analyse syntaxique
Source de l'analyseur syntaxique : *[src/main/cup/AnalyseurSyntaxique.cup](src/main/cup/AnalyseurSyntaxique.cup)*

Il s'agit de reconnaître la syntaxe (grammaire du langage).
Il travaille à partir des lexèmes (token) qui remontent de l'analyseur lexical. 
Tous les lexèmes doivent être déclarés comme des symboles terminaux.
```JFLEX
terminal PLUS, MOINS, MUL, DIV, PAR_G, PAR_D, SEMI, ENTIER, ERROR; 
```
Les règles de la grammaire décrivent le langage :
- on a une suite d'expressions (au moins une)
- chaque expression est terminée par un point-virgule (lexème SEMI)
- une expression est soit :
    - un entier (lexème ENTIER)
    - une expression arithmétique simple : expression_gauche opérateur expression_droite
    - une expression parenthèsée : ( expression )
```
// on a une liste d'expressions (avec au moins une expression)
liste_expr ::= expr liste_expr 
             | expr  
             ;
// chaque expression arithmetique est terminee par un point virgule (SEMI)
expr ::= expression SEMI
       | error SEMI
       ;
expression ::= ENTIER 
             | expression PLUS expression  
             | expression MOINS expression  
             | expression MUL expression 
             | expression DIV expression
             | PAR_G expression PAR_D   
             ;
```
Les règles du genre : `expression ::= expression PLUS expression` introduisent des ambiguïtés dans la grammaire 
(ce que n'aime pas l'analyseur). 
Il faut dont lever ces ambiguïtés, soit en réécrivant les règles (cf. exemple donné en cours pour les expressions arithmétique), 
soit précisant (comme le permet l'analyseur syntaxique) l'associativité et la priorité des opérateurs:
```
precedence left PLUS, MOINS;
precedence left MUL, DIV;
```
On liste les opérateurs du moins prioritaire au plus prioritaire.

### Evaluation des expressions
Pour l'évaluation des expressions, il faut aussi disposer des valeurs des entiers reconnus par l'analyseur lexical. 
Avec JFlex et CUP cela peut se faire en passant un paramètre supplémentaire lors de la remonté du lexème par l'analyseur lexical :
```JFLEX
{chiffre}+	{ return new Symbol(sym.ENTIER, new Integer(yytext())) ;}
```
Au niveau de CUP (analyseur syntaxique) on peut avoir une valeur associée à chacun des symboles (terminaux ou non terminaux). 
Le type de cette valeur doit être spécifié lors de la déclation du symbole : 
```
/* symboles terminaux */
terminal PLUS, MOINS, MUL, DIV, PAR_G, PAR_D, SEMI, ERROR; 
terminal Integer ENTIER;
/* non terminaux */
non terminal liste_expr, expr;
non terminal Integer expression ;
```
Dans notre cas, on aura un entier associé 
- au symbole terminal (lexème) `ENTIER` - il est défini par l'analyzeur lexical - 
- au symbole non terminal `expression`.

Pour calculer les valeur associées au symboles non terminaux, on ajoute des actions sémantique dans les règles (valeur associée à `RESULT`) : 
`expression ::= expression:e1 PLUS expression:e2 {: RESULT = e1+e2 ; :} `

On ajoute aussi une action semantique pour afficher la valeur finale de l'expression : 
`expr ::= expression:e SEMI {: System.out.println("val: "+e); :}`
```
/* grammaire */
// on a une liste d'expressions (avec au moins une expression)
liste_expr ::= expr liste_expr 
             | expr  
             ;
// chaque expression arithmetique est terminee par un point virgule (SEMI)
expr ::= expression:e SEMI   {: System.out.println("val: "+e); :}
       | error SEMI
       ;
expression ::= ENTIER:e                            {: RESULT = e ; :}
             | expression:e1 PLUS expression:e2    {: RESULT = e1+e2 ; :}
             | expression:e1 MOINS expression:e2   {: RESULT = e1-e2 ; :}
             | expression:e1 MUL expression:e2     {: RESULT = e1*e2 ; :}
             | expression:e1 DIV expression:e2     {: RESULT = e1/e2 ; :}
             | PAR_G expression:e PAR_D            {: RESULT = e ; :}
             ;
```

La règle `expr ::= error SEMI` permet de gérér les erreurs syntaxiques en définissant un point de reprise d'erreur 
après l'obtention d'un point virgule.

