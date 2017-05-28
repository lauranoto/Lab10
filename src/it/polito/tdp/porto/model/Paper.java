package it.polito.tdp.porto.model;

import java.util.LinkedList;
import java.util.List;

public class Paper {

	private int eprintid;
	private String title;
	private int year;
	private List<Author> autori;

	public Paper(int eprintid, String title, int year) {
		this.eprintid = eprintid;
		this.title = title;
		this.year = year;
	}

	public int getEprintid() {
		return eprintid;
	}

	public void setEprintid(int eprintid) {
		this.eprintid = eprintid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return eprintid + ", " + title + ", " + year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eprintid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paper other = (Paper) obj;
		if (eprintid != other.eprintid)
			return false;
		return true;
	}

	public List<Author> getAutori() {
		return autori;
	}

	public void setAutori(List<Author> autori) {
		this.autori = new LinkedList<>(autori);
	}

}
