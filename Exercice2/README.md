# Exercice 2

## Ajout de la gestion des variables.

### Analyse lexicale
Source pour l'analyseur lexical (JFlex) : *[src/main/jflex/AnalyseurLexical.flex](src/main/jflex/AnalyseurLexical.flex)*

Un identificateur doit commencer par une lettre et peut contenir des chiffres, des lettres et des underscores.
```JFLEX
ident = [:letter:]\w*
```

Ajout de la reconnaissance des token pour les variables : LET, EGAL , IDENT.
Dans le cas des identificateurs, on renvoie aussi la chaîne de caractères de l'identificateur.
```JFLEX
{let}		{ return new Symbol(sym.LET) ;}
"="			{ return new Symbol(sym.EGAL) ;}
{ident}		{ return new Symbol(sym.IDENT, yytext()) ;}
```

### Analyse syntaxique
Source de l'analyseur syntaxique : *[src/main/cup/AnalyseurSyntaxique.cup](src/main/cup/AnalyseurSyntaxique.cup)*

On ajoute dans les déclaration les nouveaux symboles terminaux renvoyés par l'analyseur lexical :
```
terminal LET, EGAL, ERROR; 
terminal String IDENT;
```

On ajoute ensuite les règles pour prendre en les variables dans la syntaxe.

On a une règle pour les déclarations et modifications de variables :
```
expr ::= expression SEMI
       | LET IDENT EGAL expression:e SEMI
       | error SEMI
	   ;
```
Et une autre règle pour l'utilisation des variables dans les expressions :
```
expression ::= IDENT
             | ENTIER
             | expression PLUS expression
             | ...
```

### Evaluation des expressions
Pour la prise en compte des variables, l'analyseur lexical fait remonter avec le lexème IDENT, 
la chaîne de caractères qui correspond au nom de la variable.

Au niveau de CUP (analyseur syntaxique) on va devoir gérer les valeur des variables. 
Une solution peut consister à utiliser une HashMap pour associer leurs valeurs aux noms des variables : 
```
action code {: 
    // pour utilisation dans les actions (classe action)
	// HashMap pour la gestion des variables
	private HashMap<String,Integer> vars = new HashMap<>();
:};
```
Ensuite suivant qu'on utilise la variable dans les expressions ou une affectation, 
on fait un accès en lecture ou en écriture.
```
affectation ::= LET IDENT:nom EGAL expression:e   {: vars.put(nom,e); RESULT = e; :}
```
```
expression ::= IDENT:nom  {: RESULT = vars.get(nom) ; :}
```

