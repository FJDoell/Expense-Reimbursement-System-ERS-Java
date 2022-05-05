package dao;

import java.util.TreeMap;

import model.UserRole;

public class UserRoleDao {
	private TreeMap<Integer, UserRole> userRoles = new TreeMap<Integer, UserRole>();
	
	public UserRoleDao() {
		saveAll(new TreeMap<Integer, UserRole>());
	}
	public UserRoleDao(TreeMap<Integer, UserRole> userRoles) {
		super();
		this.userRoles = userRoles;
	}

	public UserRole getById(int id) {
		return userRoles.get(id);
	}

	public TreeMap<Integer, UserRole> getAll() {
		return userRoles;
	}

	public void save(UserRole u) {
		this.userRoles.put(u.getId(), u);
	}
	
	// Save all from a passed in map, replacing current data
	public void saveAll(TreeMap<Integer, UserRole> u) {
		this.userRoles.clear();
		this.userRoles.putAll(u);
	}

	public void delete(int id) {
		this.userRoles.remove(id);
	}
	
}
