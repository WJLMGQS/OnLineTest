package org.wjlmgqs.daomain;

import java.util.ArrayList;
import java.util.List;


public class IChromosome {

	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public List<GeneView> getGeneViews() {
		return geneViews;
	}
	public void setGeneViews(List<GeneView> geneViews) {
		this.geneViews = geneViews;
	}
	private int length = 0;
	private List<GeneView> geneViews = new ArrayList<GeneView>();
}
