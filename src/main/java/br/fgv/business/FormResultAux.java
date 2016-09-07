package br.fgv.business;

import java.io.Serializable;
import java.util.*;
import br.fgv.util.ColumnField;

public class FormResultAux implements Serializable {

	private static final long serialVersionUID = -8961026753295064058L;

	private Map<String, ColumnField> optinalFields;
	private Map<String, ColumnField> fixedFields;

	public FormResultAux(List<ColumnField> optionalFields, List<ColumnField> fixedFields) {
		this.optinalFields = new HashMap<String, ColumnField>();
		this.fixedFields = new HashMap<String, ColumnField>();

		for (ColumnField field : optionalFields)
			this.addOptionalField(field);

		for (ColumnField field : fixedFields)
			this.addFixedField(field);
	}
	
	public void addOptionalField(ColumnField column) {
		this.optinalFields.put(column.getKey(), column);
	}

	public void addFixedField(ColumnField column) {
		this.fixedFields.put(column.getKey(), column);
	}

    public Map<String, ColumnField> getFixedFields() {
        return Collections.unmodifiableMap(fixedFields);
    }

    public Map<String, ColumnField> getOptionalFields() {
        return Collections.unmodifiableMap(optinalFields);
    }
}
