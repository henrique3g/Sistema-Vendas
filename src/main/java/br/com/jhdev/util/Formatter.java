package br.com.jhdev.util;

import javax.swing.text.MaskFormatter;

/**
 * Formatter
 */
public class Formatter {

	public static String formatCpf(String cpf){
		
		try {
            MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
            maskCpf.setValueContainsLiteralCharacters(false);
			// System.out.println("OK: \""+cpf+"\"");
			return maskCpf.valueToString(cpf);
           
        } catch (Exception ee) {
			// System.out.println("ERRO: \""+cpf+"\"");
            // System.out.println("erro ao colocar mask CPF");
			return cpf;
        }
	}

	public static String formatRg(String rg){
		try {
            MaskFormatter maskCpf = new MaskFormatter("#.###.###");
            maskCpf.setValueContainsLiteralCharacters(false);
            
			return maskCpf.valueToString(rg);
           
        } catch (Exception ee) {
            // System.out.println("erro ao colocar mask RG");
			return rg;
        }
	}
	
	public static String formatTelefone(String telefone){
		try {
            MaskFormatter maskCpf = new MaskFormatter("(##) #####-####");
            maskCpf.setValueContainsLiteralCharacters(false);
            
			return maskCpf.valueToString(telefone);
           
        } catch (Exception ee) {
            // System.out.println("erro ao colocar mask Telefone");
			return telefone;
        }
	}
}