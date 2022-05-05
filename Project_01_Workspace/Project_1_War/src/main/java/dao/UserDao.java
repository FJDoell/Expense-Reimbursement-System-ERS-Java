package dao;

import java.util.TreeMap;

import model.User;

public class UserDao implements Dao<User> {
	private TreeMap<Integer, User> allUsers = new TreeMap<Integer, User>();
	
	public UserDao() {
		this.saveAll(new TreeMap<Integer, User>());
	}
	public UserDao(TreeMap<Integer, User> allUsers) {
		this.saveAll(allUsers);
	}
	
	@Override
	public User getById(int id) {
		return allUsers.get(id);
	}
	
	public User getByCredentials(String user, String pass) {
		for(User u : this.allUsers.values()) {
			// if credentials match
			if(u.getUsername().equals(user) && u.getPassword().equals(pass)) {
				return u;
			}
		}
		return new User();
	}

	@Override
	public TreeMap<Integer, User> getAll() {
		return allUsers;
	}

	@Override
	public void save(User u) {
		this.allUsers.put(u.getId(), u);
	}
	
	// Save all from a passed in map, replacing current data
	public void saveAll(TreeMap<Integer, User> u) {
		this.allUsers.clear();
		this.allUsers.putAll(u);
	}

	@Override
	public void delete(int id) {
		this.allUsers.remove(id);
	}
}
