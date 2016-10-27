package br.fgv.business;

import java.io.Serializable;
import java.util.*;
import br.fgv.util.ColumnField;

public class CollumnFieldsCollection implements Serializable {

	private static final long serialVersionUID = -8961026753295064058L;

	private List<ColumnField> optionalFields;
	private List<ColumnField> fixedFields;

	public CollumnFieldsCollection(List<ColumnField> optionalFields, List<ColumnField> fixedFields) {
		this.optionalFields = new ArrayList<ColumnField>();
		this.fixedFields = new ArrayList<ColumnField>();

		for (ColumnField field : optionalFields)
			this.addOptionalField(field);

		for (ColumnField field : fixedFields)
			this.addFixedField(field);
	}
	
	public void addOptionalField(ColumnField column) {
		this.optionalFields.add(column);
	}

	public void addFixedField(ColumnField column) {
		this.fixedFields.add(column);
	}

    public List<ColumnField> getFixedFields() {
        return Collections.unmodifiableList(fixedFields);
    }

    public List<ColumnField> getOptionalFields() {
        return Collections.unmodifiableList(optionalFields);
    }

    public String toString() {
    	return "Optional Fields: " + this.getOptionalFields() + ";\n Fixed Fields: " + this.getFixedFields();
	}
}
