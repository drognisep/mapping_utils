/*******************************************************************************
 * Copyright 2018 Saylor Solutions
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.saylorsolutions.util.mapping_utils;

import java.util.HashMap;

public class SourceValueMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 8861175757379055451L;

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		Object obj = this.get(key);
		if(!clazz.isAssignableFrom(obj.getClass()) || obj == null) {
			return (T) null;
		}
		return (T) obj;
	}
}
