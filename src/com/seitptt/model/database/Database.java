package com.seitptt.model.database;

import java.io.FileNotFoundException;



import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.seitptt.model.personnel.Administrator;
import com.seitptt.model.personnel.ClassDirector;
import com.seitptt.model.personnel.Employee;
import com.seitptt.model.personnel.ListOfEmployees;
import com.seitptt.model.personnel.PTTDirector;
import com.seitptt.model.personnel.Teacher;
import com.seitptt.model.processes.ListOfTeachingRequests;
import com.seitptt.model.processes.ListOfTeachingRequirements;
import com.seitptt.model.processes.Semester;
import com.seitptt.model.processes.TeachingRequest;
import com.seitptt.model.processes.TeachingRequirement;
import com.seitptt.model.processes.Classes;
import com.seitptt.model.processes.ListOfClasses;
import com.seitptt.model.processes.ListOfSemesters;

public class Database {
	private final static String dbDir = "db/";
	public static void setEmployeesCacheFromDB() {
		final String dbFile = Database.dbDir + "employees.txt";
		final ListOfEmployees listOfEmployees = new ListOfEmployees();
		
		FileReader fr = null;
		
		try {
			fr = new FileReader(dbFile);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		final Scanner s = new Scanner(fr);
		
		s.nextLine();
		
		while(s.hasNextLine()) {
			while(s.hasNext()) {
				final String type = s.next();
				final String username = s.next();
				final String password = s.next();
				final String firstName = s.next();
				final String lastName = s.next();
				
				Employee employee = null;
				
				
				
				if (type.contains("ptt")) {
					employee = new PTTDirector(firstName, lastName);
					employee.setUsername(username);
					employee.setPassword(password);
				}
				
				if (type.contains("class")) {
					employee = new ClassDirector(firstName, lastName);
					employee.setUsername(username);
					employee.setPassword(password);
				}
				
				if (type.contains("admin")) {
					employee = new Administrator(firstName, lastName);
					employee.setUsername(username);
					employee.setPassword(password);
				}
				
				if (type.contains("teacher")) {
					employee = new Teacher(firstName, lastName);
					employee.setUsername(username);
					employee.setPassword(password);
				}
				
				listOfEmployees.add(employee);
				
			}
		}
		
		try{
			fr.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		s.close();
		DatabaseCache.setEmployeesCache(listOfEmployees);
	}
	
	public static ListOfEmployees getEmployeesFromDB() {
		return DatabaseCache.getEmployeesCache();
	}
	
	public static void setSemesterCacheFromDB() {
		final String dbFile = Database.dbDir + "semesters.txt";
		final ListOfSemesters listOfSemesters = new ListOfSemesters();
		
		FileReader fr = null;
		
		try {
			fr = new FileReader(dbFile);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		final Scanner s = new Scanner(fr);
		
		s.nextLine();
		
		while(s.hasNextLine()) {
			while(s.hasNext()) {
				final int id = s.nextInt();
				final int number = s.nextInt();
				final int year = s.nextInt();
				
				Semester semester = new Semester(id, number, year);
				
				listOfSemesters.add(semester);
				
			}
		}
		
		try{
			fr.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		s.close();
		DatabaseCache.setSemestersCache(listOfSemesters);
	}
	
	public static ListOfSemesters getSemestersFromDB() {
		return DatabaseCache.getSemestersCache();
	}
	
	public static ListOfTeachingRequests getTeachingRequestsFromDB() {
		final String dbFile = Database.dbDir + "teaching_requests.txt";
		final ListOfTeachingRequests listOfTeachingRequests = new ListOfTeachingRequests();
		
		FileReader fr = null;
		
		try {
			fr = new FileReader(dbFile);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		final Scanner s = new Scanner(fr);
		
		s.nextLine();
		
		while(s.hasNextLine()) {
			while(s.hasNext()) {
				final int id = s.nextInt();
				final String teacherUsername = s.next();
				final String classCode = s.next();
				final int teachingRequirementId = s.nextInt();
				
				Teacher teacher = (Teacher)Database.getEmployeesFromDB().find(teacherUsername);
				
				Classes classObj = (Classes)Database.getClassesFromDB().find(classCode);
				TeachingRequirement teachingRequirement = (TeachingRequirement)Database.getTeachingRequirementsFromDB().find(teachingRequirementId);
				
				TeachingRequest teachingRequest = new TeachingRequest(id, teacher, classObj, teachingRequirement);
				
				listOfTeachingRequests.add(teachingRequest);
				
			}
		}
		
		try{
			fr.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		s.close();
		return listOfTeachingRequests;
	}
	
	public static ListOfTeachingRequirements getTeachingRequirementsFromDB() {
		final String dbFile = Database.dbDir + "teaching_requirements.txt";
		final ListOfTeachingRequirements listOfTeachingRequirements= new ListOfTeachingRequirements();
		
		FileReader fr = null;
		
		try {
			fr = new FileReader(dbFile);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		final Scanner s = new Scanner(fr);
		
		s.nextLine();
		
		while(s.hasNextLine()) {
			while(s.hasNext()) {
				final int id = s.nextInt();
				final int numberOfTeachers = s.nextInt();
				final String classCode = s.next();
				
				Classes classObj = (Classes)Database.getClassesFromDB().find(classCode);
				
				TeachingRequirement teachingRequirement = new TeachingRequirement(id, numberOfTeachers, classObj);
				
				listOfTeachingRequirements.add(teachingRequirement);
				
			}
		}
		
		try{
			fr.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		s.close();
		return listOfTeachingRequirements;
	}
	
	public static ListOfClasses getClassesFromDB() {
		final String dbFile = Database.dbDir + "classes.txt";
		final ListOfClasses listOfClasses= new ListOfClasses();
		
		FileReader fr = null;
		
		try {
			fr = new FileReader(dbFile);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		final Scanner s = new Scanner(fr);
		
		s.nextLine();
		
		while(s.hasNextLine()) {
			while(s.hasNext()) {
				final String code = s.next();
				final String name = s.next();
				final int semesterId = s.nextInt();
				
				final Semester semester = Database.getSemestersFromDB().find(semesterId);
				
				final Classes classObj = new Classes(code, name, semester);
				
				listOfClasses.add(classObj);
				
			}
		}
		
		try{
			fr.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		s.close();
		return listOfClasses;
	}
	
	public void removeTeachingRequirementFromDB(TeachingRequirement teachingRequirement) {
		final String dbFile = Database.dbDir + "teaching_requirements.txt";
		String newDbString = "";
		
		FileReader fr = null;
		FileWriter fw = null;
		
		try {
			fr = new FileReader(dbFile);
			fw = new FileWriter(dbFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		final Scanner s = new Scanner(fr);
		
		newDbString += s.nextLine();
		
		while(s.hasNextLine()) {
			int fileId = s.nextInt();
			
			int trId = teachingRequirement.getId();
			
			if (!(fileId==trId)){
				newDbString += s.nextLine();
			}
			
			s.nextLine();
		}
		
		s.close();
		
		try {
			fw.write(newDbString);
		} catch(IOException e) {
			
		}
		
	}
	
	public void removeTeachingRequestFromDB(TeachingRequest teachingRequest) {
		final String dbFile = Database.dbDir + "teaching_requests.txt";
		String newDbString = "";
		
		FileReader fr = null;
		FileWriter fw = null;
		
		try {
			fr = new FileReader(dbFile);
			fw = new FileWriter(dbFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		final Scanner s = new Scanner(fr);
		
		newDbString += s.nextLine();
		
		while(s.hasNextLine()) {
			int fileId = s.nextInt();
			
			int trId = teachingRequest.getId();
			
			if (!(fileId==trId)){
				newDbString += s.nextLine();
			}
			
			s.nextLine();
		}
		
		s.close();
		
		try {
			fw.write(newDbString);
		} catch(IOException e) {
			
		}
		
	}
	
	public static void LoadCaches() {
		Database.setEmployeesCacheFromDB();
		Database.setSemesterCacheFromDB();
	}
}
