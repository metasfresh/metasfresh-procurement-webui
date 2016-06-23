package de.metas.procurement.webui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.gwt.thirdparty.guava.common.base.Objects.ToStringHelper;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Entity
@Table(name = Rfq.TABLE_NAME)
@SuppressWarnings("serial")
public class Rfq extends AbstractSyncConfirmAwareEntity
{
	/* package */static final String TABLE_NAME = "rfq";

	@NotNull
	private Date dateStart;
	@NotNull
	private Date dateEnd;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private BPartner bpartner;

	@NotNull
	private Date dateClose;
	private boolean closed;
	private boolean winner;

	@ManyToOne
	@NotNull
	private Product product;

	@NotNull
	private BigDecimal qtyRequested = BigDecimal.ZERO;

	@NotNull
	private BigDecimal pricePromised = BigDecimal.ZERO;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private final List<RfqQty> quantities = new ArrayList<>();


	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.omitNullValues()
				.add("dateStart", dateStart)
				.add("dateEnd", dateEnd)
				//
				.add("bpartner", bpartner)
				//
				.add("dateClosed", dateClose)
				.add("closed", closed)
				.add("winner", winner)
				//
				.add("product", product)
				//
				.add("qtyRequested", qtyRequested)
				//
				.add("quantities", quantities)
				//
				.add("pricePromised", pricePromised);
	}

	public Date getDateStart()
	{
		return dateStart;
	}

	public void setDateStart(final Date dateStart)
	{
		this.dateStart = dateStart;
	}

	public Date getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(final Date dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	public BPartner getBpartner()
	{
		return bpartner;
	}

	public void setBpartner(final BPartner bpartner)
	{
		this.bpartner = bpartner;
	}

	public Date getDateClose()
	{
		return dateClose;
	}

	public void setDateClose(final Date dateClose)
	{
		this.dateClose = dateClose;
	}

	public boolean isClosed()
	{
		return closed;
	}

	public void setClosed(final boolean closed)
	{
		this.closed = closed;
	}

	public boolean isWinner()
	{
		return winner;
	}

	public void setWinner(final boolean winner)
	{
		this.winner = winner;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(final Product product)
	{
		this.product = product;
	}

	public BigDecimal getQtyRequested()
	{
		return qtyRequested;
	}

	public void setQtyRequested(final BigDecimal qtyRequested)
	{
		this.qtyRequested = qtyRequested;
	}

	public BigDecimal getPricePromised()
	{
		return pricePromised;
	}

	public void setPricePromised(final BigDecimal pricePromised)
	{
		this.pricePromised = pricePromised;
	}

	public List<RfqQty> getQuantities()
	{
		return quantities;
	}
}
