/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.richfaces.taglib;

import javax.faces.context.FacesContext;

import org.ajax4jsf.webapp.taglib.AjaxComponentHandler;

import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributes;
import com.sun.facelets.tag.jsf.ComponentConfig;

/**
 * 
 * <br /><br />
 * 
 * Created 23.08.2007
 * @author Nick Belaevski
 * @since 3.1
 */

public abstract class TabPanelTagHandlerBase extends AjaxComponentHandler {

	public TabPanelTagHandlerBase(ComponentConfig config) {
		super(config);
	}
	
	protected MetaRuleset createMetaRuleset(Class type) {
		TagAttributes attributes = this.tag.getAttributes();
		TagAttribute attribute = attributes.get("value");
		if (attribute != null && attributes.get("selectedTab") != null) {
			TagAttribute idAttribute = attributes.get("id");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().log("selectedTab attribute has been already set for component with id: " + 
					idAttribute != null ? idAttribute.getValue() : null + 
					"[" + attribute.getValue() + "]. value attribute is deprecated and thus has been dropped!");
		}
		return super.createMetaRuleset(type).alias("selectedTab", "value");
	}

}
