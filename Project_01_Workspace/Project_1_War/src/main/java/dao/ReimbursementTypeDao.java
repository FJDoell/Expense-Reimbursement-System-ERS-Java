package dao;

import java.util.TreeMap;

import db.DbConnectionHandler;
import model.ReimbursementType;

public class ReimbursementTypeDao {
	private TreeMap<Integer, ReimbursementType> allReimbursementTypes = new TreeMap<Integer, ReimbursementType>();
	
	public ReimbursementTypeDao() {
		this.saveAll(new TreeMap<Integer, ReimbursementType>());
	}
	public ReimbursementTypeDao(TreeMap<Integer, ReimbursementType> allReimbursementTypes) {
		this.saveAll(allReimbursementTypes);
	}
	
	public void initialize() {
		saveAll(DbConnectionHandler.getAllReimbursementTypes());
	}
	
	public ReimbursementType getById(int id) {
		return allReimbursementTypes.get(id);
	}
	
	public ReimbursementType getByName(String name) {
		for(ReimbursementType rt : this.allReimbursementTypes.values()) {
			if(rt.getType().equals(name)) {
				return rt;
			}
		}
		return null;
	}

	public TreeMap<Integer, ReimbursementType> getAll() {
		return allReimbursementTypes;
	}

	public void save(ReimbursementType r) {
		this.allReimbursementTypes.put(r.getTypeId(), r);
	}

	// Save all from a passed in map, replacing current data
	public void saveAll(TreeMap<Integer, ReimbursementType> rt) {
		this.allReimbursementTypes.clear();
		this.allReimbursementTypes.putAll(rt);
	}

	public void delete(int id) {
		this.allReimbursementTypes.remove(id);
	}

}
