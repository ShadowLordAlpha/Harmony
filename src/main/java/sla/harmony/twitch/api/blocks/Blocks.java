package sla.harmony.twitch.api.blocks;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "_links" })
public class Blocks {

	@JsonProperty("blocks")
	private Block[] blocks;

	private Blocks() {

	}

	public Block[] getBlocks() {
		return blocks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(blocks);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Blocks other = (Blocks) obj;
		if(!Arrays.equals(blocks, other.blocks)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Blocks [blocks=" + Arrays.toString(blocks) + "]";
	}
}
