package dao;

import java.util.TreeMap;

import db.DbConnectionHandler;
import model.ReimbursementStatus;

public class ReimbursementStatusDao {
	private TreeMap<Integer, ReimbursementStatus> allReimbursementStatuses = new TreeMap<Integer, ReimbursementStatus>();
	
	public ReimbursementStatusDao() {
		saveAll(new TreeMap<Integer, ReimbursementStatus>());
	}
	public ReimbursementStatusDao(TreeMap<Integer, ReimbursementStatus> allReimbursementStatuses) {
		super();
		this.allReimbursementStatuses = allReimbursementStatuses;
	}
	
	public void initialize() {
		saveAll(DbConnectionHandler.getAllReimbursementStatuses());
	}
	
	public ReimbursementStatus getById(int id) {
		return allReimbursementStatuses.get(id);
	}
	
	public ReimbursementStatus getByName(String name) {
		for(ReimbursementStatus rst : this.allReimbursementStatuses.values()) {
			if(rst.getStatus().equals(name)) {
				return rst;
			}
		}
		return null;
	}

	public TreeMap<Integer, ReimbursementStatus> getAll() {
		return allReimbursementStatuses;
	}

	public void save(ReimbursementStatus r) {
		this.allReimbursementStatuses.put(r.getId(), r);
	}
	
	// Save all from a passed in map, replacing current data
	public void saveAll(TreeMap<Integer, ReimbursementStatus> rs) {
		this.allReimbursementStatuses.clear();
		this.allReimbursementStatuses.putAll(rs);
	}

	public void delete(int id) {
		this.allReimbursementStatuses.remove(id);
	}
	
}
