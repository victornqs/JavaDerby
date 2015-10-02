package bdconn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	public String nomeBD;
	private String driverBD;
	private String stringDeConexao;
	private Connection conexaoBD;
	private Statement executor;
	public String nomeTabela;	
	
	
	public void iniciar(String nomeDB) throws SQLException{
		this.nomeBD = nomeBD;
		driverBD = "org.apache.derby.jdbc.EmbeddedDriver";
		stringDeConexao = "jdbc:derby:" + nomeBD + ";create=true";
		//Conecta na Base de dados (Caso não exista ela será criada)
		conectaBD(nomeDB);
		//Inicializa o executor de comandos na base de dados.	
		executor = conexaoBD.createStatement();
	}
	
	
	private void conectaBD(String nomeBD) throws SQLException {
		this.nomeBD = nomeBD;
		conexaoBD = DriverManager.getConnection(stringDeConexao);
		System.out.println("Conexão estabelecida com sucesso na base de dados '" + nomeBD+"'!");
	}	
	
	
	public void criaTabela(String nomeTabela) throws SQLException{
		this.nomeTabela = nomeTabela;
		String createString = "CREATE TABLE "+this.nomeTabela
	        +  " (Data TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "	        
	        +  " Setpoint Real NOT NULL, "
	        +  " PressaoRecalque Real NOT NULL, "
	        +  " PressaoRetaguarda Real NOT NULL, "
	        +  " Frequencia Real NOT NULL, "
	        +  " PRIMARY KEY(Data)) "
	        ;
		executor.execute(createString);
	}
	
	
	public void inseriDadosTabela(String nomeTabelaVar, Double Setpoint, Double PressaoRecalque, Double PressaoRetaguarda, Double Frequencia) throws SQLException, IOException{			
		PreparedStatement executorInsert = conexaoBD.prepareStatement("INSERT INTO "+nomeTabelaVar+"(Setpoint, PressaoRecalque, PressaoRetaguarda, Frequencia) values (?,?,?,?)");			
			executorInsert.setDouble(1,Setpoint);
			executorInsert.setDouble(2,PressaoRecalque);
			executorInsert.setDouble(3,PressaoRetaguarda);
			executorInsert.setDouble(4,Frequencia);	
			executorInsert.executeUpdate();
			executorInsert.close();
	}
	
		
	public void mostraTabela(String nomeTabela) throws SQLException{		
		ResultSet dadosTabela = executor.executeQuery("select Data, Setpoint, PressaoRecalque, PressaoRetaguarda, Frequencia from "+nomeTabela+" order by Data");
		System.out.println("\n");
		while (dadosTabela.next()){			
			System.out.println("Tabela "+nomeTabela+" | " + dadosTabela.getTimestamp(1) + "\t|" + dadosTabela.getString(2) + "\t|" + dadosTabela.getString(3) + "\t|" + dadosTabela.getString(4) + "\t|" + dadosTabela.getString(5) );
		}
		System.out.println("\n");
		dadosTabela.close();
	}
	
	
	public void buscaSetpoint(String nomeTabela) throws SQLException{		
		ResultSet dadosTabela = executor.executeQuery("select Data, Setpoint from "+nomeTabela+" order by Data");
		System.out.println("\n");
		while (dadosTabela.next()){			
			System.out.println("Tabela "+nomeTabela+" | " + dadosTabela.getTimestamp(1) + "\t|" + dadosTabela.getString(2));
		}
		System.out.println("\n");
		dadosTabela.close();
	}
	
	
	public void buscaPressaoRecalque(String nomeTabela) throws SQLException{		
		ResultSet dadosTabela = executor.executeQuery("select Data, PressaoRecalque from "+nomeTabela+" order by Data");
		System.out.println("\n");
		while (dadosTabela.next()){			
			System.out.println("Tabela "+nomeTabela+" | " + dadosTabela.getTimestamp(1) + "\t|" + dadosTabela.getString(2));
		}
		System.out.println("\n");
		dadosTabela.close();
	}
	
	
	public void buscaPressaoRetaguarda(String nomeTabela) throws SQLException{		
		ResultSet dadosTabela = executor.executeQuery("select Data, PressaoRetaguarda from "+nomeTabela+" order by Data");
		System.out.println("\n");
		while (dadosTabela.next()){			
			System.out.println("Tabela "+nomeTabela+" | " + dadosTabela.getTimestamp(1) + "\t|" + dadosTabela.getString(2));
		}
		System.out.println("\n");
		dadosTabela.close();
	}
	
	
	public void buscaFrequencia(String nomeTabela) throws SQLException{		
		ResultSet dadosTabela = executor.executeQuery("select Data, Frequencia from "+nomeTabela+" order by Data");
		System.out.println("\n");
		while (dadosTabela.next()){			
			System.out.println("Tabela "+nomeTabela+" | " + dadosTabela.getTimestamp(1) + "\t|" + dadosTabela.getString(2));
		}
		System.out.println("\n");
		dadosTabela.close();
	}
		
	
	public void finalizar() throws SQLException{
		//Desconecta da base de dados
		desconectaDB();
		//Encerra serviço da base de dados
		encerraDB();
	}
	
	
	private void desconectaDB() throws SQLException{
		//Fecha a conexão com o banco.
        conexaoBD.close();
	}
	
	
	private void encerraDB() throws SQLException{
		// No uso do banco de dados acoplado com a aplicação é importante que ele também seja encerrado.
        if (driverBD.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
           try {
        	   //Encerrando base de dados.
              DriverManager.getConnection("jdbc:derby:;shutdown=true");
           } catch (SQLException se)  {	
              if ( se.getSQLState().equals("XJ015") ) {		
            	  System.out.println("Base de dados foi encerrada com sucesso!");	
              }else{
            	  throw new SQLException("Erro ao encerrar a base de dados! "+se.getMessage(),se.getSQLState(),se);
              }
           }
        }
	}
	
	
}