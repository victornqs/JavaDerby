package bdconn;

import java.io.IOException;
import java.sql.SQLException;

import bdconn.Conexao;

public class UseConexao {

	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
		try{
		Conexao Banco = new Conexao();
		
		//Define o nome do banco
		Banco.nomeBD = "BD_Teste";
		
		//Define o nome da tabela
		Banco.nomeTabela = "Table_Teste";
		
		//Inicia a conexão com o banco
		Banco.iniciar(Banco.nomeBD);
		
		//Cria a tabela
		Banco.criaTabela(Banco.nomeTabela);
		
		//Insere os dados na tabela
		Banco.inseriDadosTabela("Table_Teste",20.0,30.0,40.0,50.0);
		
		//Mostra os dados da tabela
		Banco.mostraTabela("Table_Teste");		
		
		//Mostra somente o Setpoint
		Banco.buscaSetpoint("Table_Teste");
		
		//Mostra somente a Pressao de Recalque
		Banco.buscaPressaoRecalque("Table_Teste");
		
		//Mostra somente a Pressao de Retaguarda
		Banco.buscaPressaoRetaguarda("Table_Teste");
		
		//Mostra somente a Frequencia
		Banco.buscaFrequencia("Table_Teste");
		
		Banco.finalizar();
		
		}catch(Exception excep){
			excep.printStackTrace();
		}
		

	}

}
