package service;

import java.util.ArrayList;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An object that stores a Generic object map of any type. Then it can return
 * that map's objects as JSON.
 * 
 * @author darkm
 *
 */
public class JsonHandler<T> {
	// T stands for "Type"
	private TreeMap<Integer, T> t;

	public JsonHandler(TreeMap<Integer, T> t) {
		super();
		this.t = t;
	}

	public void set(TreeMap<Integer, T> t) {
		this.t = t;
	}

	public TreeMap<Integer, T> get() {
		return t;
	}

	public String mapToJSON() {
		String jsonString = "";
		// create `ObjectMapper` instance
		ObjectMapper mapper = new ObjectMapper();

		// convert our current map to JSON
		try {
			// first we need an ArrayList, not a Map
			ArrayList<T> tAsArray = new ArrayList<T>();
			tAsArray.addAll(t.values());
			jsonString = mapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return that map as JSON
		return jsonString;
	}

}
