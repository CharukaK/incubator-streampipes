package org.streampipes.container.util;

import java.io.IOException;
import java.net.URL;

import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import org.streampipes.commons.exceptions.SepaParseException;
import org.streampipes.serializers.jsonld.JsonLdTransformer;

public class DeclarerUtils {

	public static <T> T descriptionFromResources(URL resourceUrl, Class<T> destination) throws SepaParseException
	{
		try {
			return new JsonLdTransformer().fromJsonLd(Resources.toString(resourceUrl, Charsets.UTF_8), destination);
		} catch (RDFParseException | UnsupportedRDFormatException
				| RepositoryException | IOException e) {
			e.printStackTrace();
			throw new SepaParseException();
		}
	}
}
