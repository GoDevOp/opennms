package org.opennms.features.topology.app.internal.jung;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import org.apache.commons.collections15.Transformer;
import org.opennms.features.topology.api.GraphContainer;
import org.opennms.features.topology.api.LayoutAlgorithm;
import org.opennms.features.topology.app.internal.Edge;
import org.opennms.features.topology.app.internal.Graph;
import org.opennms.features.topology.app.internal.Vertex;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.SparseGraph;

public class FRLayoutAlgorithm implements LayoutAlgorithm, LayoutConstants {

	public void updateLayout(GraphContainer graph) {
		
		Graph g = new Graph(graph);
		
		int szl = g.getSemanticZoomLevel();
		
		
		SparseGraph<Vertex, Edge> jungGraph = new SparseGraph<Vertex, Edge>();
		
		
		List<Vertex> vertices = g.getVertices(szl);
		
		for(Vertex v : vertices) {
			jungGraph.addVertex(v);
		}
		
		List<Edge> edges = g.getEdges(szl);
		
		for(Edge e : edges) {
			jungGraph.addEdge(e, e.getSource(), e.getTarget());
		}
		

		FRLayout<Vertex, Edge> layout = new FRLayout<Vertex, Edge>(jungGraph);
		layout.setInitializer(new Transformer<Vertex, Point2D>() {
			public Point2D transform(Vertex v) {
				return new Point(v.getX(), v.getY());
			}
		});
		layout.setSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT));
		
		while(!layout.done()) {
			layout.step();
		}
		
		
		for(Vertex v : vertices) {
			v.setX((int)layout.getX(v));
			v.setY((int)layout.getY(v));
		}
		
		
		
		
	}

}
