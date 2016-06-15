package de.metas.procurement.webui.sync;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductTrl;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductTrlRepository;

/*
 * #%L
 * de.metas.procurement.webui
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

@Service
@Transactional
public class SyncProductImportService extends AbstractSyncImportService
{
	@Autowired
	@Lazy
	private ProductRepository productsRepo;
	@Autowired
	@Lazy
	private ProductTrlRepository productTrlsRepo;

	public Product importProduct(final SyncProduct syncProduct)
	{
		final Product product = productsRepo.findByUuid(syncProduct.getUuid());
		return importProduct(syncProduct, product);
	}

	public Product importProduct(final SyncProduct syncProduct, Product product)
	{
		final String uuid = syncProduct.getUuid();
		if (product != null && !Objects.equals(product.getUuid(), uuid))
		{
			product = null;
		}
		if (product == null)
		{
			product = productsRepo.findByUuid(uuid);
		}

		//
		// Handle delete request
		if (syncProduct.isDeleted())
		{
			if (product == null)
			{
				// does not exist anyways
				return null;
			}
			else
			{
				deleteProduct(product);
				return null;
			}
		}

		if (product == null)
		{
			product = new Product();
			product.setUuid(uuid);
		}

		product.setDeleted(false);
		product.setName(syncProduct.getName());
		product.setPackingInfo(syncProduct.getPackingInfo());
		product.setShared(syncProduct.isShared());
		productsRepo.save(product);
		logger.debug("Imported: {} -> {}", syncProduct, product);

		//
		// Import product translations
		final Map<String, ProductTrl> productTrls = mapByLanguage(productTrlsRepo.findByRecord(product));
		for (final Map.Entry<String, String> lang2nameTrl : syncProduct.getNamesTrl().entrySet())
		{
			final String language = lang2nameTrl.getKey();
			final String nameTrl = lang2nameTrl.getValue();

			ProductTrl productTrl = productTrls.remove(language);
			if (productTrl == null)
			{
				productTrl = new ProductTrl();
				productTrl.setLanguage(language);
				productTrl.setRecord(product);
			}

			productTrl.setName(nameTrl);
			productTrlsRepo.save(productTrl);
			logger.debug("Imported: {}", productTrl);
		}
		for (final ProductTrl productTrl : productTrls.values())
		{
			productTrlsRepo.delete(productTrl);
		}

		return product;
	}

	private void deleteProduct(final Product product)
	{
		if (product.isDeleted())
		{
			return;
		}
		product.setDeleted(true);
		productsRepo.save(product);

		logger.debug("Deleted: {}", product);
	}
}
