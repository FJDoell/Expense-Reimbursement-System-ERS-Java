package dao;

import java.util.TreeMap;

import model.Reimbursement;

public class ReimbursementDao implements Dao<Reimbursement> {
	private TreeMap<Integer, Reimbursement> allReimbursements = new TreeMap<Integer, Reimbursement>();
	
	public ReimbursementDao() {
		this.saveAll(new TreeMap<Integer, Reimbursement>());
	}
	public ReimbursementDao(TreeMap<Integer, Reimbursement> allReimbursements) {
		this.saveAll(allReimbursements);
	}
	
	@Override
	public Reimbursement getById(int id) {
		return allReimbursements.get(id);
	}

	@Override
	public TreeMap<Integer, Reimbursement> getAll() {
		return allReimbursements;
	}
	
	public TreeMap<Integer, Reimbursement> getByStatus(int id) {
		TreeMap<Integer, Reimbursement> returned = new TreeMap<Integer, Reimbursement>();
		for(Reimbursement r : allReimbursements.values()) {
			// if the status ID matches what is given, add to return.
			if(r.getStatusId() == id) {
				returned.put(r.getId(), r);
			}
		}
		return returned;
	}
	
	public TreeMap<Integer, Reimbursement> getByType(int id) {
		TreeMap<Integer, Reimbursement> returned = new TreeMap<Integer, Reimbursement>();
		for(Reimbursement r : allReimbursements.values()) {
			// if the status ID matches what is given, add to return.
			if(r.getTypeId() == id) {
				returned.put(r.getId(), r);
			}
		}
		return returned;
	}
	
	public TreeMap<Integer, Reimbursement> getByAuthor(int id) {
		TreeMap<Integer, Reimbursement> returned = new TreeMap<Integer, Reimbursement>();
		for(Reimbursement r : allReimbursements.values()) {
			// if the author ID matches what is given, add to return.
			if(r.getAuthor() == id) {
				returned.put(r.getId(), r);
			}
		}
		return returned;
	}
	
	public TreeMap<Integer, Reimbursement> getResolved() {
		TreeMap<Integer, Reimbursement> returned = new TreeMap<Integer, Reimbursement>();
		for(Reimbursement r : allReimbursements.values()) {
			// if the resolver's ID is greater than 0, it was resolved
			if(r.getResolver() > 0) {
				returned.put(r.getId(), r);
			}
		}
		return returned;
	}
	
	public TreeMap<Integer, Reimbursement> getUnresolved() {
		TreeMap<Integer, Reimbursement> returned = new TreeMap<Integer, Reimbursement>();
		for(Reimbursement r : allReimbursements.values()) {
			// if the resolver's ID is NOT greater than 0, it was NOT resolved
			if(!(r.getResolver() > 0)) {
				returned.put(r.getId(), r);
			}
		}
		return returned;
	}

	@Override
	public void save(Reimbursement r) {
		allReimbursements.put(r.getId(), r);
	}
	
	// Save all from a passed in map, replacing current data
	public void saveAll(TreeMap<Integer, Reimbursement> rs) {
		allReimbursements.clear();
		allReimbursements.putAll(rs);
	}

	@Override
	public void delete(int id) {
		allReimbursements.remove(id);
	}
	
}
