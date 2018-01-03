package fr.usmb.m1isc.compilation.tp1;


import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java_cup.runtime.Symbol;

public class Main {

	public static void main(String[] args) throws Exception  {
		LexicalAnalyzer yy;
		if (args.length > 0)
			yy = new LexicalAnalyzer(new FileReader(args[0])) ;
		else
			yy = new LexicalAnalyzer(new InputStreamReader(System.in)) ;
		@SuppressWarnings("deprecation")
		parser p = new parser (yy);

		Symbol s = p.parse( );
		Arbre a = (Arbre) s.value;

		GenerateurASM gA = new GenerateurASM(a);
		Files.write(Paths.get("D:\\Documents\\M1\\INFO703\\INFO703-TP2\\newtest.asm"), gA.getCodeASM().getBytes());
		//gA.toString();

		a.toString();
	}

}
