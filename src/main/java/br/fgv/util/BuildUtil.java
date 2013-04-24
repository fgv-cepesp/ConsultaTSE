/**
 * Copyright (c) 20012-2013 "FGV - CEPESP" [http://cepesp.fgv.br]
 *
 * This file is part of CEPESP-DATA.
 *
 * CEPESP-DATA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CEPESP-DATA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CEPESP-DATA. If not, see <http://www.gnu.org/licenses/>.
 */
package br.fgv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public final class BuildUtil {
	private static final Logger LOG = Logger.getLogger(BuildUtil.class);

	private static final ResourceBundle PROPERTIES = ResourceBundle
			.getBundle("build");

	
	public static final String POM_VERSION = PROPERTIES.getString("POM_VERSION");
	public static final Date BUILD_TIME = getDate(PROPERTIES.getString("BUILD_TIME"));
	public static final String ANALYTICS_CODE = PROPERTIES.getString("ANALYTICS_CODE");
	
	private static Date getDate(String date) {
		try {
			return new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z").parse( date );
		} catch (ParseException e) {
			LOG.error("Error parsing date: " + date);
			return null;
		}
	}

}
