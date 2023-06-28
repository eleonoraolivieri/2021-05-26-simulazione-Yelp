package it.polito.tdp.yelp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;



public class Model {
	
	YelpDao dao;
	private Graph<Business, DefaultWeightedEdge> grafo;
	private Map<String,Business> idMap;
	private List<String> città;

	public Model() {
		this.dao = new YelpDao();
		this.idMap = new HashMap<String,Business>();
		this.dao.getAllBusiness(idMap);
	}
	
	

	public List<String> getCittà() {
		if(this.città==null) {
			YelpDao dao = new YelpDao() ;
			this.città = dao.getCittà();
		}
		return this.città;
	}
	
	public void creaGrafo(String città, int anno) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(città,anno, idMap));
		
		//aggiungo gli archi
		
		for(Adiacenza a : dao.getAdiacenze(città,anno, idMap)) {
			if(a.getPeso() <= 0) {
				//p1 peggio di p2
				if(grafo.containsVertex(a.getV1()) && grafo.containsVertex(a.getV2())) {
					Graphs.addEdgeWithVertices(this.grafo, a.getV1(), 
							a.getV2(),(-1)* a.getPeso());
				}
			} else {
				//p2 meglio di p1
				if(grafo.containsVertex(a.getV1()) && grafo.containsVertex(a.getV2())) {
					Graphs.addEdgeWithVertices(this.grafo, a.getV2(), 
							a.getV1(), a.getPeso());
				}
			}
		}
	}
	
	public LocaleMigliore getMigliore() {
		if(grafo == null) {
			return null;
		}
		
		Business best = null;
		Double maxDelta = 0.0;
		
		for(Business b : this.grafo.vertexSet()) {
			// calcolo la somma dei pesi degli archi uscenti
			double pesoUscente = 0.0;
			for(DefaultWeightedEdge edge : this.grafo.outgoingEdgesOf(b)) {
				pesoUscente += this.grafo.getEdgeWeight(edge);
			}
			
			// calcolo la somma dei pesi degli archi entranti
			double pesoEntrante = 0.0;
			for(DefaultWeightedEdge edge : this.grafo.incomingEdgesOf(b)) {
				pesoEntrante += this.grafo.getEdgeWeight(edge);
			}
			
			double delta = pesoEntrante - pesoUscente;
			if(delta > maxDelta) {
				best = b;
				maxDelta = delta;
			}
		}
		
		return new LocaleMigliore (best,maxDelta);
		
	}
	
	public Business localeMigliore() {
		double max = 0.0;
		Business result = null ;
		
		for(Business b: this.grafo.vertexSet()) {
			double val = 0.0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(b))
				val -= this.grafo.getEdgeWeight(e) ;
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(b))
				val += this.grafo.getEdgeWeight(e) ;
			
			if(val>max) {
				max = val ;
				result = b ;
			}
		}
		return result; 
		
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Graph<Business,DefaultWeightedEdge> getGrafo() {
		return this.grafo;
	}
	
	
}
