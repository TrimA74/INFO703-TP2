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

%% 
/* ------------------------Section des Regles Lexicales----------------------*/

/* regles */

"("			{ return new Symbol(sym.PAR_G) ;}
")"			{ return new Symbol(sym.PAR_D) ;}
"+"			{ return new Symbol(sym.PLUS) ;}
"-"			{ return new Symbol(sym.MOINS) ;}
"/"			{ return new Symbol(sym.DIV) ;}
"*"			{ return new Symbol(sym.MUL) ;}
";"			{ return new Symbol(sym.SEMI) ;}
{chiffre}+	{ return new Symbol(sym.ENTIER, new Integer(yytext())) ;}
{espace} 	{  }
.			{ return new Symbol(sym.ERROR) ;}