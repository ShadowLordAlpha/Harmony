package sla.harmony.twitch.api.ingests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingest {

	@JsonProperty("name")
	private String name;

	@JsonProperty("default")
	private boolean defaul;

	@JsonProperty("_id")
	private int id;

	@JsonProperty("url_template")
	private String urlTemplate;

	@JsonProperty("availability")
	private float availability;

	private Ingest() {

	}

	public String getName() {
		return name;
	}

	public boolean isDefault() {
		return defaul;
	}

	public int getId() {
		return id;
	}

	public String getUrlTemplate() {
		return urlTemplate;
	}

	public float getAvailability() {
		return availability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(availability);
		result = prime * result + (defaul ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((urlTemplate == null) ? 0 : urlTemplate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Ingest other = (Ingest) obj;
		if(Float.floatToIntBits(availability) != Float.floatToIntBits(other.availability)) return false;
		if(defaul != other.defaul) return false;
		if(id != other.id) return false;
		if(name == null) {
			if(other.name != null) return false;
		} else if(!name.equals(other.name)) return false;
		if(urlTemplate == null) {
			if(other.urlTemplate != null) return false;
		} else if(!urlTemplate.equals(other.urlTemplate)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ingest [name=" + name + ", default=" + defaul + ", id=" + id + ", urlTemplate=" + urlTemplate + ", availability=" + availability + "]";
	}
}
