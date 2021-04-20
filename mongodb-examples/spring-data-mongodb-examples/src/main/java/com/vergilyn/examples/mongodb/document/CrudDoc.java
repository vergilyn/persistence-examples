package com.vergilyn.examples.mongodb.document;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.vergilyn.examples.mongodb.enums.TypeEnum;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = CrudDoc.DOC_NAME)
public class CrudDoc extends AbstractDoc<String> {
	public static final String DOC_NAME = "crud_doc";

	private String tString;
	private Integer tInteger;
	private Boolean tBoolean;
	private LocalDateTime tLocalDateTime;
	private TypeEnum tEnum;

	public static CrudDoc buildDefault(){
		CrudDoc doc = new CrudDoc();
		doc.setTString("string");
		doc.setTInteger(1);
		doc.setTBoolean(true);
		doc.setTLocalDateTime(LocalDateTime.now());
		doc.setTEnum(TypeEnum.A);

		return doc;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CrudDoc)) {
			return false;
		}
		CrudDoc doc = (CrudDoc) o;
		return Objects.equal(getId(), doc.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
}
