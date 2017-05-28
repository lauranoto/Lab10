package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	public Map<Integer, Author> getAutori() {

		final String sql = "SELECT * FROM author ORDER BY lastname ASC";
		Map<Integer, Author> autori = new LinkedHashMap<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int idAutore = rs.getInt("id");
				Author a = new Author(idAutore, rs.getString("lastname"), rs.getString("firstname"));
				autori.put(idAutore, a);
			}

			st.close();
			conn.close();
			return autori;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<Integer, Paper> getArticoli() {

		final String sql = "SELECT * FROM paper ";
		Map<Integer, Paper> articoli = new HashMap<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int idArticolo = rs.getInt("eprintid");
				Paper p = new Paper(idArticolo, rs.getString("title"), rs.getInt("date"));
				articoli.put(idArticolo, p);
			}

			st.close();
			conn.close();
			return articoli;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Paper> getArticoli(Author author, Map<Integer, Paper> articoli) {

		final String sql = "SELECT paper.eprintid, paper.title, paper.date FROM paper, creator"
				+ " WHERE paper.eprintid = creator.eprintid AND creator.authorid=? ";
		List<Paper> papers = new LinkedList<>();
		
		int id = author.getId();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				papers.add(articoli.get(rs.getInt("paper.eprintid")));
			}

			st.close();
			conn.close();
			return papers;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Author> getAutori(Paper paper, Map<Integer, Author> autori) {
		
		final String sql = "SELECT author.id, author.lastname, author.firstname FROM author, creator"
				+ " WHERE author.id = creator.authorid AND creator.eprintid=? ";
		List<Author> authors = new LinkedList<>();
		
		int id = paper.getEprintid();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				authors.add(autori.get(rs.getInt("author.id")));
			}

			st.close();
			conn.close();
			return authors;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}