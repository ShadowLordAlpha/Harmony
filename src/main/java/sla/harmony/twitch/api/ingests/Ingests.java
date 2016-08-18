package sla.harmony.twitch.api.ingests;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "_links" })
public class Ingests {

	@JsonProperty("ingests")
	private Ingest[] ingests;

	private Ingests() {

	}

	public Ingest[] getIngestList() {
		return ingests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(ingests);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Ingests other = (Ingests) obj;
		if(!Arrays.equals(ingests, other.ingests)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ingests [ingests=" + Arrays.toString(ingests) + "]";
	}
}
