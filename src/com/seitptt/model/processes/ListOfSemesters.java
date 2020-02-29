package com.seitptt.model.processes;

import java.util.ArrayList;
import java.util.Iterator;

import com.seitptt.interfaces.Findable;

public class ListOfSemesters implements Iterable<Semester>,Findable{

	private ArrayList<Semester> loS = new ArrayList<Semester>();

	@Override
	public Semester find(int identifier) {
		
		for (Semester sm: loS) {
			if (sm.getId() == identifier) {
				return sm;
			}
		}
		
		return null;
	}

	@Override
	public Findable find(String identifier) {
		throw new RuntimeException("Error: Please search for Semesters using an ID");
	}

	
	
	public void add(Semester sm) {
		loS.add(sm);
	}

	@Override
	public Iterator<Semester> iterator() {
		return loS.iterator();
	}
	
}
