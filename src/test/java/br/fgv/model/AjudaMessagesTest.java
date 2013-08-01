package br.fgv.model;

import static org.junit.Assert.*;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.junit.Test;

public class AjudaMessagesTest {
	
	private static final ResourceBundle RESOURCE_BUNDLE = PropertyResourceBundle
			.getBundle(AjudaMessages.BUNDLE_NAME);

	@Test
	public void test() {
		assertTrue(RESOURCE_BUNDLE.keySet().size() > 0);
		
		for (String k : RESOURCE_BUNDLE.keySet()) {
			String[] parts = k.split("\\.");
			
			Tabela t = Tabela.byName(parts[0]);
			if(t == null) {
				t = Tabela.byName(parts[0] + "_" + Tabela.HOLDER_ANO_ELEICAO);
			}
			assertNotNull("Tabela não encontrada! " + parts[0], t);
			
			assertTrue("Coluna não encontrada! " + parts[1], buscaColuna(t, parts[1]));
		}
	}
	
	@Test
	public void acentos() {
		System.out.println(AjudaMessages.getString("aux_candidatos.titulo"));
		assertTrue(AjudaMessages.getString("aux_candidatos.titulo").contains("Número"));
		
	}

	private boolean buscaColuna(Tabela t, String nome) {
		for (Coluna c : t.getColunas()) {
			if(c.getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}

}
