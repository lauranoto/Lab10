package it.polito.tdp.porto.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private PortoDAO dao;
	private Map<Integer, Author> autori;
	private Map<Integer, Paper> articoli;
	private SimpleGraph<Author, DefaultEdge> grafo;
	private List<Author> nonCoautori;
	private List<DefaultEdge> shortestPathEdgeList;

	public Model() {
		this.dao = new PortoDAO();
	}

	public Map<Integer, Author> getAutori() {

		if (this.autori == null) {
			this.autori = dao.getAutori();
		}
		return autori;
	}

	public Map<Integer, Paper> getArticoli() {

		if (this.articoli == null) {
			this.articoli = dao.getArticoli();
		}

		return articoli;
	}

	public void caricaInformazioni() {

		// Memorizzo gli articoli dell'autore
		for (Author autore : this.getAutori().values()) {
			if (autore.getArticoli() == null) {
				autore.setArticoli(dao.getArticoli(autore, this.getArticoli()));
			}
		}
		// Memorizzo gli autori dell'articolo
		for (Paper articolo : this.getArticoli().values()) {
			if (articolo.getAutori() == null) {
				articolo.setAutori(dao.getAutori(articolo, autori));
			}
		}

	}

	public void creaGrafo() {

		this.grafo = new SimpleGraph<>(DefaultEdge.class);

		Graphs.addAllVertices(grafo, this.getAutori().values());

		for (Paper articolo : this.getArticoli().values()) {
			for (Author autore1 : articolo.getAutori()) {
				for (Author autore2 : articolo.getAutori()) {
					if (!autore1.equals(autore2) && !grafo.containsEdge(autore1, autore2)
							&& !grafo.containsEdge(autore2, autore1)) {
						grafo.addEdge(autore1, autore2);
					}
				}
			}
		}
	}

	public String getCoautori(Author autoreSelezionato) {

		String result = "";
		List<Author> coautori = Graphs.neighborListOf(grafo, autoreSelezionato);
		for (Author coautore : coautori) {
			result += coautore.toString() + "\n";
		}
		
		nonCoautori = new LinkedList<>(autori.values());
		nonCoautori.removeAll(coautori);

		return result;

	}

	public List<Author> getNonCoautori() {
		return nonCoautori;
	}

	public String getSequenzaArticoli() {
		
		String result = "";
		List<Paper> articoliComuni = new LinkedList<>();

		for (DefaultEdge e : shortestPathEdgeList) {
			Author partenza = this.getGrafo().getEdgeSource(e);
			Author arrivo = this.getGrafo().getEdgeTarget(e);
			for (Paper articolo : partenza.getArticoli()) {
				if(arrivo.getArticoli().contains(articolo)){
					articoliComuni.add(articolo);
					break;
				}
			}
		}
		
		for (Paper paper : articoliComuni) {
			result += paper.toString() + "\n";
		}

		return result;

	}

	public void calcolaSequenza(Author autorePartenza, Author autoreArrivo) {
		
		DijkstraShortestPath<Author, DefaultEdge> d = new DijkstraShortestPath<Author, DefaultEdge>(
				this.getGrafo(), autorePartenza, autoreArrivo);

		shortestPathEdgeList = d.getPathEdgeList();

	}

	private SimpleGraph<Author, DefaultEdge> getGrafo() {
		
		if(this.grafo == null){
			this.creaGrafo();
		}
		return grafo;
	}

}
