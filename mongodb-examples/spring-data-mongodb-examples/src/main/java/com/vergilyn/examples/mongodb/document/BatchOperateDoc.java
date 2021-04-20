package com.vergilyn.examples.mongodb.document;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.vergilyn.examples.mongodb.enums.TypeEnum;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = BatchOperateDoc.DOC_NAME)
public class BatchOperateDoc extends AbstractDoc<String> {
	public static final String DOC_NAME = "batch_operate_doc";

	private String tString;
	private Integer tInteger;
	private Boolean tBoolean;
	private LocalDateTime tLocalDateTime;
	private TypeEnum tEnum;

	public static BatchOperateDoc buildDefault(){
		BatchOperateDoc doc = new BatchOperateDoc();
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
		if (!(o instanceof BatchOperateDoc)) {
			return false;
		}
		BatchOperateDoc doc = (BatchOperateDoc) o;
		return Objects.equal(getId(), doc.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
}
