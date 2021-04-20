package com.vergilyn.examples.mongodb.document;

import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * VTODO 2021-04-19 "Automatic index creation will be disabled by default as of Spring Data MongoDB 3.x."
 *
 * @author vergilyn
 * @since 2021-04-19
 */
@Data
@Document(collection = WebsitesDoc.DOC_NAME)
//@CompoundIndex(name = "idx_name", def = "{'name': 1}")
//@CompoundIndex(name = "unique_url", def = "{'url': 1}", unique = true)
public class WebsitesDoc extends AbstractDoc<String> {
	public static final String DOC_NAME = "websites";

	private String name;

	private String url;

	private Integer sort;

	private String description;

	private LocalDateTime createTime;

}
