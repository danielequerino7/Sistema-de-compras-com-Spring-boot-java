package br.market.market_sistem.Model;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public ProdutoDAO(){

    }
    
    public void addProduto(Produto produto){
        Connection connection = null;
        PreparedStatement p = null;
        
        try{
            connection = Conexao.getConnection();
            p = connection.prepareStatement("INSERT INTO Produto (preco, nome, descricao, estoque) VALUES (?,?,?,?)");
            p.setFloat(1, produto.getPreco());
            p.setString(2, produto.getNome());
            p.setString(3,produto.getDescricao());
            p.setInt(4,produto.getEstoque());
            p.executeUpdate();
            connection.close();
        }catch(SQLException | URISyntaxException exception){
            throw new RuntimeException("Erro ao cadastrar produto", exception);
        }
    }

    public void atualizarProduto(Produto produto){
        Connection connection = null;
        PreparedStatement p = null;

        try{
            connection = Conexao.getConnection();
            p = connection.prepareStatement("UPDATE Produto SET estoque=? WHERE id_produto=?");
            p.setFloat(1, produto.getEstoque());
            p.setInt(2, produto.getId());
            p.executeUpdate();
            connection.close();
        }catch(SQLException | URISyntaxException exception){
            throw new RuntimeException("Erro ao atualizar produto", exception);
        }
    }

    public List<Produto> listarProdutos(){
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet r = null;
        List<Produto> lista = new ArrayList<>();

        try{
            connection = Conexao.getConnection();
            p = connection.prepareStatement("SELECT * FROM Produto");
            r = p.executeQuery();

            while(r.next()){
                Produto pr = new Produto(r.getInt("id_produto"), r.getFloat("preco"), r.getString("nome"), r.getString("descricao"), r.getInt("estoque"));
                lista.add(pr);
            }

        }catch(SQLException | URISyntaxException exception){
            throw new RuntimeException("Erro ao listar produto", exception);
        }
        return lista;
    }

    public Produto getProdutoById(int id){
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet r = null;
        Produto produto = null;
        try{
            connection = Conexao.getConnection();
            p = connection.prepareStatement("SELECT * FROM Produto WHERE id_produto = ?");
            p.setInt(1, id);
            r = p.executeQuery();

            while(r.next()){
                Produto pr = new Produto(r.getInt("id_produto"), r.getFloat("preco"), r.getString("nome"), r.getString("descricao"), r.getInt("estoque"));
                produto = pr;
            }
        }catch(SQLException | URISyntaxException exception){
            throw new RuntimeException("Erro ao buscar produto", exception);
        }
        return produto;
    }

}
