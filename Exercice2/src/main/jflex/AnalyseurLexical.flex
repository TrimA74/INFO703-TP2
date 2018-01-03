/* --------------------------Section de Code Utilisateur---------------------*/
package fr.usmb.m1isc.compilation.tp1;
import java_cup.runtime.Symbol;

%%

/* -----------------Section des Declarations et Options----------------------*/
// nom de la class a generer
%class LexicalAnalyzer
%unicode
%line   // numerotation des lignes
%column // numerotation caracteres par ligne

// utilisation avec CUP 
%cup

// code a ajouter dans la classe produite
%{

%}

/* definitions regulieres */

chiffre 	= [0-9]
espace 		= \s
mod 		= "mod"|"MOD"
let			= "let"|"LET"
input       = "input"|"INPUT"
if          = "if"|"IF"
then        = "then"|"THEN"
else        = "else"|"ELSE"
while       = "while"|"WHILE"
do          = "do"|"DO"
and         = "and"|"AND"
or          = "or"|"OR"
not         = "not"|"NOT"
output      = "output"|"OUTPUT"
// un identifiant commence par une lettre suivit d'un charactere alphanumerique (lettre/chiffre/underscore)
ident		= [:letter:]\w*
comment1	= "//".*					 // commentaire uniligne
comment2	= "/*"([^*]|("*"[^/]))*"*/"  // commentaire multiligne
comment	= {comment1}|{comment2}	

%% 
/* ------------------------Section des Regles Lexicales----------------------*/

/* regles */

{let}		{ return new Symbol(sym.LET, yyline, yycolumn) ;}
"=="		{ return new Symbol(sym.DEGAL, yyline, yycolumn) ;}
"="			{ return new Symbol(sym.EGAL, yyline, yycolumn) ;}
"("			{ return new Symbol(sym.PAR_G, yyline, yycolumn) ;}
")"			{ return new Symbol(sym.PAR_D, yyline, yycolumn) ;}
"+"			{ return new Symbol(sym.PLUS, yyline, yycolumn) ;}
"-"			{ return new Symbol(sym.MOINS, yyline, yycolumn) ;}
"/"			{ return new Symbol(sym.DIV, yyline, yycolumn) ;}
{mod}		{ return new Symbol(sym.MOD, yyline, yycolumn) ;}
"*"			{ return new Symbol(sym.MUL, yyline, yycolumn) ;}
";"			{ return new Symbol(sym.SEMI, yyline, yycolumn) ;}
{chiffre}+	{ return new Symbol(sym.ENTIER, yyline, yycolumn, new Integer(yytext())) ;}
{comment}	{ /*commentaire : pas d'action*/ }
{espace} 	{ /*espace : pas d'action*/ }

{input}		{ return new Symbol(sym.INPUT, yyline, yycolumn) ;}
{if}		{ return new Symbol(sym.IF, yyline, yycolumn) ;}
{while}		{ return new Symbol(sym.WHILE, yyline, yycolumn) ;}
{do}		{ return new Symbol(sym.DO, yyline, yycolumn) ;}
{then}		{ return new Symbol(sym.THEN, yyline, yycolumn) ;}
{else}		{ return new Symbol(sym.ELSE, yyline, yycolumn) ;}
{not}		{ return new Symbol(sym.NOT, yyline, yycolumn) ;}
{or}		{ return new Symbol(sym.OR, yyline, yycolumn) ;}
{and}		{ return new Symbol(sym.AND, yyline, yycolumn) ;}
"<"         { return new Symbol(sym.INF, yyline, yycolumn) ;}
"<="        { return new Symbol(sym.INFE, yyline, yycolumn) ;}
{output}    { return new Symbol(sym.OUTPUT, yyline, yycolumn) ;}

{ident}		{ return new Symbol(sym.IDENT, yyline, yycolumn, yytext()) ;}

.			{ return new Symbol(sym.ERROR, yyline, yycolumn) ;}

