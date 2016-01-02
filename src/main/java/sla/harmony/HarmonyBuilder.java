/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package sla.harmony;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Builds a new Harmony instance with all options and other 
 * <p>
 * Everyone else, and most of the libraries used, are using builders so a builder it is.
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class HarmonyBuilder {

	private String email;
	private String password;
	private Path dataPath;
	private AtomicBoolean advancedMode = new AtomicBoolean(false);
	
	public HarmonyBuilder() {
		this(null, null);
	}
	
	public HarmonyBuilder(String password) {
		this(null, password);
	}
	
	public HarmonyBuilder(String email, String password) {
		this(email, password, "data", "auth.json");
	}
	
	public HarmonyBuilder(String email, String password, String dataPathFirst, String... dataPathParts) {
		this(email, password, FileSystems.getDefault().getPath(dataPathFirst, dataPathParts));
	}
	
	public HarmonyBuilder(String email, String password, Path dataPath) {
		this.email = email;
		this.password = password;
		this.dataPath = dataPath;
	}
	
	public HarmonyBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public HarmonyBuilder setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public HarmonyBuilder setDataPath(String dataPath) {
		return setDataPath(FileSystems.getDefault().getPath(dataPath));
	}
	
	public HarmonyBuilder setDataPath(String dataPathFirst, String... dataPathParts) {
		return setDataPath(FileSystems.getDefault().getPath(dataPathFirst, dataPathParts));
	}

	public HarmonyBuilder setDataPath(Path dataPath) {
		this.dataPath = dataPath;
		return this;
	}
	
	// TODO different options for Harmony
	public HarmonyBuilder setAdvancedMode(boolean advancedMode) {
		this.advancedMode.set(advancedMode);
		return this;
	}
	
	public Harmony build() {
		// TODO decrypt token and pass option arguments
		return new Harmony(email, password, dataPath, advancedMode.get(), true);
	}
}
