package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ReimbursementDao;
import model.Reimbursement;
import model.ResponseObject;

public class JsonController {
	private ResponseObject respObj = null;
	private ReimbursementDao reimbDao = null;
	private TreeMap<Integer, Reimbursement> allReimbs = null;
	private Reimbursement ourReimb = null;
	
	public JsonController() {
		setRespObj(new ResponseObject(""));
		setReimbDao(new ReimbursementDao());
		setAllReimbs(new TreeMap<Integer, Reimbursement>());
		setOurReimb(new Reimbursement());
	}
	public JsonController(ResponseObject respObj, ReimbursementDao reimbDao, Reimbursement ourReimb) {
		super();
		this.respObj = respObj;
		this.reimbDao = reimbDao;
		this.ourReimb = ourReimb;
	}
	// Getters and Setters
	public ResponseObject getRespObj() {
		return respObj;
	}
	public void setRespObj(ResponseObject respObj) {
		this.respObj = respObj;
	}
	public ReimbursementDao getReimbDao() {
		return reimbDao;
	}
	public void setReimbDao(ReimbursementDao reimbDao) {
		this.reimbDao = reimbDao;
	}
	public TreeMap<Integer, Reimbursement> getAllReimbs() {
		return allReimbs;
	}
	public void setAllReimbs(TreeMap<Integer, Reimbursement> allReimbs) {
		this.allReimbs = allReimbs;
	}
	public Reimbursement getOurReimb() {
		return ourReimb;
	}
	public void setOurReimb(Reimbursement ourReimb) {
		this.ourReimb = ourReimb;
	}
	
	/**
	 * 
	 * @param resp
	 * @param msg What message should the response have?
	 * @throws ServletException
	 * @throws IOException
	 */
	public void returnAllReimbJson(HttpServletResponse resp, String msg) throws ServletException, IOException {
		// initialize
		ArrayList<Reimbursement> reimbsList = new ArrayList<Reimbursement>();
		
		// for all reimbs
		for(Reimbursement r : getAllReimbs().values()) {
			// add them to the ArrayList
			reimbsList.add(r);
		}
		
		// Set up the responseObject values
		setRespObj(new ResponseObject(msg, reimbsList));
		
		// Finally, print that response
		printJson(resp);
	}
	
	public void returnReimbJson(HttpServletResponse resp, String msg) throws ServletException, IOException {
		// initialize
		ArrayList<Reimbursement> reimbsList = new ArrayList<Reimbursement>();
		
		reimbsList.add(getOurReimb());
		
		// Set up the responseObject values
		setRespObj(new ResponseObject(msg, reimbsList));
		
		// Finally, print that response
		printJson(resp);
	}
	
	public void returnErrorJson(HttpServletResponse resp, String msg) throws ServletException, IOException {
		// Set up the responseObject values
		setRespObj(new ResponseObject(msg));
		
		// Finally, print that response
		printJson(resp);
	}
	
	/**
	 * Return the header JSON and a response object
	 * @param resp
	 * @param head
	 * @throws ServletException
	 * @throws IOException
	 */
	public void returnHeadAndMsgJson(HttpServletResponse resp, String head, String msg) throws ServletException, IOException {
		// Set up the responseObject values
		setRespObj(new ResponseObject(head, msg));
		
		// Finally, print that response
		printJson(resp);
	}
	
	public void printJson(HttpServletResponse resp) throws IOException {
		String json = "";
		// Now set the json to this
		ObjectMapper mapper = new ObjectMapper();
		json = mapper.writeValueAsString(getRespObj());
		System.out.println("RETURN JSON: " + json);
		
		// Let's print that JSON and respond with it!
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out.print(json);
		out.flush();
	}
	
}
