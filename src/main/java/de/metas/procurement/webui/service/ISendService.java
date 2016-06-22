package de.metas.procurement.webui.service;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;

/*
 * #%L
 * metasfresh-procurement-webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Central point for controlling all aspects around "Send" objects to metasfresh server.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public interface ISendService
{
	/** Implemented by beans which are aware of sent status */
	interface ISendAwareBean
	{
		String PROPERTY_Sent = "sent";

		void setSent(boolean sent);

		boolean isSent();

		boolean checkSent();

		void setSentFieldsFromActualFields();
	}

	Property<Integer> getNotSentCounterProperty();

	int getNotSentCounter();

	void incrementNotSentCounter();

	void decrementNotSentCounter();

	<BT extends ISendAwareBean> void updateSentStatus(BeanItem<BT> item);

	void sendAll();
}
